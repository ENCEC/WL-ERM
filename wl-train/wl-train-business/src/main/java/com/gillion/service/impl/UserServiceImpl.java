//package com.gillion.service.impl;
//
//import com.gillion.ec.core.security.IUser;
//import com.gillion.ec.core.security.UserService;
//import com.gillion.ec.core.security.data.ITable;
//import com.gillion.ec.core.utils.Collections3;
//import com.gillion.ec.core.utils.CookieUtils;
//import com.gillion.ec.core.utils.RequestAndResponseContextHolder;
//import com.gillion.ec.core.utils.UserUtils;
//import com.gillion.login.CredentialManager;
//import com.gillion.login.Role;
//import com.gillion.login.User;
//import com.gillion.permission.DataPermissionUtils;
//import com.gillion.service.LoginService;
//import com.gillion.service.RoleService;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.Validate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.DigestUtils;
//
//import javax.servlet.http.Cookie;
//import java.nio.charset.StandardCharsets;
//import java.util.Collection;
//import java.util.List;
//
///**
// * 用户操作服务类
// *
// * @author wengms
// * @date 2:48 PM 2018/12/19
// */
//@Service
//public class UserServiceImpl implements UserService, LoginService {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private CredentialManager credentialManager;
//
//    @Autowired
//    private RoleService roleService;
//
//
//    /**
//     * 根据用户名sql查询表获取用户信息
//     * @param username
//     * @return
//     */
//    @Override
//    public IUser findByUsername(String username) {
//        final List<User> users = jdbcTemplate.query("SELECT * FROM sys_user WHERE USERNAME = ?", BeanPropertyRowMapper.newInstance(User.class), username);
//        final User user = Collections3.getFirst(users);
//
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("不存在用户名为 %s 的用户", username));
//        }
//        List<Role> roles = jdbcTemplate.query("SELECT role_id FROM sys_role_user where user_id = ?",BeanPropertyRowMapper.newInstance(Role.class),user.getUserId());
//        user.setAuthorities(roles);
//        return user;
//    }
//
//    /**
//     * 获取匿名用户功能权限信息
//     *
//     * @param
//     * @return java.lang.Iterable<java.lang.String>
//     * @author wengms
//     * @date 2:50 PM 2018/12/19
//     */
//    @Override
//    public Iterable<String> getAnonymousPermissionUrlPatterns() {
//        return jdbcTemplate.queryForList("SELECT URL FROM sys_resource WHERE RESOURCE_ID IN (SELECT RESOURCE_ID FROM sys_role_resource WHERE ROLE_ID = ?)", String.class, UserUtils.ANONYMOUS_ROLE_ID);
//    }
//
//    /**
//     * 获取匿名用户数据权限信息
//     *
//     * @param
//     * @return java.util.Collection<com.gillion.ec.core.security.data.ITable>
//     * @author wengms
//     * @date 2:50 PM 2018/12/19
//     */
//    @Override
//    public Collection<ITable> getAnonymousDataPermissionTables() {
//        return DataPermissionUtils.getPermissionTableByRoles(UserUtils.ANONYMOUS_ROLE_ID);
//    }
//
//    @Override
//    public void login(User user) {
//        Validate.notEmpty(user.getUsername(),"用户名不能为空");
//        Validate.notEmpty(user.getPassword(),"密码不能为空");
//
//        User userInDb = (User) this.findByUsername(user.getUsername());
//        Validate.notNull(userInDb,"用户名不存在");
//
//        //对密码进行md5,并匹配正确性
//        String digestPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
//        Validate.isTrue(StringUtils.equals(digestPassword,userInDb.getPassword()),"密码错误");
//
//        //5、申请凭证
//        String ticket = credentialManager.create(userInDb);
//        //6、绑定凭证
//        bindingCredential(ticket);
//    }
//
//    /**
//     * 绑定凭证->将凭证绑定到cookie中，用于会话传递
//     *
//     * @param ticket
//     * @return void
//     * @author wengms
//     * @date 3:45 PM 2018/12/19
//     */
//    private void bindingCredential(String ticket) {
//        Cookie cookie = new Cookie("ticket", ticket);
//        cookie.setMaxAge(credentialManager.getExpireTime());
//        cookie.setPath("/");
//        RequestAndResponseContextHolder.response().addCookie(cookie);
//    }
//
//    @Override
//    public void logout() {
//        Cookie ticketCookie = CookieUtils.getCookie("ticket");
//        if (ticketCookie != null) {
//            String ticket = ticketCookie.getValue();
//            credentialManager.destroy(ticket);
//            ticketCookie.setMaxAge(0);
//            ticketCookie.setPath("/");
//            RequestAndResponseContextHolder.response().addCookie(ticketCookie);
//        }
//    }
//}
