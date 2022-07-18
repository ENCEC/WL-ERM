package com.share.auth.center.config;

import com.google.common.collect.Lists;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.service.impl.MyUserDetailsServiceImpl;
import com.share.auth.center.service.UemUserService;
import com.share.support.model.MyUser;
import com.share.support.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:chenxf
 * @Description: oauth2配置类
 * @Date: 11:37 2020/10/21
 * @Param: 
 * @Return:
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenStore customRedisStore;

    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private UemUserService uemUserService;
    @Value("${authentication.credentialExpireSeconds:1800}")
    private int credentialExpireSeconds;

    /**
     * @Author:chenxf
     * @Description: 安全配置
     * @Date: 11:38 2020/10/21
     * @Param: [security]
     * @Return:void
     *
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()");
    }

    /**
     * @Author:chenxf
     * @Description: 客户端配置，从数据库读取
     * @Date: 11:38 2020/10/21
     * @Param: [clients]
     * @Return:void
     *
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * @Author:chenxf
     * @Description: 认证端点配置
     * @Date: 11:38 2020/10/21
     * @Param: [endpoints]
     * @Return:void
     *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints.accessTokenConverter(jwtAccessTokenConverter).tokenEnhancer(tokenEnhancerChain);
        endpoints.tokenStore(jwtTokenStore);
        //设置userDetailsService刷新token时候会用到
        endpoints.userDetailsService(userDetailsService);
        // 配置TokenServices参数
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setAccessTokenValiditySeconds(credentialExpireSeconds);
        tokenServices.setRefreshTokenValiditySeconds(credentialExpireSeconds);
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        // 复用refresh token
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        endpoints.tokenServices(tokenServices);

        super.configure(endpoints);
    }


    /**
     * JWT内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            MyUser user = (MyUser) authentication.getUserAuthentication().getPrincipal();
            map.put("userId", user.getUserId());
            map.put("clientId", user.getClientId());
            // 根据用户Id的和clientId查出用户信息
            User userInfoModel = uemUserService.getUserInfo(user.getUserId(),user.getClientId());
            String jwt = credentialProcessor.createCredential(userInfoModel);
            map.put("jwt", jwt);
            List<GrantedAuthority> authorityList = Lists.newArrayList(user.getAuthorities());
            if (CollectionUtils.isNotEmpty(authorityList)){
                map.put("role",authorityList.get(0).getAuthority());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}
