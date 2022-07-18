package com.gillion.cache;

import com.gillion.login.User;

/**
 * @author wengms
 * @date 2021/1/11 3:51 下午
 * @email wengms@gillion.com.cn
 */
public interface UserCacheService {
    /**
     * 获取用户
     * @param username
     * @return
     */
    User get(String username);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User update(User user);


    /**
     * 移除用户
     * @param username
     * @return
     */
    User remove(String username);
}
