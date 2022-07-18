package com.share.auth.center.credential;


import com.share.support.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 凭证接口类
 * @author wengms
 * @date 2018/12/11 11:43 PM
 * @email wengms@gillion.com.cn
 */
public interface CredentialProcessor {
    /**
     * 创建凭证
     * @param userInfo userInfo
     * @return  String
     */
    String createCredential(Object userInfo);

    /**
     * 投递凭证
     * @param response response
     * @param credential credential
     * @param uid uid
     */
    void deliveryCredential(HttpServletResponse response, String credential, String uid);



    /**
     * 解析凭证
     * @param request 请求
     * @return 凭证对象，一般为一个用户信息
     */
    User parseCredential(HttpServletRequest request);

    /**
     * 解析凭证
     * @param credential credential
     * @return 凭证对象，一般为一个用户信息
     */
    User parseCredential(String credential);


    /**
     * 刷新凭证
     * @param response response
     * @param credential credential
     */
    void refreshCredential(HttpServletResponse response, String credential) ;


    /**
     * 销毁凭证
     * @param response response
     * @param credential credential
     */
    void destroy(HttpServletResponse response, String credential);


}
