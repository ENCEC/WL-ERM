package com.gillion.configuration;

import com.gillion.eds.extend.redis.RedisInterfaceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by zengqw on 2019/1/21.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }



    @ConfigurationProperties(prefix = "redis")
    @Bean
    public RedisInterfaceFactory redisInterface(){
        return new RedisInterfaceFactory();
    }
}
