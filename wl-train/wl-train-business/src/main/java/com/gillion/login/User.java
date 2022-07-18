package com.gillion.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gillion.ec.core.security.IRole;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.data.ITable;
import com.gillion.ec.core.utils.ContextHolder;
import com.gillion.permission.DataPermissionUtils;
import com.gillion.service.PermissionService;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.lambda.Seq;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户操作资源类
 *
 * @author wengms
 * @date 2:50 PM 2018/12/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements IUser {

    private String userId;
    private String username;
    private String password;
    private Collection<Role> roles;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Object getUserId() {
        return this.userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<IRole> getAuthorities() {
        return Seq.seq(this.roles)
                .map(role -> (IRole)role)
                .collect(Collectors.toList());
    }

    /**
     * 将Iterable转成collection
     * @param roles
     */
    @JsonIgnore
    @Override
    public void setAuthorities(Iterable<? extends IRole> roles) {
        this.roles = Seq.seq(roles)
                .map(role -> (Role) role)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /**
     * 获取用户功能权限信息
     * TODO 用户权限信息尽量不要直接在登录用户信息里写死，
     * TODO 最好动态进行调用，不建议在此处写sql和查询操作，建议放入另外的服务或工具类中操作查询并返回
     * TODO 可参考下面一个方法数据权限的获取
     *
     * @param
     * @return java.util.Set<java.lang.String>
     * @author wengms
     * @date 4:09 PM 2018/12/19
     */
    @JsonIgnore
    @Override
    public Set<String> getHasPermissionUrlPatterns() {
        PermissionService permissionService = ContextHolder.getBean(PermissionService.class);
        List<String> urls = permissionService.getPermissionUrlByUserName(this.username);
        return Sets.newHashSet(urls);
    }

    /**
     * 获取用户数据权限信息
     *
     * @param
     * @return java.util.Collection<com.gillion.ec.core.security.data.ITable>
     * @author wengms
     * @date 4:10 PM 2018/12/19
     */
    @Override
    @JsonIgnore
    public Collection<ITable> getPermissionTables() {
        String[] roleIds = Seq
                .seq(this.roles)
                .map(Role->String.valueOf(Role.getRoleId()))
                .toArray(String[]::new);
        return DataPermissionUtils.getPermissionTableByRoles(roleIds);
    }


}
