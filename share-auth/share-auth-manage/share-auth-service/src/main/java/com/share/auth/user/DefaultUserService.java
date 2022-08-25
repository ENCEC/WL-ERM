package com.share.auth.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.gillion.ec.core.security.IThreadLoggedUserPorter;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.core.security.UserService;
import com.gillion.ec.core.security.data.AclMode;
import com.gillion.ec.core.security.data.CrudType;
import com.gillion.ec.core.security.data.IDataPermission;
import com.gillion.ec.core.security.data.ITable;
import com.gillion.ec.core.utils.CookieUtils;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.constants.CodeFinal;
import com.share.auth.service.UemUserService;
import com.share.auth.util.RedisUtil;
import com.share.auth.util.ThreadLocalUtil;
import com.share.support.constants.UserConstant;
import com.share.support.model.Project;
import com.share.support.model.Role;
import com.share.support.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Service
@Slf4j
public class DefaultUserService implements UserService, UserInfoCollector, IThreadLoggedUserPorter<IUser> {

    /**
     * session的用户信息
     */
    private static final String USER = "user";

    /** 当前登录用户 */
    public static final String CURRENT_LOGIN_USER = "current_login_user";

    @Autowired
    private AuthCenterInterface authCenterInterface;

    @Autowired
    private UemUserService uemUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IUser findByUsername(String s) {
        return null;
    }

    @Override
    public Iterable<String> getAnonymousPermissionUrlPatterns() {
        return Lists.newArrayList();
    }

    @Override
    public Collection<ITable> getAnonymousDataPermissionTables() {
        return Lists.newArrayList();
    }

    @Override
    public Object getUserId() {
        final UserInfoModel currentLoginUser = (UserInfoModel) getCurrentLoginUser();
        return currentLoginUser.getUemUserId();
    }

    @Override
    public String getOfficeId() {
        final UserInfoModel currentLoginUser = (UserInfoModel) getCurrentLoginUser();
        return currentLoginUser.getBlindCompanny() == null ? "" : currentLoginUser.getBlindCompanny().toString();
    }

    @Override
    public IUser getCurrentLoginUser() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(requestAttributes)) {
                throw new NullPointerException("获取当前登录用户失败");
            }
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            log.info("Get user info befor:"+user);
            UserInfoModel spUser = new UserInfoModel();
            Cookie uidCookie = CookieUtils.getCookie("uid");
            if (user != null && uidCookie != null) {
                String uid = uidCookie.getValue();
                Long uemUserId = user.getUemUserId();
                if (!String.valueOf(uemUserId).equals(uid)) {
                    user = null;
                }
            }
            /* 从session里获取用户，如果获取不到，就先调用认证权限获取用户信息，并将用户信息保存到session */
//            AuthUserInfoModel authUserInfoModel = new AuthUserInfoModel();
            if (user == null) {
                user = authCenterInterface.getUserInfo();
                session.setAttribute(USER, user);
            }
            if (user == null) {
                return spUser;
            }
            String jsonUser = JSON.toJSONString(user);
            log.info("Get user info :"+jsonUser);
            spUser = JSON.parseObject(jsonUser, UserInfoModel.class);
