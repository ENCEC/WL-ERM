package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.gillion.ec.core.security.IUser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysResourceQueryVO;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.service.SysResourceService;
import com.share.auth.user.DefaultUserService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxf
 * @date 2020-10-26 16:45
 */
@Service
@Slf4j
public class SysResourceServiceImpl implements SysResourceService {

    @Value("#{'${sso.allow.all}'.split(',')}")
    private List<Long> allowAllClientIdList;

    @Value("#{'${sso.allow.admin}'.split(',')}")
    private List<Long> allowAdminClientIdList;

    @Value("#{'${sso.allow.noRole}'.split(',')}")
    private List<Long> allowNoRoleClientIdList;

    @Autowired
    private DefaultUserService defaultUserService;
    /**
     * 应用id(数据服务查询占位符)
     */
    private static final String SYS_APPLICATION_ID_PLACEHOLDER = ":sysApplicationId";
    /**
     * 是否禁用(数据服务查询占位符)
     */
    private static final String IS_VALID_PLACEHOLDER = ":isValid";
    /**
     * 应用id(字段名称)
     */
    private static final String SYS_APPLICATION_ID = "sysApplicationId";
    /**
     * 是否禁用(字段名称)
     */
    private static final String IS_VALID = "isValid";


    /**
     * @Author:chenxf
     * @Description: 根据应用id，用户id查询资源
     * @Date: 17:53 2020/10/26
     * @Param: [param]clientId：应用client_id，非空；uid：用户id，非空；sysRoleId：调度系统用户当前角色id，可选
     * @Return:java.lang.String
     */
    @Override
    public ResultHelper<List<QueryResourceDTO>> queryResource(SysResourceQueryVO sysResourceQueryVO) {
        if (Objects.isNull(sysResourceQueryVO.getClientId())){
            log.info("入参客户端id为空");
            return CommonResult.getFaildResultData("入参客户端id为空");
        }
        if (Objects.isNull(sysResourceQueryVO.getUid())){
            log.info("入参用户id为空");
            return CommonResult.getFaildResultData("入参用户id为空，请确认！");
        }
        // 根据clientId查出应用id
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne(QOauthClientDetails.oauthClientDetails.fieldContainer()).byId(sysResourceQueryVO.getClientId());
//        // 允许所有用户登录的客户端id
//        List<Long> allowAllClientIdList = OauthClientUtils.ALLOW_ALL_CLIENT_ID;
//        // 只允许管理员登录的客户端id
//        List<Long> allowAdminClientIdList = OauthClientUtils.ALLOW_ADMIN_CLIENT_ID;
//        // 允许没有角色的账号登录
//        List<Long> allowNoRoleClientIdList = OauthClientUtils.ALLOW_NO_ROLE_CLIENT_ID;
        // 是否登录公共服务
        if (allowAdminClientIdList.contains(oauthClientDetails.getSysApplicationId())){
            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
            if (Objects.isNull(sysPlatformUser)) {
                return CommonResult.getFaildResultData("非平台客服无法登录公共服务，请确认！");
            }
            return CommonResult.getSuccessResultData();
        }
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(sysResourceQueryVO.getUid());
        if (Objects.isNull(sysPlatformUser) && Objects.isNull(uemUser)) {
            log.info("获取用户{}信息失败", sysResourceQueryVO.getUid());
            return CommonResult.getFaildResultData("用户不存在！");
        }
        // 校验用户信息
        if (Objects.nonNull(uemUser)) {
            if (!CodeFinal.AUDIT_STATUS_ONE.equals(uemUser.getAuditStatus())){
                log.info("用户{}未实名认证通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未实名认证，请前往实名认证！");
            }
            if (Objects.isNull(uemUser.getBlindCompanny())){
                log.info("用户{}未绑定企业，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未绑定企业，请前往绑定企业！");
            }
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            if (Objects.isNull(uemCompany) || !CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
                log.info("用户{}绑定的企业未审批通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户绑定的企业未审批通过！");
            }
        }

        Expression expression = null;
        // 判断根据是否有当前用户角色id来选择查询条件（调度系统传来的当前用户角色id） -- modified by huanghwh
        if (Objects.isNull(sysResourceQueryVO.getSysRoleId())) {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.sysApplicationId.eq$(oauthClientDetails.getSysApplicationId())).and(QUemUserRole.isValid.eq$(true));
        } else {
            expression =  QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.sysApplicationId.eq$(oauthClientDetails.getSysApplicationId())).and(QUemUserRole.isValid.eq$(true)).and(QUemUserRole.sysRoleId.eq$(sysResourceQueryVO.getSysRoleId()));
        }

        // 根据应用id，用户id查出该应用该用户启用的角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.sysRoleId)
                .where(expression)
                .execute();
        if (CollectionUtils.isEmpty(uemUserRoleList)){
            // 平台客服登录
            if (Objects.nonNull(sysPlatformUser)) {
                UemUserRole uemUserRole  = new UemUserRole();
                uemUserRole.setSysRoleId(0L);
                uemUserRoleList.add(uemUserRole);
            } else if (allowNoRoleClientIdList.contains(oauthClientDetails.getSysApplicationId())) {
                // 若登录允许没角色的应用，赋值默认角色，角色id = 0（客服），1（其他用户）
                UemUserRole uemUserRole  = new UemUserRole();
                uemUserRole.setSysRoleId(1L);
                uemUserRoleList.add(uemUserRole);
            } else {
                log.info("该用户在该应用没有角色，clientId:" + sysResourceQueryVO.getClientId()+ "，用户id" + sysResourceQueryVO.getUid());
                return CommonResult.getFaildResultData("该用户在该应用没有角色，请联系客服人员确认！");
            }
        }
        // 根据应用id，启用状态，父级资源id，关联查询上面所查的该用户该应用启用的角色的角色资源表数据，查出所有资源
        List<QueryResourceDTO> queryResourceDTOList = QSysResource.sysResource.select(
                QSysResource.sysResourceId,
                QSysResource.resourceLogo,
                QSysResource.resourceTitle,
                QSysResource.resourceUrl,
                QSysResource.resourceSort,
                QSysResource.component,
                QSysResource.resourcePid
        ).where(
                QSysResource.sysApplicationId.eq(SYS_APPLICATION_ID_PLACEHOLDER)
                        .and(QSysResource.isValid.eq(IS_VALID_PLACEHOLDER))
                        .and(QSysResource.sysResource.chain(QSysRoleResource.sysRoleId).eq(":sysRoleId"))
                        .and(QSysResource.resourceType.eq$(1)))
                .mapperTo(QueryResourceDTO.class)
                .sorting(QSysResource.resourceSort.asc())
                .execute(ImmutableMap.of(SYS_APPLICATION_ID, oauthClientDetails.getSysApplicationId(), IS_VALID, true, "sysRoleId", uemUserRoleList.get(0).getSysRoleId()));
        queryResourceDTOList = dealWithResource(queryResourceDTOList, true);
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }

    /**
     * @Author:chenxf
     * @Description: 根据应用id查询资源集合层级数据
     * @Date: 15:48 2020/11/16
     * @Param: [sysApplicationId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @Override
    public ResultHelper<List<QueryResourceDTO>> queryApplicationResourceTree(Long sysApplicationId) {
        // 查出该应用下所有资源
        List<QueryResourceDTO> queryResourceDTOList = QSysResource.sysResource.select(
                QSysResource.sysResourceId,
                QSysResource.resourceTitle,
                QSysResource.resourceSort,
                QSysResource.resourcePid,
                QSysResource.resourceRemark
        ).where(
                QSysResource.sysApplicationId.eq(SYS_APPLICATION_ID_PLACEHOLDER)
                        .and(QSysResource.isValid.eq(IS_VALID_PLACEHOLDER))
                        .and(QSysResource.resourceType.eq$(1).or(QSysResource.resourceType.eq$(2))))
                .mapperTo(QueryResourceDTO.class)
                .execute(ImmutableMap.of(SYS_APPLICATION_ID, sysApplicationId, IS_VALID, true));

        queryResourceDTOList = dealWithResource(queryResourceDTOList, false);
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }

    /**
     * @Author:chenxf
     * @Description: 将集合中的资源按照层级关系组合
     * @Date: 15:50 2020/11/16
     * @Param: [queryResourceDTOList]
     * @Return:java.util.List<com.share.auth.domain.QueryResourceDTO>
     *
     */
    private List<QueryResourceDTO> dealWithResource(List<QueryResourceDTO> queryResourceDTOList, boolean isSort){
        // 第一级资源集合
        List<QueryResourceDTO> parentList = Lists.newArrayList();
        // 第一级以下的资源按父级资源id保存到map中,key为父级资源id，value为子资源信息集合
        Map<Long, List<QueryResourceDTO>> childrenMap = new HashMap<>(16);
        List<QueryResourceDTO> childrenList = null;
        // 将资源分开
        for (QueryResourceDTO queryResourceDTO: queryResourceDTOList) {
            if (Objects.isNull(queryResourceDTO.getResourcePid())){
                // 父级资源id为空的是第一级资源
                parentList.add(queryResourceDTO);
            }else {
                // 父级资源不为空的按父级资源id添加到map中
                if (CollectionUtils.isNotEmpty(childrenMap.get(queryResourceDTO.getResourcePid()))){
                    childrenList = childrenMap.get(queryResourceDTO.getResourcePid());
                }else {
                    childrenList = Lists.newArrayList();
                }
                childrenList.add(queryResourceDTO);
                childrenMap.put(queryResourceDTO.getResourcePid(), childrenList);
            }
        }
        // 调用递归方法完善子资源数据
        queryResourceDTOList = dealWithChildrenResource(parentList, childrenMap, isSort);
        return queryResourceDTOList;

    }

    /**
     * @Author:chenxf
     * @Description: 递归方法，完善父级资源的子资源数据
     * @Date: 16:46 2020/11/16
     * @Param: [queryResourceDTOList, childrenMap]
     * @Return:java.util.List<com.share.auth.domain.QueryResourceDTO>
     *
     */
    private List<QueryResourceDTO> dealWithChildrenResource(List<QueryResourceDTO> queryResourceDTOList, Map<Long, List<QueryResourceDTO>> childrenMap, boolean isSort){
        for (QueryResourceDTO parent: queryResourceDTOList) {
            List<QueryResourceDTO> childrenList = childrenMap.get(parent.getSysResourceId());
            if (CollectionUtils.isNotEmpty(childrenList)){
                childrenList = dealWithChildrenResource(childrenList, childrenMap, isSort);
                parent.setHaveChildrenResource(true);
                if (isSort){
                    childrenList.sort(Comparator.comparingInt(QueryResourceDTO::getResourceSort));
                }
                parent.setChildrenResourceList(childrenList);
            }else {
                parent.setHaveChildrenResource(false);
            }
        }
        return queryResourceDTOList;
    }

    /**
     * 根据应用id，用户id获取所有系统资源集合
     *
     * @param sysResourceQueryVO 查询VO
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 根据应用id，用户id获取资源集合
     * @Date: 15:48 2020/11/16
     */
    @Override
    public ResultHelper<Map<String , List<QueryResourceDTO>>> queryResourceAllSystem(SysResourceQueryVO sysResourceQueryVO){
        IUser user = defaultUserService.getCurrentLoginUser();
        sysResourceQueryVO.setUid(Long.valueOf(user.getUserId().toString()));
        if (ObjectUtils.isEmpty(Long.valueOf(user.getUserId().toString()))){
            return CommonResult.getFaildResultData("请先登入系统！");
        }
//        if (Objects.isNull(sysResourceQueryVO.getClientId())){
//            log.info("入参客户端id为空");
//            return CommonResult.getFaildResultData("入参客户端id为空");
//        }
        if (Objects.isNull(sysResourceQueryVO.getUid())){
            log.info("入参用户id为空");
            return CommonResult.getFaildResultData("入参用户id为空，请确认！");
        }
        // 根据clientId查出应用id
        //OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne(QOauthClientDetails.oauthClientDetails.fieldContainer()).byId(sysResourceQueryVO.getClientId());
        // 允许所有用户登录的客户端id
        //List<Long> allowAllClientIdList = OauthClientUtils.ALLOW_ALL_CLIENT_ID;
        // 只允许管理员登录的客户端id
        //List<Long> allowAdminClientIdList = OauthClientUtils.ALLOW_ADMIN_CLIENT_ID;
        // 允许没有角色的账号登录
//        List<Long> allowNoRoleClientIdList = OauthClientUtils.ALLOW_NO_ROLE_CLIENT_ID;
//        // 是否登录公共服务
//        if (allowAdminClientIdList.contains(oauthClientDetails.getSysApplicationId())){
//            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
//            if (Objects.isNull(sysPlatformUser)) {
//                return CommonResult.getFaildResultData("非平台客服无法登录公共服务，请确认！");
//            }
//            return CommonResult.getSuccessResultData();
//        }
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(sysResourceQueryVO.getUid());
        if (Objects.isNull(sysPlatformUser) && Objects.isNull(uemUser)) {
            log.info("获取用户{}信息失败", sysResourceQueryVO.getUid());
            return CommonResult.getFaildResultData("用户不存在！");
        }
        // 校验用户信息
        if (Objects.nonNull(uemUser)) {
            if (!CodeFinal.AUDIT_STATUS_ONE.equals(uemUser.getAuditStatus())){
                log.info("用户{}未实名认证通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未实名认证，请前往用户中心实名认证！");
            }
            if (Objects.isNull(uemUser.getBlindCompanny())){
                log.info("用户{}未绑定企业，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未绑定企业，请前往用户中心绑定企业！");
            }
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            if (Objects.isNull(uemCompany) || !CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
                log.info("用户{}绑定的企业未审批通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户绑定的企业未审批通过！");
            }
        }

        Expression expression = null;
        // 判断根据是否有当前用户角色id来选择查询条件（调度系统传来的当前用户角色id） -- modified by huanghwh
        if (Objects.isNull(sysResourceQueryVO.getSysRoleId())) {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        } else {
            expression =  QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.sysRoleId.eq$(sysResourceQueryVO.getSysRoleId()))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        }

        // 根据应用id，用户id查出该应用该用户启用的角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.sysRoleId)
                .where(expression)
                .execute();
        if (CollectionUtils.isEmpty(uemUserRoleList)){
            // 平台客服登录
            if (Objects.nonNull(sysPlatformUser)) {
                UemUserRole uemUserRole  = new UemUserRole();
                uemUserRole.setSysRoleId(0L);
                uemUserRoleList.add(uemUserRole);
            }
//            else if (allowNoRoleClientIdList.contains(oauthClientDetails.getSysApplicationId())) {
//                // 若登录允许没角色的应用，赋值默认角色，角色id = 0（客服），1（其他用户）
//                UemUserRole uemUserRole  = new UemUserRole();
//                uemUserRole.setSysRoleId(Objects.nonNull(sysPlatformUser) ? 0L : 1L);
//                uemUserRoleList.add(uemUserRole);
//            }
            else {
                log.info("该用户在该应用没有角色，clientId:" + sysResourceQueryVO.getClientId()+ "，用户id:" + sysResourceQueryVO.getUid());
                return CommonResult.getFaildResultData("该用户在该应用没有角色，请联系客服人员确认！");
            }
        }
        //多权限roleId
        List<Long> roleIdList = uemUserRoleList.stream().map(p -> p.getSysRoleId()).distinct().collect(Collectors.toList());
        // 根据应用id，启用状态，父级资源id，关联查询上面所查的该用户该应用启用的角色的角色资源表数据，查出所有资源
        List<QueryResourceDTO> queryResourceDTOList = QSysResource.sysResource.select(
                QSysResource.sysApplicationId, QSysResource.sysResourceId,
                QSysResource.resourceLogo, QSysResource.resourceTitle,
                QSysResource.resourceUrl, QSysResource.resourceSort,
                QSysResource.component, QSysResource.resourcePid, QSysResource.componentName
        ).where(QSysResource.isValid.eq$(true)
                .and(QSysResource.sysResource.chain(QSysRoleResource.sysRoleId).in$(roleIdList))
                .and(QSysResource.resourceType.eq$(1)))
                .mapperTo(QueryResourceDTO.class)
                .sorting(QSysResource.resourceSort.asc(),QSysResource.sysResourceId.asc())
                .execute();
        Map<Long, List<QueryResourceDTO>> queryResourceDTOMap = queryResourceDTOList.stream().collect(Collectors.groupingBy(QueryResourceDTO::getSysApplicationId));
        Map<String , List<QueryResourceDTO>> queryResourceDTOListMap = new HashMap<>();
        //所有系统转换
        List<Long> sysApplicationIds = new ArrayList<>(queryResourceDTOMap.keySet());
        List<SysApplication> sysApplicationList = QSysApplication.sysApplication.select().where(QSysApplication.sysApplicationId.in$(sysApplicationIds)).execute();
        Map<Long,SysApplication> sysApplicationMap = sysApplicationList.stream().collect(Collectors.toMap(SysApplication::getSysApplicationId, a -> a,(k1,k2)->k1));
        for(Long key  : queryResourceDTOMap.keySet()){
            List<QueryResourceDTO> dtoList = queryResourceDTOMap.get(key);
            SysApplication sysApplication = sysApplicationMap.get(key);
            dtoList = dealWithResource(dtoList, true);
            //dtoList.sort(Comparator.comparing(QueryResourceDTO::getSysResourceId));
            queryResourceDTOListMap.put(sysApplication.getApplicationName(),dtoList);
        }
        return CommonResult.getSuccessResultData(queryResourceDTOListMap);
    }

    /**
     * 根据应用ID、用户ID和页面URL获取页面的按钮列表
     * @param sysResourceQueryVO -
     * @return 页面中允许的按钮列表
     */
    @Override
    public ResultHelper<List<QueryResourceDTO>> queryButtonInPage(SysResourceQueryVO sysResourceQueryVO) {
        IUser user = defaultUserService.getCurrentLoginUser();
        sysResourceQueryVO.setUid(Long.valueOf(user.getUserId().toString()));
        if (ObjectUtils.isEmpty(Long.valueOf(user.getUserId().toString()))){
            return CommonResult.getFaildResultData("请先登入系统！");
        }
//        if (Objects.isNull(sysResourceQueryVO.getClientId())){
//            log.info("入参客户端id为空");
//            return CommonResult.getFaildResultData("入参客户端id为空");
//        }
        if (Objects.isNull(sysResourceQueryVO.getUid())){
            log.info("入参用户id为空");
            return CommonResult.getFaildResultData("入参用户id为空，请确认！");
        }
        if (Objects.isNull(sysResourceQueryVO.getResourceUrl())){
            log.info("入参资源URL为空");
            return CommonResult.getFaildResultData("入参资源URL为空，请确认！");
        }
        // 根据clientId查出应用id
        //OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne(QOauthClientDetails.oauthClientDetails.fieldContainer()).byId(sysResourceQueryVO.getClientId());
        // 允许所有用户登录的客户端id
        //List<Long> allowAllClientIdList = OauthClientUtils.ALLOW_ALL_CLIENT_ID;
        // 只允许管理员登录的客户端id
        //List<Long> allowAdminClientIdList = OauthClientUtils.ALLOW_ADMIN_CLIENT_ID;
        // 允许没有角色的账号登录
//        List<Long> allowNoRoleClientIdList = OauthClientUtils.ALLOW_NO_ROLE_CLIENT_ID;
//        // 是否登录公共服务
//        if (allowAdminClientIdList.contains(oauthClientDetails.getSysApplicationId())){
//            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
//            if (Objects.isNull(sysPlatformUser)) {
//                return CommonResult.getFaildResultData("非平台客服无法登录公共服务，请确认！");
//            }
//            return CommonResult.getSuccessResultData();
//        }
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(sysResourceQueryVO.getUid());
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(sysResourceQueryVO.getUid());
        if (Objects.isNull(sysPlatformUser) && Objects.isNull(uemUser)) {
            log.info("获取用户{}信息失败", sysResourceQueryVO.getUid());
            return CommonResult.getFaildResultData("用户不存在！");
        }
        // 校验用户信息
        if (Objects.nonNull(uemUser)) {
            if (!CodeFinal.AUDIT_STATUS_ONE.equals(uemUser.getAuditStatus())){
                log.info("用户{}未实名认证通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未实名认证，请前往用户中心实名认证！");
            }
            if (Objects.isNull(uemUser.getBlindCompanny())){
                log.info("用户{}未绑定企业，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户未绑定企业，请前往用户中心绑定企业！");
            }
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            if (Objects.isNull(uemCompany) || !CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
                log.info("用户{}绑定的企业未审批通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302,"用户绑定的企业未审批通过！");
            }
        }

        Expression expression = null;
        // 判断根据是否有当前用户角色id来选择查询条件（调度系统传来的当前用户角色id） -- modified by huanghwh
        if (Objects.isNull(sysResourceQueryVO.getSysRoleId())) {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        } else {
            expression =  QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.sysRoleId.eq$(sysResourceQueryVO.getSysRoleId()))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        }

        // 根据应用id，用户id查出该应用该用户启用的角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.sysRoleId)
                .where(expression)
                .execute();
        if (CollectionUtils.isEmpty(uemUserRoleList)){
            // 平台客服登录
            if (Objects.nonNull(sysPlatformUser)) {
                UemUserRole uemUserRole  = new UemUserRole();
                uemUserRole.setSysRoleId(0L);
                uemUserRoleList.add(uemUserRole);
            }
//            else if (allowNoRoleClientIdList.contains(oauthClientDetails.getSysApplicationId())) {
//                // 若登录允许没角色的应用，赋值默认角色，角色id = 0（客服），1（其他用户）
//                UemUserRole uemUserRole  = new UemUserRole();
//                uemUserRole.setSysRoleId(Objects.nonNull(sysPlatformUser) ? 0L : 1L);
//                uemUserRoleList.add(uemUserRole);
//            }
            else {
                log.info("该用户在该应用没有角色，clientId:" + sysResourceQueryVO.getClientId()+ "，用户id:" + sysResourceQueryVO.getUid());
                return CommonResult.getFaildResultData("该用户在该应用没有角色，请联系客服人员确认！");
            }
        }
        //多权限roleId
        List<Long> roleIdList = uemUserRoleList.stream().map(p -> p.getSysRoleId()).distinct().collect(Collectors.toList());
        // 根据应用id，启用状态，父级资源id，关联查询上面所查的该用户该应用启用的角色的角色资源表数据，查出所有资源
        List<QueryResourceDTO> queryResourceDTOList = QSysResource.sysResource.select(
                QSysResource.sysApplicationId, QSysResource.sysResourceId,
                QSysResource.resourceLogo, QSysResource.resourceTitle,
                QSysResource.resourceUrl, QSysResource.resourceSort,
                QSysResource.component, QSysResource.resourcePid, QSysResource.componentName
        ).where(QSysResource.isValid.eq$(true)
                .and(QSysResource.sysResource.chain(QSysRoleResource.sysRoleId).in$(roleIdList))
                .and(QSysResource.resourceType.eq$(2))
                .and(QSysResource.resourceUrl.eq$(sysResourceQueryVO.getResourceUrl())))
                .mapperTo(QueryResourceDTO.class)
                .sorting(QSysResource.resourceSort.asc(),QSysResource.sysResourceId.asc())
                .execute();
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }

    /**
     * 根据角色ID获取资源列表
     *
     * @param sysRoleIdList 角色ID列表
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    public ResultHelper<List<SysResourceDTO>> queryResourceByRole(List<Long> sysRoleIdList) {
        if (sysRoleIdList.isEmpty()) {
            return CommonResult.getFaildResultData("角色ID不能为空");
        }
        Map<String, Object> namedParams = new HashMap<>();
        namedParams.put("sysRoleIdList", sysRoleIdList);
        List<SysResourceDTO> sysResourceDTOList = DSContext
                .customization("WL-ERM_queryResourceByRole")
                .select()
                .mapperTo(SysResourceDTO.class)
                .execute(namedParams);
        return CommonResult.getSuccessResultData(sysResourceDTOList);
    }

    /**
     * 获取所有未禁用角色
     *
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.auth.domain.SysResourceDTO>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    public ResultHelper<List<QueryResourceDTO>> queryAllValidResource() {
        List<QueryResourceDTO> queryResourceDTOList = QSysResource.sysResource
                .select(QSysResource.sysResource.fieldContainer())
                .where(QSysResource.isValid.eq$(true).and(QSysResource.isValid.eq$(true)))
                .mapperTo(QueryResourceDTO.class)
                .execute();
        queryResourceDTOList = dealWithResource(queryResourceDTOList, false);
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }
}
