package com.gillion.permission;

import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.security.data.enviroment.EnvironmentHolder;
import com.gillion.ec.security.data.enviroment.Scope;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 *  用户上下文实现，主要用于数据权限操作的方法拦截
 *  在数据权限使用中可通过：
 *  \@userScope.getUser()获取当前登录用户信息
 *  \@userScope.getSubordinateOrganization() 获取下级组织信息
 * @author wengms
 * @date 2:55 PM 2018/12/19
 */
@Service
public class UserScope implements Scope, InitializingBean {

    @Setter
    private UserInfoCollector userInfoCollector;

    /**
     * 获取当前登录用户信息
     *
     * @author wengms
     * @date 2:55 PM 2018/12/19
     * @param
     * @return com.gillion.ec.core.security.IUser
     */
    public IUser getUser() {
        return userInfoCollector.getCurrentLoginUser();
    }

    /**
     *
     * 获取下级组织ID信息
     * @author wengms
     * @date 2:55 PM 2018/12/19
     * @param
     * @return java.util.List<java.lang.String>
     */
    public List<String> getSubordinateOrganization() {
        return Arrays.asList("1", "2");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将当前scope注册到数据权限上下文环境中，提供使用
        EnvironmentHolder.getContext().put("userScope", this);
    }
}
