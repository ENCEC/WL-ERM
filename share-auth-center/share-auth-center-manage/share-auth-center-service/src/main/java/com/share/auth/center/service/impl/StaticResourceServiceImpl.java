package com.share.auth.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.share.auth.center.enums.GlobalEnum;
import com.share.auth.center.model.entity.OauthClientDetails;
import com.share.auth.center.service.StaticResourceService;
import com.share.auth.center.service.SysResourceService;
import com.share.auth.center.service.UemUserService;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author:chenxf
 * @Description: 前端静态资源相关服务
 * @Date: 16:13 2021/1/18
 * @Param:
 * @Return:
 *
 */
@Slf4j
@Service
public class StaticResourceServiceImpl implements StaticResourceService {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private UemUserService uemUserService;

    @Autowired
    private RedisUtil redisUtil;

    /**所有资源权限缓存*/
    private LoadingCache<String, Set<String>> allLocalResourceCaches;

    /** 应用所有的资源权限缓存*/
    private LoadingCache<Pair<String, String>, Set<String>> allLocalResourceByAppCodeCaches;

    /**
     * @Author:chenxf
     * @Description: 初始化缓存
     * @Date: 16:14 2021/1/18
     * @Param: []
     * @Return:
     *
     */
    public StaticResourceServiceImpl() {
        allLocalResourceCaches = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Set<String>>() {
                    @Override
                    public Set<String> load(String key) {
                        //直接从数据库中获取，不要从redisInterface中获取
                        if (StringUtils.isNotEmpty(key)) {
                            log.info("加载pageUrl:{}功能权限信息", key);
                            return sysResourceService.loadAllResourcesByPageUrl(key);
                        }
                        return new HashSet<>();
                    }
                });
        allLocalResourceByAppCodeCaches = CacheBuilder
                .newBuilder()
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<Pair<String, String>, Set<String>>() {
                    @Override
                    public Set<String> load(Pair<String, String> key) {
                        //直接从数据库中获取，不要从redisInterface中获取
                        if (StringUtils.isNotEmpty(key.getKey()) && StringUtils.isNoneEmpty(key.getValue())) {
                            log.info("加载pageUrl:{}功能权限信息", key);
                            return sysResourceService.loadAllResourcesByAppCodeAndPageUrl(key.getKey(), key.getValue());
                        }
                        return new HashSet<>();
                    }
                });

    }
    /**
     * @Author:chenxf
     * @Description: 获取用户页面下无权限url
     * @Date: 15:37 2020/12/5
     * @Param: [user, pageUrl]
     * @Return:java.util.Collection<java.lang.String>
     *
     */
    @Override
    public Collection<String> getNoPermissionUrl(User user, String pageUrl, OauthClientDetails oauthClientDetails) {
//        // 允许没有角色的账号登录
//        List<Long> allowNoRoleClientIdList = OauthClientUtils.getAllowNoRoleClientId();
//        // token传来的用户当前角色id
//        Long sysRoleId = user.getSysRoleId();
//        // 查询用户信息（这里用户如果是多角色，查出来的角色信息会为空）
//        user = uemUserService.getUserInfo(user.getUemUserId(), oauthClientDetails.getClientId());
//        // 当查询出来的用户没有角色id时，使用session中的角色id
//        if (Objects.isNull(user.getSysRoleId())) {
//            user.setSysRoleId(sysRoleId);
//        }
//        // 当前登录的应用为允许没有角色时，用户角色为空，则设置默认角色id = 1
//        if (allowNoRoleClientIdList.contains(oauthClientDetails.getSysApplicationId())
//                && Objects.isNull(user.getSysRoleId())) {
//            user.setSysRoleId(1L);
//        }
//
//        Set<String> allUrls;
//        Set<String> roleUrls;
//        if (Objects.isNull(oauthClientDetails.getSysApplicationId())) {
//            // 如果没有应用Id，则获取全部的
//            // 从缓存中获取页面下所有的url
//            allUrls = allLocalResourceCaches.getUnchecked(pageUrl);
//            // 获取该用户角色所有权限url
//        } else {
//            // 如果有应用id，则获取应用下的
//            // 从缓存中获取当前应用下当前页面下的所有url
//            allUrls = allLocalResourceByAppCodeCaches.getUnchecked(Pair.of(oauthClientDetails.getSysApplicationId().toString(), pageUrl));
//        }
//        // 获取该用户角色在该应用下的所有权限url
//        roleUrls = this.initResourcePermissionUrlCaches(user.getSysRoleId()).getUnchecked(user.getSysRoleId().toString());
//
//        Collection<String> noPermitList = CollectionUtils.subtract(allUrls, roleUrls);
//        return noPermitList.isEmpty() ? CollectionUtils.emptyCollection() : noPermitList;
        return null;
    }

    private LoadingCache<String, Set<String>> initResourcePermissionUrlCaches(Long roleId) {
        String roleResourceListCodeStr = "roleResourceList";
        // 角色资源权限缓存
        LoadingCache<String, Set<String>> resourcePermissionUrlCaches;
        if (Objects.nonNull(redisUtil.get(roleResourceListCodeStr))) {
            resourcePermissionUrlCaches = JSON.parseObject(redisUtil.get("roleResourceList").toString(), LoadingCache.class);
        } else {
            resourcePermissionUrlCaches = CacheBuilder
                    .newBuilder()
                    .refreshAfterWrite(30, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, Set<String>>() {
                        @Override
                        public Set<String> load(String key) {
                            log.info("加载角色id:{}功能权限信息", key);
                            //直接从数据库中获取，不要从redisInterface中获取
                            return sysResourceService.getResourcesByRoleId(key, GlobalEnum.ResourceTypeEnum.BUTTON.getCode());

                        }
                    });
            log.info("角色id：" + roleId + "resourcePermissionUrlCaches" + resourcePermissionUrlCaches.toString());
            redisUtil.setForTimeDays(roleId.toString(), EntityUtils.toJsonString(resourcePermissionUrlCaches),9999);
        }
        return resourcePermissionUrlCaches;
    }

}