//            BeanUtils.copyProperties(user, authUserInfoModel);

            final ArrayList<IDataPermission> dataPermissions = new ArrayList<>();
            final ArrayList<ITable> permissionTables = new ArrayList<>(0);
            checkSelectDataPermissions(dataPermissions, user);
            checkTablePermissions(permissionTables, dataPermissions);
            spUser.setPermissionTables(permissionTables);
            ThreadLocalUtil.set(CURRENT_LOGIN_USER, spUser);
            return spUser;
        } catch (Exception e) {
            log.error("获取当前登录用户信息失败：" + e.getMessage());
            return null;
        }
    }

    private List<Long> parseIdList(String idStr) {
        List<Long> idList = new ArrayList<>();
        if (StrUtil.isNotBlank(idStr)) {
            List<String> ids = StrUtil.splitTrim(idStr, ",");
            for (String id : ids) {
                idList.add(Long.parseLong(id));
            }
        }
        return idList;
    }

    private void checkSelectDataPermissions(ArrayList<IDataPermission> dataPermissions, User user) {
        List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            if ("ADMIN".equals(role.getRoleName())) {
                dataPermissions.add(new DataPermission("uem_user_id IS NOT NULL", CrudType.SELECT));
                dataPermissions.add(new DataPermission("uem_user_id IS NOT NULL", CrudType.DELETE));
                dataPermissions.add(new DataPermission("uem_user_id IS NOT NULL", CrudType.UPDATE));
                dataPermissions.add(new DataPermission("uem_user_id IS NOT NULL", CrudType.INSERT));
                return;
            }
        }
        List<Project> projectList = user.getProjectList();
        Set<Long> uemUserIdSet = new HashSet<>();
        for (Project project : projectList) {
            for (Role role : roleList) {
                String roleName = role.getRoleName();
                switch (roleName) {
                    case "DEMAND_LEADER":
                        uemUserIdSet.addAll(parseIdList(project.getGenDemandUsers()));
                        break;
                    case "DM":
                        uemUserIdSet.addAll(parseIdList(project.getGenDevUsers()));
                        break;
                    case "MD":
                    case "PD":
                    case "PM":
                    case "GM":
                    case "VGM":
                        uemUserIdSet.addAll(parseIdList(project.getGenDemandUsers()));
                        uemUserIdSet.addAll(parseIdList(project.getGenDemandUsers()));
                        uemUserIdSet.add(project.getChiefId());
                        uemUserIdSet.add(project.getDutyId());
                        uemUserIdSet.add(project.getDevDirectorId());
                        uemUserIdSet.add(project.getDemandId());
                        break;
                    case "DEV_STAFF":
                    case "DEMAND_STAFF":
                    default:
                        break;
                }
            }
        }
        uemUserIdSet.add(user.getUemUserId());
        if (!uemUserIdSet.isEmpty()) {
            String idListStr = StrUtil.join(",", uemUserIdSet);
            idListStr = StrUtil.wrap(idListStr, "(", ")");
            dataPermissions.add(new DataPermission("uem_user_id IN " + idListStr, CrudType.SELECT));
            dataPermissions.add(new DataPermission("uem_user_id IN " + idListStr, CrudType.DELETE));
            dataPermissions.add(new DataPermission("uem_user_id IN " + idListStr, CrudType.UPDATE));
        }
    }

    private void checkTablePermissions(ArrayList<ITable> permissionTables, ArrayList<IDataPermission> dataPermissions) {
        permissionTables.add(new TablePermission("uem_user", AclMode.BLACK_LIST, dataPermissions));
    }

    /**
     * @Author:chenxf
     * @Description: 刷新缓存中的用户信息
     * @Date: 20:59 2021/1/22
     * @Param: []
     * @Return:void
     */
    public void updateCurrentLoginUser() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(requestAttributes)) {
                throw new NullPointerException("获取当前登录用户失败");
            }
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            /* 从session里获取用户，如果获取不到，就先调用认证权限获取用户信息，并将用户信息保存到session */
            if (user == null) {
                user = authCenterInterface.getUserInfo();
                session.setAttribute(USER, user);
            } else {
                user = uemUserService.getUserAllInfo(user.getUemUserId(), null);
            }
            session.setAttribute(USER, user);
        } catch (Exception e) {
            log.error("更新当前登录用户信息失败：" + e.getMessage());
        }
    }

    /**
     * 拼接cookie信息
     *
     * @param request
     * @return
     * @author wangcl
     */
    public List<String> getCookieList(HttpServletRequest request) {
        List<String> cookieList = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return cookieList;
        }
        for (Cookie cookie : cookies) {
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }
        return cookieList;
    }

    /**
     * 登出接口
     *
     * @param request
     * @author wangcl
     */
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 清除session
        session.removeAttribute(UserConstant.USER);
        Cookie jwt = CookieUtils.getCookie(CodeFinal.ACCESS_TOKEN);
        if (Objects.nonNull(jwt)) {
            String credential = jwt.getValue();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(credential)) {
                String digest = DigestUtils.md5Hex(credential);
                redisUtil.setForTimeSecs(digest, CodeFinal.DISABLED_JWT, 3600);
            }
        }
        session.invalidate();
        authCenterInterface.logout();
    }

    @Override
    public IUser getCurrentThreadLoggedUser() {
        return ThreadLocalUtil.get(CURRENT_LOGIN_USER);
    }

    @Override
    public void registerLoggedUserToCurrentThread(IUser iUser) {
        ThreadLocalUtil.set(CURRENT_LOGIN_USER,iUser);
    }
}
