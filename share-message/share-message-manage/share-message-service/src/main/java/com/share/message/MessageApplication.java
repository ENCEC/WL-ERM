package com.share.message;


import com.gillion.ds.cache.config.RedisConfig;
import com.gillion.ec.core.data.BaseMapper;
import com.gillion.ec.mybatis.ECMybatisConfiguration;
import com.gillion.eds.client.authentication.HttpRequestJWTUserProvider;
import com.gillion.eds.client.authentication.JWTEdsUserInfoCollector;
import com.gillion.eds.client.authentication.RpcRequestJWTUserProvider;
import com.gillion.eds.sso.IUser;
import com.gillion.eds.sso.session.DefaultSessionIdentityParser;
import com.share.support.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 * 描述：推送中心入口
 * </pre>
 * @author daoService
 */
@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        FreeMarkerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        ECMybatisConfiguration.class
},
        scanBasePackages = {"com.gillion", "com.share"})
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactory", basePackages = {"com.gillion.platform", "com.gillion.ilp",
        "com.share"}, markerInterface = BaseMapper.class)
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.gillion", "com.share"})
@EnableTransactionManagement
@ServletComponentScan(basePackages = {"com.share.message.servlet", "com.share.message.filter"})
@EnableRedisHttpSession
@ComponentScan("com.share.support.config")
@ComponentScan("com.share.message")
public class MessageApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MessageApplication.class);
    }

    @Autowired
    private RedisConfig redisConfig;

    /**
     *实例restTemplate
     * @param builder
     * @return
     * @author wangcl
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    /**
     * 秘钥
     */
    public static final String SECRET_KEY = "DfEqd%AvjY1!pFEx*4g$E%hL77b#ecjR";

    @Bean
    HttpRequestJWTUserProvider httpRequestJWTUserProvider() {
        return new HttpRequestJWTUserProvider("access_token", User.class, SECRET_KEY);
    }

    @Bean
    RpcRequestJWTUserProvider rpcRequestJWTUserProvider() {
        return new RpcRequestJWTUserProvider("access_token", User.class, SECRET_KEY);
    }

    @Bean
    JWTEdsUserInfoCollector jwtEdsUserInfoCollector(HttpRequestJWTUserProvider httpRequestJWTUserProvider,
                                                    RpcRequestJWTUserProvider rpcRequestJWTUserProvider) {
        return new JWTEdsUserInfoCollector(httpRequestJWTUserProvider, rpcRequestJWTUserProvider, IUser.class, null, new DefaultSessionIdentityParser());

    }
}
