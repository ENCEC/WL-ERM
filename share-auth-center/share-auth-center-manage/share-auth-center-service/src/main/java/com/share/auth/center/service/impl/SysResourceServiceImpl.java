package com.share.auth.center.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.share.auth.center.enums.GlobalEnum;
import com.share.auth.center.model.entity.SysResource;
import com.share.auth.center.model.querymodels.QSysResource;
import com.share.auth.center.model.querymodels.QSysRoleResource;
import com.share.auth.center.service.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * @Author:chenxf
 * @Description: 资源查询相关服务
 * @Date: 16:17 2021/1/18
 * @Param: 
 * @Return:
 *
 */
@Service
@Slf4j
public class SysResourceServiceImpl implements SysResourceService{

    /**
     * @Author:chenxf
     * @Description: 获取所有接口权限url
     * @Date: 14:30 2020/12/18
     * @Param: []
     * @Return:java.util.Set<java.lang.String>
     *
     */
    @Override
    public Set<String> findAllByIsValid() {
        List<SysResource> resourceList = QSysResource.sysResource.select(QSysResource.resourceUrl).where(
                QSysResource.isValid.eq$(true).and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.INTERFACE.getCode()))).execute();
        return resourceList.stream().map(SysResource::getResourceUrl).collect(toSet());
    }

    /**
     * @Author:chenxf
     * @Description: 获取角色的所有按钮权限url
     * @Date: 14:16 2020/12/18
     * @Param: [roleId]
     * @Return:java.util.Set<java.lang.String>
     *
     */
    @Override
    public Set<String> getResourcesByRoleId(String roleId, int resourceType) {
        List<SysResource> resourceList = QSysResource.sysResource.select(QSysResource.resourceUrl).where(
                QSysResource.isValid.eq$(true)
                .and(QSysResource.resourceType.eq$(resourceType))
                .and(QSysResource.sysResource.chain(QSysRoleResource.sysRoleId).eq$(Long.valueOf(roleId)))
        ).execute();
        return resourceList.stream().map(SysResource::getResourceUrl).collect(toSet());
    }

    /**
     * @Author:chenxf
     * @Description: 根据应用Id和角色Id获取权限列表
     * @Date: 14:47 2020/12/18
     * @Param: [key, value]
     * @Return:java.util.Set<java.lang.String>
     *
     */
    @Override
    public Set<String> getResourcesByClientIdAndRoleId(String sysApplicationId, String sysRoleId) {
        List<SysResource> resourceList = QSysResource.sysResource.select(QSysResource.resourceUrl).where(
                QSysResource.isValid.eq$(true)
                        .and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.INTERFACE.getCode()))
                .and(QSysResource.sysApplicationId.eq$(Long.valueOf(sysApplicationId)))
                .and(QSysResource.sysResource.chain(QSysRoleResource.sysRoleId).eq$(Long.valueOf(sysRoleId)))
        ).execute();
        return resourceList.stream().map(SysResource::getResourceUrl).collect(toSet());
    }

    /**
     * @Author:chenxf
     * @Description: 获取页面下的所有按钮资源集合
     * @Date: 13:56 2020/12/18
     * @Param: [key]
     * @Return:java.util.Set<java.lang.String>
     *
     */
    @Override
    public Set<String> loadAllResourcesByPageUrl(String key) {
        SysResource sysResource = QSysResource.sysResource.selectOne().where(
                QSysResource.isValid.eq$(true)
                        .and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.PAGE.getCode())
                                .and(QSysResource.resourceUrl.eq$(key)))).execute();
        List<SysResource> resourceList = QSysResource.sysResource.select(QSysResource.resourceUrl).where(
                QSysResource.isValid.eq$(true)
                        .and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.BUTTON.getCode()))
                .and(QSysResource.resourcePid.eq$(sysResource.getSysResourceId()))
        ).execute();
        return resourceList.stream().map(SysResource::getResourceUrl).collect(toSet());
    }

    /**
     * @Author:chenxf
     * @Description: 根据应用Id和页面url获取该页面下所有资源
     * @Date: 13:59 2020/12/18
     * @Param: [key, value]
     * @Return:java.util.Set<java.lang.String>
     *
     */
    @Override
    public Set<String> loadAllResourcesByAppCodeAndPageUrl(String sysApplicationId, String pageUrl) {
        SysResource sysResource = null;
        String[] path = pageUrl.split("/");
        if (path.length > 1){
            Long pid = null;
            for (int i=0;i< path.length;i++){
                if (StringUtils.isEmpty(path[i])){
                    continue;
                }
                if (i==1){
                    sysResource = findByResourceUrl(path[i], null);
                }else {
                    sysResource = findByResourceUrl(path[i], pid);
                }
                pid = sysResource.getSysResourceId();
            }
        }else {
            sysResource = findByResourceUrl(pageUrl, null);
        }
        Set<String> resourceList = new HashSet<>();
        if (Objects.nonNull(sysResource)){
            List<SysResource> sysResourceList = QSysResource.sysResource.select().where(
                    QSysResource.sysApplicationId.eq$(Long.valueOf(sysApplicationId))
                    .and(QSysResource.resourcePid.eq$(sysResource.getSysResourceId()))
                    .and(QSysResource.isValid.eq$(true))
                    .and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.BUTTON.getCode()))
            ).execute();
            resourceList = sysResourceList.stream().map(SysResource::getResourceUrl).collect(toSet());
        }
        return resourceList;
    }

    private SysResource findByResourceUrl(String path,Long pid){
        AndExpression andExpression =   QSysResource.resourceUrl.eq$(path)
                .and(QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.PAGE.getCode()))
                .and(QSysResource.isValid.eq$(true));
        if (Objects.nonNull(pid)){
            Expression expression = QSysResource.resourcePid.eq$(pid);
            andExpression = andExpression.and(expression);
        }
        return QSysResource.sysResource.selectOne().where(andExpression).execute();
    }
    /**
     * @Author:chenxf
     * @Description: 根据接口url获取资源所属应用Id
     * @Date: 14:39 2020/12/18
     * @Param: [path]
     * @Return:java.lang.Long
     *
     */
    @Override
    public List<Long> findByPath(String path) {
        List<SysResource> sysResourceList = QSysResource.sysResource.select(QSysResource.sysResource.fieldContainer()).where(
                QSysResource.resourceType.eq$(GlobalEnum.ResourceTypeEnum.INTERFACE.getCode())
                .and(QSysResource.isValid.eq$(true))
                .and(QSysResource.resourceUrl.eq$(path))
        ).execute();
        if (CollectionUtils.isNotEmpty(sysResourceList)){
            return sysResourceList.stream().map(SysResource::getSysApplicationId).collect(Collectors.toList());
        }else {
            return null;
        }
    }

}
