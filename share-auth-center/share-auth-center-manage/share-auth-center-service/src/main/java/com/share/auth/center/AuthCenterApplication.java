package com.share.auth.center;


import com.gillion.ds.cache.config.RedisConfig;
import com.gillion.ec.core.data.BaseMapper;
import com.gillion.ec.mybatis.ECMybatisConfiguration;
import com.gillion.eds.client.authentication.HttpRequestJWTUserProvider;
import com.gillion.eds.client.authentication.JWTEdsUserInfoCollector;
import com.gillion.eds.client.authentication.RpcRequestJWTUserProvider;
import com.gillion.eds.sso.IUser;
import com.gillion.eds.sso.session.DefaultSessionIdentityParser;
import com.gillion.saas.redis.SassRedisClusterImpl;
import com.google.common.collect.Lists;
import com.share.support.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.*;
import com.gillion.saas.redis.SassRedisImpl;
import com.gillion.saas.redis.SassRedisInterface;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@ServletComponentScan
@EnableRedisHttpSession
public class AuthCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCenterApplication.class, args);
    }

    @Autowired
    private RedisConfig redisConfig;

    @Value("${redis.maxRedirects:8}")
    private int maxRedirects;

    /**
     * jedis连接池
     * @return
     */
    @Bean(name = "poolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig bean = new JedisPoolConfig();
        bean.setMaxTotal(redisConfig.getMaxTotal());
        bean.setMaxIdle(redisConfig.getMaxIdle());
        return bean;
    }

    @Bean
    public SassRedisInterface sassRedisInterface(JedisPoolConfig jedisPoolConfig) {
        if(redisConfig.isCluster()){
            Set<HostAndPort> hostAndPortSet = new HashSet<>();
            String[] nodes= redisConfig.getHostAndPort().split(",");
            for (String node : nodes) {
                String[] nodeStr = node.split(":");
                hostAndPortSet.add(new HostAndPort(nodeStr[0], Integer.parseInt(nodeStr[1])));
            }
            JedisCluster jedisCluster = new JedisCluster(hostAndPortSet,redisConfig.getConnectionTimeout(),redisConfig.getSoTimeout(),maxRedirects,redisConfig.getPassword(),jedisPoolConfig);
            return StringUtils.isNotBlank(redisConfig.getNamespace())
                    ? new SassRedisClusterImpl(jedisCluster, redisConfig.getNamespace())
                    : new SassRedisClusterImpl(jedisCluster);
        }else{
            String[] hostAndPort = redisConfig.getHostAndPort().split(":");
            GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
            JedisShardInfo jedisShardInfo = new JedisShardInfo(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
            jedisShardInfo.setPassword(redisConfig.getPassword());
            List<JedisShardInfo> shardInfos = Lists.newArrayList(jedisShardInfo);
            ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shardInfos);
            return StringUtils.isNotBlank(redisConfig.getNamespace())
                    ? new SassRedisImpl(shardedJedisPool, redisConfig.getNamespace())
                    : new SassRedisImpl(shardedJedisPool);
        }
    }

    /**
     * 秘钥
     */
    public static final String SECRET_KEY = "DfEqd%AvjY1!pFEx*4g$E%hL77b#ecjR";

    @Bean
    HttpRequestJWTUserProvider httpRequestJWTUserProvider() {
        return new HttpRequestJWTUserProvider("access-token", User.class, SECRET_KEY);
    }

    @Bean
    RpcRequestJWTUserProvider rpcRequestJWTUserProvider() {
        return new RpcRequestJWTUserProvider("access-token", User.class, SECRET_KEY);
    }

    @Bean
    JWTEdsUserInfoCollector jwtEdsUserInfoCollector(HttpRequestJWTUserProvider httpRequestJWTUserProvider,
                                                    RpcRequestJWTUserProvider rpcRequestJWTUserProvider) {
        return new JWTEdsUserInfoCollector(httpRequestJWTUserProvider, rpcRequestJWTUserProvider, IUser.class, null, new DefaultSessionIdentityParser());

    }
}
