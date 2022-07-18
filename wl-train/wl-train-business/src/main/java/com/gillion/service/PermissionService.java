package com.gillion.service;

import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 3:29 下午
 * @email wengms@gillion.com.cn
 */
public interface PermissionService {

    /**
     * 通过用户名获取有权限的url列表
     * @param username
     * @return
     */
    List<String> getPermissionUrlByUserName(String username);
}
