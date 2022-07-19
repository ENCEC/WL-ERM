package com.share.message.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 实例restTemplate
 * @author temp
 */
@Configuration
public class RestTemplateConfig {

    @Bean(name = "myrestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
