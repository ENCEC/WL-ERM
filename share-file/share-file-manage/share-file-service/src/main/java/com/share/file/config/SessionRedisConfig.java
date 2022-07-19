/**
 *
 */
package com.share.file.config;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** 
* @version:1.0
* @Description: 修改默认的spring sesion redis connectFactory配置
* @author: SYF
* @date: 2020年11月2日上午10:16:44
*/
@Configuration
public class SessionRedisConfig {

    /**
     * 以下配置为 吉联框架自定义配置路径 ,默认值采用默认配置
	 */
	@Value("${redis.hostAndPort}")
	private String hostAndPort;

	@Value("${redis.connectionTimeout:2000}")
	private int connectionTimeout;
	
	@Value("${redis.soTimeout:10000}")
	private int soTimeout;

	@Value("${redis.password:#{null}}")
	private String password;

	@Value("${redis.database:6}")
	private int database;

	@Value("${redis.maxTotal:8}")
	private int maxTotal;

	@Value("${redis.maxIdle:8}")
	private int maxIdle;

	@Value("${redis.cluster:false}")
	private boolean cluster;

    /**
     * 重写连接信息
	 * @return
     */
    @Bean
    public RedisConnectionFactory connectionFactory() {
        //redis连接池配置
        GenericObjectPoolConfig<Object> poolConfig =new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(true);
    	//redis基础配置  根据是否是集群 返回不同的配置对象
        if(StringUtils.isEmpty(hostAndPort)) {
			throw new NullPointerException("redis.hostAndPort 不能为空");
		}
		RedisConfiguration redisConfiguration=null;
        if(cluster) {
			redisConfiguration = new RedisClusterConfiguration();
			String[] nodes = hostAndPort.split(",");
			List<RedisNode> list = new ArrayList<>();
			for (String node : nodes) {
				String[] nodeStr = node.split(":");
				RedisNode rn = new RedisNode(nodeStr[0], Integer.parseInt(nodeStr[1]));
				list.add(rn);
			}
			((RedisClusterConfiguration) redisConfiguration).setClusterNodes(list);
			if (StringUtils.isNotEmpty(password)) {
				((RedisClusterConfiguration) redisConfiguration).setPassword(password);
			}
		} else {
        	String[] hostStr=hostAndPort.split(":");
            redisConfiguration = new RedisStandaloneConfiguration(hostStr[0],Integer.parseInt(hostStr[1]));
        	((RedisStandaloneConfiguration) redisConfiguration).setDatabase(database);
        	if(StringUtils.isNotEmpty(password)) {
          		 ((RedisStandaloneConfiguration) redisConfiguration).setPassword(password);
           	}
        }
        //redis客户端配置
        LettucePoolingClientConfigurationBuilder  builder =  LettucePoolingClientConfiguration.builder();
        builder.commandTimeout(Duration.ofMillis(soTimeout));
        builder.shutdownTimeout(Duration.ofMillis(soTimeout));
        builder.poolConfig(poolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        //创建连接工厂
        return new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
	}
}
