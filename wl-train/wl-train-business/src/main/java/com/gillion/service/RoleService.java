package com.gillion.service;

import com.gillion.login.Role;

import java.util.List;

/**
 * 角色操作类
 * @author wengms
 * @date 2018/12/19 3:36 PM
 * @email wengms@gillion.com.cn
 */
public interface RoleService {
    /**
     * 获取指定用户的角色信息
     *
     * @author wengms
     * @date 3:38 PM 2018/12/19
     * @param userId
     * @return java.util.List<com.gillion.login.Role>
     */
    List<Role> findByUserId(String userId);

}
