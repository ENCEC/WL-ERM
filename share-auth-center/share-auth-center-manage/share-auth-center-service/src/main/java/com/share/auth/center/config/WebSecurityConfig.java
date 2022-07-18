package com.share.auth.center.config;

import com.share.auth.center.handler.*;
import com.share.auth.center.handler.LoginFilter;
import com.share.auth.center.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Author:chenxf
 * @Description: springsercurity配置
 * @Date: 11:39 2020/10/21
 * @Param: 
 * @Return:
 *
 */
@Configuration
@Order(1)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @Author:chenxf
     * @Description: 用户登录处理service
     * @Date: 11:47 2020/10/21
     * @Param: 
     * @Return:
     *
     */
    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    @Autowired
    private LogoutSuccessHandler myLogoutHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Value("${aes_secret_key}")
    private String aesSecretKey;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**", "/getUserInfo","/validateUser","/system/security/**","/system/security/getSessionAttrsAndNoPermits","/getToken", "/oauth2/auth/authentication","/validateUnlock","/oauthSSO/render","/oauthSSO/callback","/createCredential","/parseCredential","/createCredentialCookie","/sysPublishConfig/getTimes");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and().logout().logoutSuccessHandler(myLogoutHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/getUserInfo", "/validateUser", "/system/security/getSessionAttrsAndNoPermits", "/system/security/**", "/getToken", "/oauth2/auth/authentication", "/validateUnlock", "/oauthSSO/render", "/oauthSSO/callback").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable().cors();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**登录自定义过滤器*/
    private LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAesSecretKey(aesSecretKey);
        return loginFilter;
    }

}
