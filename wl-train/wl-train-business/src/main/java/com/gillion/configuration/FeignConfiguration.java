package com.gillion.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/4
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                Enumeration<String> attributeNames = request.getHeaderNames();
                if (attributeNames != null) {
                    while (attributeNames.hasMoreElements()) {
                        String name = attributeNames.nextElement();
                        String value = request.getHeader(name);
                        if (name.equals("content-length")){
                            continue;
                        }
                        requestTemplate.header(name,value);
                    }
                }
            }
        };
    }
}