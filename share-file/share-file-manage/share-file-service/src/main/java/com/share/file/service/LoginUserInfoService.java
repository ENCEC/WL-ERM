package com.share.file.service;

import java.util.Map;

/**
 * @author tujx
 * @description 当前登录信息
 * @date 2020/11/26
 */
public interface LoginUserInfoService {

    /**
     * 获取当前登录用户信息
     *
     * @param
     * @return Map
     * @throws
     * @author tujx
     */
    Map<String,Object> getCurrentLoginUser();
}
