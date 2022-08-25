package com.share.file.user;

import cn.hutool.core.codec.Base64;
import com.gillion.devops.authentication.centre.sdk.utils.EntityUtils;
import com.gillion.eds.client.authentication.HttpRequestJWTUserProvider;
import com.gillion.eds.sso.IUser;
import com.share.file.util.GZipUtils;
import com.share.support.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Strings;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 解决压缩JWT无法读取问题
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/24
 */
public class MyHttpRequestJWTUserProvider extends HttpRequestJWTUserProvider {

    public MyHttpRequestJWTUserProvider(String jwtName, Class userClass, String secretKey) {
        super(jwtName, userClass, secretKey);
    }

    @Override
    public IUser getUserByToken(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        int delimiterCount = 0;
        String base64UrlEncodedPayload = null;
        for (char c : accessToken.toCharArray()) {
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
            byte[] zip = Base64.decode(claims.getSubject());
            byte[] data = GZipUtils.unGZip(zip);
            String subject = new String(data, StandardCharsets.UTF_8);
            user = EntityUtils.readObject(subject, User.class);
            if (Objects.nonNull(user)) {
                user.setExpireTime(claims.getExpiration().getTime());
            }
        }
        return user;
    }
}
