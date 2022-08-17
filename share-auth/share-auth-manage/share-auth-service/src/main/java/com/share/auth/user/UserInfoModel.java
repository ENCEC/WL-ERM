package com.share.auth.user;

import com.gillion.ec.core.security.IRole;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.data.ITable;
import com.share.support.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author tujx
 * @description 登录用户信息
 * @date 2020/11/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoModel extends User implements Serializable, IUser,com.gillion.eds.sso.IUser {

    /**用于自定义通用字段*/
    private String companyName;

    private Collection<ITable> permissionTables;

    @Override
    public Object getUserId() {
        return this.getUemUserId();
    }

    @Override
    public Collection<IRole> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setAuthorities(Iterable<? extends IRole> iterable) {
        // 无需实现
    }

    @Override
    public Set<String> getHasPermissionUrlPatterns() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ITable> getPermissionTables() {
        return permissionTables;
    }

    public void setPermissionTables(Collection<ITable> permissionTables) {
        this.permissionTables = permissionTables;
    }
}
