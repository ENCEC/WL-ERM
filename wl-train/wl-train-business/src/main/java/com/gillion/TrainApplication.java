package com.gillion;


import com.gillion.ec.core.exceptions.SystemRuntimeException;
import com.gillion.ec.mq.EnableMQ;
import com.gillion.ec.mq.EnableMQConfigServer;
import com.gillion.ec.mq.EnableMQDashboard;
import com.gillion.ec.rule.number.generator.client.protocol.EnableFeignRuleNumberClient;
import com.gillion.ec.scheduler.EnableSchedulerSupervisor;
import com.gillion.ec.scheduler.controller.EnableSchedulerDashboard;
import com.gillion.ec.scheduler.worker.EnableSchedulerWorker;
import com.gillion.eds.client.authentication.HttpRequestJWTUserProvider;
import com.gillion.eds.client.authentication.JWTEdsUserInfoCollector;
import com.gillion.eds.client.authentication.RpcRequestJWTUserProvider;
import com.gillion.eds.sso.IUser;
import com.gillion.eds.sso.session.DefaultSessionIdentityParser;
import com.share.support.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * @author wengms
 * @date 7:25 PM 2018/12/19
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.gillion",
                "com.gillion.ds.excel.export.controller"
        })
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.gillion.ec.rule.number", "com.gillion.ds", "com.gillion.service", "com.share"})
@EnableFeignRuleNumberClient
@EnableSchedulerSupervisor
@EnableSchedulerDashboard
@EnableSchedulerWorker
@EnableTransactionManagement
@Slf4j
@EnableMQ("com.gillion")
//@EnableMQConfigServer
//@EnableMQDashboard
public class TrainApplication {


    public static void main(String... args) {
        try {
            ApplicationContext applicationContext = SpringApplication.run(TrainApplication.class, args);
            log.info("business start.");
        } catch (Exception e) {
            log.error("应用启动失败", e);
            throw new SystemRuntimeException("应用启动失败", e);
        }
    }

    @Bean
    DataSourceTransactionManager transactionManager(DataSource dataSource,
                                                    ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
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

