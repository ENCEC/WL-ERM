package com.gillion.service.impl;

import com.gillion.cache.api.annotations.CacheEnable;
import com.gillion.cache.api.annotations.L2CacheEnable;
import com.gillion.ec.core.utils.ContextHolder;
import com.gillion.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 3:30 下午
 * @email wengms@gillion.com.cn
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@CacheEnable(key = "#username",value = "USER_URL_PERMISSION")
   // @L2CacheEnable
    @Override
    public List<String> getPermissionUrlByUserName(String username) {
        log.info("获取用户:{}的访问权限列表",username);
        JdbcTemplate jdbcTemplate = ContextHolder.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForList("SELECT URL FROM sys_resource WHERE RESOURCE_ID IN (SELECT RESOURCE_ID FROM sys_role_resource WHERE ROLE_ID IN (SELECT ROLE_ID FROM sys_role_user WHERE USER_ID = (SELECT USER_ID FROM sys_user WHERE USERNAME = ?)))", String.class, username);
    }
}
