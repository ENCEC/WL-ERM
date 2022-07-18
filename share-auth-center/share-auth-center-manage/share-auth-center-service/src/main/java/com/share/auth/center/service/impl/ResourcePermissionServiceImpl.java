package com.share.auth.center.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gillion.utils.CommonResult;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.share.auth.center.common.PatternPathMatcher;
import com.share.auth.center.constants.AuthenticationConfig;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.enums.GlobalEnum;
import com.share.auth.center.model.entity.OauthClientDetails;
import com.share.auth.center.model.entity.UemUserPermission;
import com.share.auth.center.model.entity.SysPlatformUser;
import com.share.auth.center.model.entity.UemUser;
import com.share.auth.center.model.entity.UemUserRole;
import com.share.auth.center.model.querymodels.QOauthClientDetails;
import com.share.auth.center.model.querymodels.QUemUserPermission;
import com.share.auth.center.model.querymodels.QSysPlatformUser;
import com.share.auth.center.model.querymodels.QUemUser;
import com.share.auth.center.model.querymodels.QUemUserRole;
import com.share.auth.center.service.ResourcePermissionService;
import com.share.auth.center.service.SysResourceService;
import com.share.auth.center.util.OauthClientUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.lambda.Seq;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author:chenxf
 * @Description: 资源相关服务
 * @Date: 16:09 2021/1/18
 * @Param:
 * @Return:
 */
@Service
@Slf4j
public class ResourcePermissionServiceImpl implements ResourcePermissionService, InitializingBean {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private AuthenticationConfig authenticationConfig;

    private static final String ALL_RESOURCE_KEY = "ALL_RESOURCE_KEY";

    /**
     * 白名单集合
     */
    private Set<PatternPathMatcher> ignorePathPatternMatchers;

    /** 国家综合交通运输信息平台用户允许访问账号系统的url*/
    private Set<PatternPathMatcher> imtpUserAllowPathPatternMatchers;

    /**
     * 资源缓存
     */
    private LoadingCache<String, Set<PatternPathMatcher>> resourcePermissionCaches;

    /**
     * 应用角色资源缓存
     */
    private LoadingCache<Pair<String, String>, Set<PatternPathMatcher>> resourcePermissionByAppCodeCaches;

    @Value("${sso.login.errorPage}")
    private String errorPage;

    @Value("${sso.login.permissionApplyPage}")
    private String permissionApplyPage;

    @Value("${sso.login.permissionApplyingPage}")
    private String permissionApplyingPage;

