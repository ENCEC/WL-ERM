package com.gillion.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.sql.DataSource;
import java.util.Locale;

/**
 * Created with ec-wcs-security.
 * User:marcos
 * Date:2020/12/12
 * Time:6:52 PM
 * Description:
 */
@Configuration
public class DaoServiceConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
        cookieLocaleResolver.setCookieName("locale");
        return cookieLocaleResolver;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("locale");
        registry.addInterceptor(lci).addPathPatterns("/**");
    }


    @Primary
    @Bean(name = "quickstartDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quickstartDataSource() {
        final DruidDataSource quickstartDataSource = new DruidDataSource();
        /*quickstartDataSource.setUrl(url);
        quickstartDataSource.setDriverClassName(driverClassName);
        quickstartDataSource.setUsername(username);
        quickstartDataSource.setPassword(password);*/
        return quickstartDataSource;
    }



    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
