package com.gillion.cache;

import com.gillion.cache.api.annotations.CacheDestroyer;
import com.gillion.cache.api.annotations.CacheEnable;
import com.gillion.cache.api.annotations.CacheUpdater;
import com.gillion.cache.api.annotations.L2CacheEnable;
import com.gillion.ec.core.security.UserService;
import com.gillion.login.User;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wengms
 * @date 2021/1/11 3:52 下午
 * @email wengms@gillion.com.cn
 */
@Service
@Slf4j
public class L2UserServiceImpl implements UserCacheService{
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // User_wengms  User  wengms
    @Override
    @L2CacheEnable
    @CacheEnable(value = "User", key = "#username", condition = "#username.length()>2")
    public User get(String username) {
        log.info("获取用户信息:{}",username);
        return (User) userService.findByUsername(username);
    }

    @Override
    @CacheUpdater(value = "User", key = "#user.username", condition = "#user.username.length()>2")
    public User update(User user) {
        log.info("更新用户信息:{}",user.getUsername());
        jdbcTemplate.update("update sys_user set password = ? where username = ?",user.getUsername(),user.getPassword());
        return user;
    }

    @Override
    @CacheDestroyer(value = "User", key = "#username", condition = "#username.length()>2")
    public User remove(String username) {
        //此处不移除，仅作为演示移除缓存示例
        log.info("移除用户信息:{}",username);
        return null;
    }
}
