package com.share.message.user;

import com.gillion.ec.core.security.IRole;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.data.ITable;
import com.share.support.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author tujx
 * @description 登录用户信息
 * @date 2020/11/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MsgUserInfoModel extends User implements Serializable, IUser,com.gillion.eds.sso.IUser {

    /**用于自定义通用字段*/
    private String companyName;

    @Override
    public Object getUserId() {
        return this.getUemUserId();
    }

    @Override
    public Collection<IRole> getAuthorities() {
        return null;
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

    }

    @Override
    public Set<String> getHasPermissionUrlPatterns() {
        return null;
    }

    @Override
    public Collection<ITable> getPermissionTables() {
        return null;
    }
}
