package com.share.auth.center.user;

import com.gillion.devops.authentication.session.Session;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.core.security.UserService;
import com.gillion.ec.core.security.data.ITable;
import com.gillion.ec.core.utils.UserUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by wengms.
 * Email: wengms@gillion.com.cn
 * Date: 2018/4/3
 * Time: 下午9:55
 * Description:
 * @author Administrator
 */

@Service
public class DefaultUserInfoCollector implements UserService,UserInfoCollector {

    @SuppressWarnings("FieldMayBeFinal")
    @Value("${dao-service.allowAnonymousUser}")
    private boolean allowAnonymousUser = false;

    @Override
    public IUser findByUsername(String s) {
        return null;
    }

    @Override
    public Iterable<String> getAnonymousPermissionUrlPatterns() {
        return org.apache.commons.compress.utils.Lists.newArrayList();
    }

    @Override
    public Collection<ITable> getAnonymousDataPermissionTables() {
        return Lists.newArrayList();
    }

    @Override
    public Object getUserId() {
        Object userId = null;
        try {
            userId = Session.get("userId");
        } catch (Exception e) {
            if (allowAnonymousUser) {
                return getAnonymousUser().getUserId();
            } else {
                throw e;
            }
        }
        return userId;
    }

    @Override
    public String getOfficeId() {
        try {
            return Session.get("officeId");
        } catch (Exception e) {
            if (allowAnonymousUser) {
                return "";
            } else {
                throw e;
            }
        }
    }

    /**
     * 数据权限和功能权限根据当前登录用户做权限控制
     *
     * @return
     */


    @Override
    public IUser getCurrentLoginUser() {
        Object userId = null;
        try {
            userId = Session.get("userId");
        } catch (Exception ignored) {
        }
        if (userId == null) {
            if (allowAnonymousUser) {
                return getAnonymousUser();
            } else {
                return null;
            }
        }

        return getSsoLoginUser();
    }

    private IUser getSsoLoginUser() {
        return UserUtils.anonymousUser;
//        try {
//            return Session.<User>getCurrentUser();
//        } catch (Exception e) {
//            if (allowAnonymousUser) {
//                return getAnonymousUser();
//            } else {
//                throw e;
//            }
//        }
    }

    private IUser getAnonymousUser() {
        return UserUtils.anonymousUser;
//        String userId = UserUtils.anonymousUsername;
//        User user = new User();
//        user.setUserId(userId);
//
//        // 数据权限
//        List<ITable> permissionTables = Lists.newArrayList();
//        user.setPermissionTables(permissionTables);
//
//        return user;
    }

}
