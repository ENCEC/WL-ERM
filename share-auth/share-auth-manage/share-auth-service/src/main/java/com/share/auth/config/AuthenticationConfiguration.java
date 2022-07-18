package com.share.auth.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:chenxf
 * @Description: eds权限校验相关配置配置类
 * @Date: 15:50 2021/1/18
 * @Param:
 * @Return:
 *
 */
@Configuration
public class AuthenticationConfiguration {

    @Bean
    @ConfigurationProperties("authentication")
    public AuthenticationConfig authenticationConfig(){
        return new AuthenticationConfig();
    }
}
