package com.gillion.user;

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
 * @author chenxf
 * @date 2020-11-05 16:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthUserInfoModel extends User implements Serializable,IUser,com.gillion.eds.sso.IUser {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
        return true;
    }

    @Override
    public void setAuthorities(Iterable<? extends IRole> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getHasPermissionUrlPatterns() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ITable> getPermissionTables() {
        return Collections.emptyList();
    }
}
