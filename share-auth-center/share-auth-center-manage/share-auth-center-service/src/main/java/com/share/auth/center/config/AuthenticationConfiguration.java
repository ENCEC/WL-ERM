package com.share.auth.center.config;

import com.share.auth.center.constants.AuthenticationConfig;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.credential.jwt.AppJwtCredentialProcessor;
import com.share.auth.center.credential.jwt.JwtCredentialProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @Configuration
    @ConditionalOnProperty(value = "enabled",prefix = "authentication.jwt",havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(CredentialProcessor.class)
    class JwtConfiguration{

        @Value("${authentication.jwt.secretKey:default}")
        private String secretKey;
        @Value("${authentication.credentialExpireSeconds:1800}")
        private int credentialExpireSeconds;
        @Value("${authentication.jwt.jwtName:access_token}")
        private String jwtName;
        @Value("${authentication.cookieExpireSeconds:3600}")
        private int cookieExpireSeconds;


        private Integer appCredentialExpireSeconds =90*3600*24;

        private Integer appCookieExpireSeconds =90*3600*24+10;


        @Bean
        public CredentialProcessor credentialProcessor(){
            return new JwtCredentialProcessor(jwtName,secretKey,credentialExpireSeconds,cookieExpireSeconds);
        }

        @Bean
        public AppJwtCredentialProcessor appJwtCredentialProcessor(){
            return new AppJwtCredentialProcessor(jwtName,secretKey,appCredentialExpireSeconds,appCookieExpireSeconds);
        }

    }
}
