package com.gillion.service.impl;

import com.gillion.login.Role;
import com.gillion.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息获取类
 * @author wengms
 * @date 2018/12/19 3:38 PM
 * @email wengms@gillion.com.cn
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Role> findByUserId(String userId) {
        return jdbcTemplate.query("select ROLE_ID from sys_role_user where USER_ID = ?", BeanPropertyRowMapper.newInstance(Role.class), userId);
    }
}
