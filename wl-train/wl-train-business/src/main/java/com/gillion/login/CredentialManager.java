package com.gillion.login;

import com.fasterxml.jackson.databind.JavaType;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.utils.JsonMapperHolder;
import com.gillion.saas.redis.SassRedisInterface;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.UUID;

/**
 *  凭证管理中心，用于管理凭证的解析、生成、销毁
 *
 * @author wengms
 * @date 2:43 PM 2018/12/19
 */
@Slf4j
@Component
public class CredentialManager {


    @Setter
    private Integer expireTime = 60 * 60 * 4;


    @Autowired
    private SassRedisInterface redisInterface;

    /**
     * 用户申请票据
     *
     * @param user
     * @return java.lang.String
     * @author wengms
     * @date 2:39 PM 2018/12/19
     */
    public String create(IUser user) {
        String ticket = UUID.randomUUID().toString();
        redisInterface.set(ticket, JsonMapperHolder.jsonMapper.toJson(user), expireTime);
        return ticket;
    }

    /**
     * 销毁票据
     *
     * @param ticket
     * @return void
     * @author wengms
     * @date 2:39 PM 2018/12/19
     */
    public void destroy(String ticket) {
        byte[] byteKeys = SerializationUtils.serialize(ticket);
        redisInterface.del(byteKeys);
    }

    /**
     * 解析凭证，获取到凭证对应的用户信息
     *
     * @param
     * @return com.gillion.ec.core.security.IUser
     * @author wengms
     * @date 2:40 PM 2018/12/19
     */
    public IUser parseCredential(String ticket) {
        String objBytes = redisInterface.get(ticket);
        JavaType javaType = JsonMapperHolder.objectMapper.constructType(User.class);
        try {
            return JsonMapperHolder.objectMapper.readValue(objBytes,javaType);
        } catch (IOException e) {
            log.warn("用户信息反序列化失败");
            return null;
        }
    }

    public int getExpireTime() {
        return expireTime;
    }


    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

}
