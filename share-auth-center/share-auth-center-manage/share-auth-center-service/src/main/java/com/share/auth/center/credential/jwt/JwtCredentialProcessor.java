package com.share.auth.center.credential.jwt;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.gillion.ec.core.utils.CookieUtils;
import com.share.auth.center.constants.CodeFinal;
import com.share.auth.center.constants.RedisMqConstant;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.GZipUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Strings;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 凭证接口实现类
 *
 * @author wengms
 * @date 2018/12/11 11:46 PM
 * @email wengms@gillion.com.cn
 */
@Slf4j
public class JwtCredentialProcessor implements CredentialProcessor {

    @Setter
    private String jwtName;
    @Setter
    private String secretKey;


    @Setter
    private int credentialExpireSeconds;
    @Setter
    private int cookieExpireSeconds;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${web_domain}")
    private String webDomain;

    /**
     * session的用户信息
     */
    private static final String USER = "user";

    public JwtCredentialProcessor(String jwtName, String secretKey, int credentialExpireSeconds, int cookieExpireSeconds) {
        this.jwtName = jwtName;
        this.secretKey = secretKey;
        this.credentialExpireSeconds = credentialExpireSeconds;
        this.cookieExpireSeconds = cookieExpireSeconds;
    }

    @Override
    public String createCredential(Object userInfo) {
        //申请票据
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long expireTime = System.currentTimeMillis() + credentialExpireSeconds * 1000L;
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expireTime))
                .setSubject(EntityUtils.toJsonString(userInfo))
                .signWith(signatureAlgorithm, secretKey);
        byte[] jwtZip = GZipUtils.gZip(StrUtil.bytes(builder.compact(), StandardCharsets.US_ASCII));
        User userInfoModel = (User) userInfo;
        userInfoModel.setExpireTime(expireTime);
        return Base64.encode(jwtZip);
    }

    @Override
    public void deliveryCredential(HttpServletResponse response, String credential, String uid) {
        Cookie cookie = new Cookie(jwtName, credential);
        cookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            cookie.setDomain(webDomain);
        }
        cookie.setHttpOnly(false);
        cookie.setMaxAge(cookieExpireSeconds - 10);
        cookie.setSecure(false);
        response.addCookie(cookie);

        Cookie uidCookie = new Cookie("uid", uid);
        //uidCookie.setHttpOnly(true);
        uidCookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            uidCookie.setDomain(webDomain);
        }
        uidCookie.setMaxAge(cookieExpireSeconds - 10);
        uidCookie.setSecure(false);
        response.addCookie(uidCookie);
    }

    @Override
    public User parseCredential(HttpServletRequest request) {
        Cookie jwt = CookieUtils.getCookie(jwtName);
        if (jwt == null) {
            return null;
        } else {
            String credential = jwt.getValue();
            String digest = DigestUtils.md5Hex(credential);
            if(null == digest || "disabledJwt".equals(redisUtil.get(digest))){
                return null;
            }
            return parseCredential(credential);
        }
    }

    @Override
    public User parseCredential(String credential) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(credential)) {
            return null;
        }
        try {
            byte[] jsonBytes = GZipUtils.unGZip(Base64.decode(credential));
            credential = new String(jsonBytes, StandardCharsets.US_ASCII);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        StringBuilder sb = new StringBuilder(128);
        int delimiterCount = 0;
        String base64UrlEncodedPayload = null;
        for (char c : credential.toCharArray()) {
            if (c == JwtParser.SEPARATOR_CHAR) {
                CharSequence tokenSeq = Strings.clean(sb);
                String token = tokenSeq != null ? tokenSeq.toString() : null;
                if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        String payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        User user = null;
        if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') {
            Map<String, Object> claimsMap = EntityUtils.readObject(payload, Map.class);
            Claims claims = new DefaultClaims(claimsMap);
            String subject = claims.getSubject();
            user = EntityUtils.readObject(subject, User.class);
            if (Objects.nonNull(user)) {
                user.setExpireTime(claims.getExpiration().getTime());
            }
        }
        return user;
    }

    @Override
    public void refreshCredential(HttpServletResponse response, String credential) {
        User userInfo = parseCredential(credential);
        String newCredential = createCredential(userInfo);
        deliveryCredential(response, newCredential, userInfo.getUemUserId().toString());
    }

    @Override
    public void destroy(HttpServletResponse response, String credential) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        User userInfoModel = this.parseCredential(credential);
        if (userInfoModel != null) {
            String redisOfficeKey = RedisMqConstant.USER_INFO_KEY_PRE + userInfoModel.getUemUserId() + "_offices";
            // 清除二级缓存
            redisUtil.delete(redisOfficeKey);
        }

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(credential)) {
            String digest = DigestUtils.md5Hex(credential);
            redisUtil.setForTimeSecs(digest, "disabledJwt", 3600);
        }
        Cookie cookie = new Cookie(jwtName, credential);
        cookie.setValue("");
        cookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            cookie.setDomain(webDomain);
        }
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie uidCookie = new Cookie("uid", "");
        uidCookie.setValue("");
        uidCookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            uidCookie.setDomain(webDomain);
        }
        uidCookie.setSecure(false);
        uidCookie.setMaxAge(0);
        response.addCookie(uidCookie);
        Cookie clientId = new Cookie("client_id", "");
        clientId.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            clientId.setDomain(webDomain);
        }
        clientId.setSecure(false);
        clientId.setMaxAge(0);
        response.addCookie(clientId);

        if (Objects.nonNull(requestAttributes)) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute(USER, null);
            session.invalidate();
        }

    }

}