    /**
     * @Author:chenxf
     * @Description: 初始化缓存数据
     * @Date: 16:10 2021/1/18
     * @Param: []
     * @Return:
     */
    public ResourcePermissionServiceImpl() {
        resourcePermissionCaches = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Set<PatternPathMatcher>>() {

                    @Override
                    public Set<PatternPathMatcher> load(String key) {
                        if (StringUtils.equals(key, ALL_RESOURCE_KEY)) {
                            return Seq.seq(sysResourceService.findAllByIsValid())
                                    .map(PatternPathMatcher::new)
                                    .toSet();
                        } else {
                            log.info("加载角色:{}功能权限信息", key);
                            //直接从数据库中获取，不要从redisInterface中获取
                            return Seq.seq(sysResourceService.getResourcesByRoleId(key, GlobalEnum.ResourceTypeEnum.INTERFACE.getCode()))
                                    .map(PatternPathMatcher::new)
                                    .toSet();
                        }
                    }
                });
        resourcePermissionByAppCodeCaches = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<Pair<String, String>, Set<PatternPathMatcher>>() {

                    @Override
                    public Set<PatternPathMatcher> load(Pair<String, String> key) {
                        if (StringUtils.equals(key.getValue(), ALL_RESOURCE_KEY)) {
                            return Seq.seq(sysResourceService.findAllByIsValid())
                                    .map(PatternPathMatcher::new)
                                    .toSet();
                        } else {
                            log.info("加载appCode:{},角色:{}功能权限信息", key.getKey(), key.getValue());
                            //直接从数据库中获取，不要从redisInterface中获取
                            return Seq.seq(sysResourceService.getResourcesByClientIdAndRoleId(key.getKey(), key.getValue()))
                                    .map(PatternPathMatcher::new)
                                    .toSet();
                        }
                    }
                });
    }


    /**
     * @Author:chenxf
     * @Description: 后端接口校验接口
     * @Date: 16:10 2021/1/18
     * @Param: [params]
     * @Return:com.gillion.utils.CommonResult
     */
    @Override
    public CommonResult validate(String[] params) {
        log.info("---请求到validate方法！！---");
        int paramsRequireLength = 2;
        if (params.length != paramsRequireLength) {
            return CommonResult.failure().errorMessages("参数错误，无法访问资源");
        }
        String path = params[0];
        String credential = params[1];
        log.info("---请求url为：" + path + "---");
        //1. 判断当前路径是否在忽略列表中，若在忽略列表中，则返回成功
        if (isInIgnorePathPattern(path)) {
            return CommonResult.success();
        }
        // 验证请求是否已经登出
        if (StringUtils.isNotEmpty(credential) && isLogout(credential)) {
            credential = null;
        }
        //2. 判断用户是否已经登录，若开启所有资源访问都需要登录判定，没登录时直接返回失败
        User userInfoModel = credentialProcessor.parseCredential(credential);
        if (authenticationConfig.isEveryResourceRequireLogin() || userInfoModel == null) {
            return CommonResult.failure().errorMessages("访问受限，用户未登陆无法访问地址" + path);
        }
        // 校验用户信息
        if (Objects.isNull(userInfoModel.getUemUserId())) {
            return CommonResult.failure().errorMessages("用户id为空，无法访问资源");
        }
        // 校验用户是否存在
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(userInfoModel.getUemUserId());
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
        if (Objects.isNull(uemUser) && Objects.isNull(sysPlatformUser)) {
            log.error("用户id：{}，对应的用户信息不存在", userInfoModel.getUemUserId());
            return CommonResult.failure().errorMessages("用户不存在，无法访问资源");
        }

        //3. 判断登录的应用和角色
        List<Long> sysApplicationIdList = sysResourceService.findByPath(path);
        if (CollectionUtils.isEmpty(sysApplicationIdList)) {
            return CommonResult.failure().errorMessages("访问受限，接口未配置无法访问地址" + path);
        }
        // 多个应用使用该接口，暂时先直接返回成功
        if (sysApplicationIdList.size() > 1) {
            return CommonResult.success();
        }
        // 当前登录应用
        Long sysApplicationId = sysApplicationIdList.get(0);
        // 允许所有用户登录的客户端id
        List<Long> allowAllClientIdList = OauthClientUtils.getAllowAllClientId();
        if (allowAllClientIdList.contains(sysApplicationId)) {
            // 国家综合交通运输信息平台只允许访问申请权限接口
            if (Objects.equals(GlobalEnum.UserSource.NTIP.getCode(), userInfoModel.getSource()) && !this.isImtpUserAllowPath(path)) {
                return CommonResult.failure().errorMessages("302", this.getRedirectUrlByError(GlobalEnum.AuthErrorEnum.ACCESS_LIMIT, sysApplicationId));
            }
            // 其他用户可以访问
            return CommonResult.success();
        }
        // 国交管理员只能登录账号系统
        if (Objects.equals(userInfoModel.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
            return CommonResult.failure().errorMessages("访问受限，国交管理员只能访问账号系统" + path);
        }
        // 只允许管理员登录的客户端id
        List<Long> allowAdminClientIdList = OauthClientUtils.getAllowAdminClientId();
        Long customerServiceRoleId = 0L;
        if (allowAdminClientIdList.contains(sysApplicationId)) {
            // 国家综合交通运输信息平台不允许访问公共服务
            if (Objects.equals(GlobalEnum.UserSource.NTIP.getCode(), userInfoModel.getSource())) {
                return CommonResult.failure().errorMessages("302", this.getRedirectUrlByError(GlobalEnum.AuthErrorEnum.ACCESS_LIMIT, sysApplicationId));
            }
            // 系统其它用户
            if (!customerServiceRoleId.equals(userInfoModel.getSysRoleId())) {
                return CommonResult.failure().errorMessages("访问受限，非平台客服登录公共服务系统"  + path);
            } else {
                return CommonResult.success();
            }
        }
        // 允许没有角色的账号登录
        List<Long> allowNoRoleClientIdList = OauthClientUtils.getAllowNoRoleClientId();
        // 平台客服登录应急系统
        Long loginkEmergency = 6742407760051060736L;
        if (customerServiceRoleId.equals(userInfoModel.getSysRoleId())) {
            // 客服是否登录需要角色的应用
            if (!allowNoRoleClientIdList.contains(sysApplicationId) && !Objects.equals(loginkEmergency, sysApplicationId)) {
                return CommonResult.failure().errorMessages("平台客服无法访问其它系统。");
            }
        }
        //4.当未配置资源时，两种策略处理判定，1、直接返回成功 2、返回失败
        Set<PatternPathMatcher> allResourceMatchers = resourcePermissionCaches.getUnchecked(ALL_RESOURCE_KEY);
        boolean requestInDefinitions = Seq.seq(allResourceMatchers)
                .anyMatch(patternPathMatcher -> patternPathMatcher.match(path));
        if (!requestInDefinitions) {
            if (authenticationConfig.isFailedWhenResourceNotFound()) {
                return CommonResult.failure().errorMessages("访问受限，用户没有访问权限" + path);
            } else {
                return CommonResult.success();
            }
        }
        //4、校验用户的访问权限：a、匿名用户 b、正常用户
        return validateUserResourcePermission(path, userInfoModel, sysApplicationId);
    }

    /**
     * 校验用户的访问权限信息a、匿名用户 b、正常用户
     *
     * @param path
     * @param userInfoModel
     * @return
     */
    private CommonResult validateUserResourcePermission(String path, User userInfoModel, Long sysApplicationId) {
        Long sysRoleId = userInfoModel.getSysRoleId();
        List<Long> roleList = new ArrayList<>();
        if (Objects.nonNull(sysRoleId)) {
            roleList.add(sysRoleId);
        }
        // 系统跳转，获取跳转后的系统角色信息
        if (!Objects.equals(sysApplicationId.toString(), userInfoModel.getAppCode()) || Objects.isNull(sysRoleId)) {
            List<UemUserRole> userRoleList = QUemUserRole.uemUserRole.select().where(
                    QUemUserRole.uemUserId.eq$(userInfoModel.getUemUserId())
                            .and(QUemUserRole.sysApplicationId.eq$(sysApplicationId)
                                    .and(QUemUserRole.isValid.eq$(true)))
            ).execute();
            roleList = userRoleList.stream().map(UemUserRole::getSysRoleId).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(roleList)) {
            // 平台客服登录设置默认角色
            Long customerServiceRoleId = 0L;
            // 允许没有角色的账号登录，默认角色id 为1
            List<Long> allowNoRoleClientIdList = OauthClientUtils.getAllowNoRoleClientId();
			 if (customerServiceRoleId.equals(userInfoModel.getSysRoleId())) {
                roleList.add(userInfoModel.getSysRoleId());
            } else if (allowNoRoleClientIdList.contains(sysApplicationId)) {
                roleList.add(customerServiceRoleId.equals(userInfoModel.getSysRoleId()) ? customerServiceRoleId : 1L);
            } else if (Objects.equals(userInfoModel.getSource(), GlobalEnum.UserSource.NTIP.getCode())) {
                String redirectUrl = this.getRedirectUrlByPermission(userInfoModel, sysApplicationId);
                return CommonResult.failure().errorMessages("302", redirectUrl);
            } else {
                return CommonResult.failure().errorMessages("访问受限，用户没有访问权限" + path);
            }
        }
        boolean isAllowAccessResource;
        Set<PatternPathMatcher> permissionMatchers = new HashSet<>();
        //3.获取用户角色权限
        if (StringUtils.isEmpty(userInfoModel.getAppCode()) && Objects.nonNull(sysRoleId)) {
            permissionMatchers = resourcePermissionCaches.getUnchecked(sysRoleId.toString());
        } else {
            // 所有角色进行权限匹配
            for (Long roleId : roleList) {
                permissionMatchers = resourcePermissionByAppCodeCaches.getUnchecked(Pair.of(sysApplicationId.toString(), roleId.toString()));
                isAllowAccessResource = Seq.seq(permissionMatchers).anyMatch(matcher -> matcher.match(path));
                if (isAllowAccessResource) {
                    // 匹配成功
                    break;
                }
            }
        }

        //5.验证权限是否在准许范围
        isAllowAccessResource = Seq.seq(permissionMatchers)
                .anyMatch(matcher -> matcher.match(path));
        if (isAllowAccessResource) {
            log.info("获取接口权限成功");
            return CommonResult.success();
        } else {
            return CommonResult.failure().errorMessages("访问受限，无法访问地址" + path);
        }
    }

    /**
     * 获取国家综合交通运输信息平台用户错误页面
     * @param authErrorEnum 错误信息
     * @param applicationId 应用ID
     * @return 错误页面
     */
    private String getRedirectUrlByError(GlobalEnum.AuthErrorEnum authErrorEnum, Long applicationId) {
        String paramEncode = "";
        // 应用编码
        String applicationCode = "";
        List<OauthClientDetails> detailsList = QOauthClientDetails.oauthClientDetails.select(QOauthClientDetails.oauthClientDetails.fieldContainer()).where(QOauthClientDetails.sysApplicationId.eq$(applicationId)).execute();
        if (CollectionUtils.isNotEmpty(detailsList)) {
            applicationCode = detailsList.get(0).getClientId();
        }
        try {
            String param = "errorCode=" + authErrorEnum.getCode() + "&applicationCode=" + (StringUtils.isBlank(applicationCode) ? "" : applicationCode);
            paramEncode = URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("{}页面参数URLEncoder编码失败：{}", this.errorPage, e.getMessage(), e);
        }
        return this.errorPage + "?" + paramEncode;
    }

    /**
     * 获取国家综合交通运输信息平台用户没权限页面
     * @param userInfoModel 用户
     * @param sysApplicationId 应用id
     * @return 没权限页面
     */
    private String getRedirectUrlByPermission(User userInfoModel, Long sysApplicationId) {
        // 是否存在待审核/审核通过的申请记录
        List<UemUserPermission> permissionList = QUemUserPermission.uemUserPermission
                .select(QUemUserPermission.uemUserPermission.fieldContainer())
                .where(QUemUserPermission.sysApplicationId.eq$(sysApplicationId)
                        .and(QUemUserPermission.uemUserId.eq$(userInfoModel.getUemUserId()))
                        .and(QUemUserPermission.auditStatus.ne$(GlobalEnum.AuditStatusEnum.AUDIT_REJECT.getCode())))
                .execute();
        if (CollectionUtils.isEmpty(permissionList)) {
            // 权限申请页面
            return this.permissionApplyPage;
        }
        // 权限申请中页面
        return this.permissionApplyingPage;
    }

    /**
     * @Author:chenxf
     * @Description: 判断用户是否已经退出登录
     * @Date: 16:11 2021/1/18
     * @Param: [credential]
     * @Return:boolean
     */
    private boolean isLogout(String credential) {
        String digest = DigestUtils.md5Hex(credential);
        return Objects.nonNull(redisUtil.get(digest));
    }

    /**
     * @Author:chenxf
     * @Description: 刷新资源缓存接口
     * @Date: 16:11 2021/1/18
     * @Param: [roleIds]
     * @Return:void
     */
    @Override
    public void refreshResourceUrlPermissionByRoleIds(List<String> roleIds) {
        if (!roleIds.isEmpty()) {
            roleIds.stream().forEach(roleId -> resourcePermissionCaches.refresh(roleId));
        }

    }

    /**
     * @Author:chenxf
     * @Description: 根据应用id和角色id结婚刷新资源权限缓存
     * @Date: 16:11 2021/1/18
     * @Param: [appCode, roleIds]
     * @Return:void
     */
    @Override
    public void refreshResourceUrlPermissionByAppCodeAndRoleIds(String appCode, List<String> roleIds) {
        if (StringUtils.isEmpty(appCode)) {
            if (!roleIds.isEmpty()) {
                roleIds.stream().forEach(roleId -> {
                    if (resourcePermissionCaches.getIfPresent(roleId) == null ||
                            !resourcePermissionCaches.getIfPresent(roleId).isEmpty()) {
                        resourcePermissionCaches.refresh(roleId);
                    }
                });
            }
        } else {
            if (!roleIds.isEmpty()) {
                roleIds.stream().forEach(roleId -> {
                    if (resourcePermissionByAppCodeCaches.getIfPresent(Pair.of(appCode, roleId)) == null || !resourcePermissionByAppCodeCaches.getIfPresent(Pair.of(appCode, roleId)).isEmpty()) {
                        resourcePermissionByAppCodeCaches.refresh(Pair.of(appCode, roleId));
                    }
                });
            }
        }

    }

    /**
     * @Author:chenxf
     * @Description: 判断是否是白名单方法
     * @Date: 16:12 2021/1/18
     * @Param: [path]
     * @Return:boolean
     */
    @Override
    public boolean isInIgnorePathPattern(String path) {
        return Seq.seq(ignorePathPatternMatchers)
                .anyMatch(matcher -> matcher.match(path));
    }


    @Override
    public boolean isImtpUserAllowPath(String path) {
        return Seq.seq(imtpUserAllowPathPatternMatchers)
                .anyMatch(matcher -> matcher.match(path));
    }

    /**
     * @Author:chenxf
     * @Description: 配置文件加载后加载该方法初始化白名单
     * @Date: 16:13 2021/1/18
     * @Param: []
     * @Return:void
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.ignorePathPatternMatchers = Seq.seq(authenticationConfig.getIgnorePathPatterns())
                .map(PatternPathMatcher::new)
                .toSet();

        this.imtpUserAllowPathPatternMatchers = Seq.seq(authenticationConfig.getImtpUserAllowPath())
                .map(PatternPathMatcher::new)
                .toSet();
    }
}
