package com.share.auth.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author:chenxf
 * @Description: reidisTokenstore工具类
 * @Date: 17:49 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Configuration
public class CustomRedisStoreConfig {

    @Bean
    public TokenStore customRedisStore(RedisConnectionFactory redisConnectionFactory){
        return new RedisTokenStore(redisConnectionFactory);
    }
}
