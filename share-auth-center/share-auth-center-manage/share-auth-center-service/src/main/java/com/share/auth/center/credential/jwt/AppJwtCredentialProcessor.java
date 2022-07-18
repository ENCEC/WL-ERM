package com.share.auth.center.credential.jwt;

import com.gillion.ec.core.utils.CookieUtils;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Strings;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 凭证接口实现类（APP）
 * @author wengms
 * @date 2018/12/11 11:46 PM
 * @email wengms@gillion.com.cn
 */
public class AppJwtCredentialProcessor implements CredentialProcessor {

    @Autowired
    private RedisUtil redisUtil;
    @Setter
    private String jwtName = "access_token";
    @Setter
    private String secretKey = "DfEqd%AvjY1!pFEx*4g$E%hL77b#ecjR";

    @Setter
    private int credentialExpireSeconds =  90*3600*24;
    @Setter
    private int cookieExpireSeconds = 90*3600*24+10;

    public AppJwtCredentialProcessor(String jwtName, String secretKey, Integer credentialExpireSeconds, Integer cookieExpireSeconds){
        this.jwtName = jwtName;
        this.secretKey = secretKey;
        this.credentialExpireSeconds = credentialExpireSeconds;
        this.cookieExpireSeconds = cookieExpireSeconds;
    }


    @Override
    public String createCredential(Object userInfo) {
        //申请票据
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("refreshable",false)
                .setHeaderParam("expireSeconds",this.cookieExpireSeconds)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateUtils.addDays(new Date(),90))
                .setSubject(EntityUtils.toJsonString(userInfo))
                .signWith(signatureAlgorithm, secretKey);
        return builder.compact();


    }

    @Override
    public void deliveryCredential(HttpServletResponse response, String credential, String uid) {
        Cookie cookie = new Cookie(jwtName, credential);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpireSeconds-10);
        cookie.setSecure(false);
        response.addCookie(cookie);

        Cookie uidCookie = new Cookie("uid", uid);
        uidCookie.setPath("/");
        uidCookie.setMaxAge(cookieExpireSeconds-10);
        uidCookie.setSecure(false);
        response.addCookie(uidCookie);
    }

    @Override
    public User parseCredential(HttpServletRequest request) {
        Cookie jwt = CookieUtils.getCookie(jwtName);
        if (jwt==null){
            return null;
        }else{
            String credential = jwt.getValue();
            return parseCredential(credential);
        }
    }

    @Override
    public User parseCredential(String credential){
        if (StringUtils.isEmpty(credential)){
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        int delimiterCount = 0;
        String base64UrlEncodedPayload = null;
        for (char c : credential.toCharArray()) {
            if (c == JwtParser.SEPARATOR_CHAR) {
                CharSequence tokenSeq = Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;
                if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        String payload= TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        Claims claims = null;

        // likely to be json, parse it:
        if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') {
            Map<String, Object> claimsMap = EntityUtils.readObject(payload,Map.class);
            claims = new DefaultClaims(claimsMap);
        }
        String subject = Objects.isNull(claims) ? null : claims.getSubject();
        return EntityUtils.readObject(subject, User.class);
    }

    @Override
    public void refreshCredential(HttpServletResponse response, String credential) {
        User userInfo = parseCredential(credential);
        String newCredential = createCredential(userInfo);
        deliveryCredential(response,newCredential,userInfo.getUemUserId().toString());
    }

    @Override
    public void destroy(HttpServletResponse response, String credential) {
        if(StringUtils.isNotEmpty(credential)){
            redisUtil.setForTimeSecs(credential, "disabledJwt", 3600);
        }
        Cookie cookie = new Cookie(jwtName, credential);
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie uidCookie = new Cookie("uid", "");
        uidCookie.setValue("");
        uidCookie.setPath("/");
        uidCookie.setSecure(false);
        uidCookie.setMaxAge(0);
        response.addCookie(uidCookie);
    }
}