package com.gillion.login;

import com.gillion.ec.core.security.IRole;
import com.gillion.ec.core.security.data.ITable;

import java.util.Collection;
import java.util.Collections;

/**
 * 角色实体类
 *
 * @author wengms
 * @date 2:47 PM 2018/12/19
 */
public class Role implements IRole {
    private Long roleId;

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public Object getRoleId() {
        return this.roleId;
    }

    /**
     * 暂时不实现，只返回空
     *
     * @param
     * @return java.util.Collection<com.gillion.ec.core.security.data.ITable>
     * @author wengms
     * @date 2:47 PM 2018/12/19
     * @deprecated 暂时无用
     */
    @Deprecated
    @Override
    public Collection<ITable> getPermissionTables() {
        return Collections.emptyList();
    }

    public String getAuthority() {
        return "";
    }
}
