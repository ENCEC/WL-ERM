package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
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
import com.share.auth.user.UserInfoModel;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.FastDfsUploadResult;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ShareFileInterface shareFileInterface;
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
        String clientId = sysResourceQueryVO.getClientId();
        Long uemUserId = sysResourceQueryVO.getUemUserId();
        if (Objects.isNull(clientId)) {
            log.info("入参客户端id为空");
            return CommonResult.getFaildResultData("入参客户端id为空");
        }
        if (Objects.isNull(uemUserId)) {
            log.info("入参用户id为空");
            return CommonResult.getFaildResultData("入参用户id为空，请确认！");
        }
        UserInfoModel userInfoModel = (UserInfoModel) defaultUserService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || !uemUserId.equals(userInfoModel.getUemUserId())) {
            return CommonResult.getFaildResultData("用户未登录！");
        }
        // 根据clientId查出应用id
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails
                .selectOne(QOauthClientDetails.oauthClientDetails.fieldContainer())
                .byId(sysResourceQueryVO.getClientId());
//        // 允许所有用户登录的客户端id
//        List<Long> allowAllClientIdList = OauthClientUtils.ALLOW_ALL_CLIENT_ID;
//        // 只允许管理员登录的客户端id
//        List<Long> allowAdminClientIdList = OauthClientUtils.ALLOW_ADMIN_CLIENT_ID;
//        // 允许没有角色的账号登录
//        List<Long> allowNoRoleClientIdList = OauthClientUtils.ALLOW_NO_ROLE_CLIENT_ID;
        UemUser uemUser = QUemUser.uemUser.selectOne()
                .where(QUemUser.uemUserId.eq$(uemUserId)
                        .and(QUemUser.isDeleted.eq$(false)))
                .execute();
        if (Objects.isNull(uemUser)) {
            log.info("获取用户{}信息失败", uemUserId);
            return CommonResult.getFaildResultData("用户不存在！");
        }
        // 根据应用id，用户id查出该应用该用户启用的角色列表
        sysResourceQueryVO.setSysApplicationId(oauthClientDetails.getSysApplicationId());
        List<QueryResourceDTO> queryResourceDTOList = DSContext
                .customization("WL-ERM_selectResourceListByUser")
                .select()
                .mapperTo(QueryResourceDTO.class)
                .execute(sysResourceQueryVO);
        List<Long> resourcePidList = queryResourceDTOList.stream()
                .map(QueryResourceDTO::getResourcePid)
                .collect(Collectors.toList());
        if (!resourcePidList.isEmpty()) {
            List<QueryResourceDTO> parentResourceDtoList = QSysResource.sysResource
                    .select(QSysResource.sysResourceId,
                            QSysResource.sysApplicationId,
                            QSysResource.resourceLogo,
                            QSysResource.resourceTitle,
                            QSysResource.resourceUrl,
                            QSysResource.resourceRemark,
                            QSysResource.resourceSort,
                            QSysResource.resourcePid,
                            QSysResource.resourceType,
                            QSysResource.component,
                            QSysResource.componentName)
                    .where(QSysResource.sysResourceId.in$(resourcePidList))
                    .mapperTo(QueryResourceDTO.class)
                    .execute();
            Set<QueryResourceDTO> queryResourceDTOSet = new HashSet<>();
            queryResourceDTOSet.addAll(queryResourceDTOList);
            queryResourceDTOSet.addAll(parentResourceDtoList);
            queryResourceDTOList = new ArrayList<>(queryResourceDTOSet);
        }

        queryResourceDTOList = dealWithResource(queryResourceDTOList, true);
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }

    /**
     * @Author:chenxf
     * @Description: 根据应用id查询资源集合层级数据
     * @Date: 15:48 2020/11/16
     * @Param: [sysApplicationId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
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
     */
    private List<QueryResourceDTO> dealWithResource(List<QueryResourceDTO> queryResourceDTOList, boolean isSort) {
        // 第一级资源集合
        List<QueryResourceDTO> parentList = Lists.newArrayList();
        // 第一级以下的资源按父级资源id保存到map中,key为父级资源id，value为子资源信息集合
        Map<Long, List<QueryResourceDTO>> childrenMap = new HashMap<>(16);
        List<QueryResourceDTO> childrenList = null;
        // 将资源分开
        for (QueryResourceDTO queryResourceDTO : queryResourceDTOList) {
            if (Objects.isNull(queryResourceDTO.getResourcePid())) {
                // 父级资源id为空的是第一级资源
                parentList.add(queryResourceDTO);
            } else {
                // 父级资源不为空的按父级资源id添加到map中
                if (CollectionUtils.isNotEmpty(childrenMap.get(queryResourceDTO.getResourcePid()))) {
                    childrenList = childrenMap.get(queryResourceDTO.getResourcePid());
                } else {
                    childrenList = Lists.newArrayList();
                }
                childrenList.add(queryResourceDTO);
                childrenMap.put(queryResourceDTO.getResourcePid(), childrenList);
            }
        }
        // 调用递归方法完善子资源数据
        queryResourceDTOList = dealWithChildrenResource(parentList, childrenMap, isSort);
        queryResourceDTOList.sort(Comparator.comparingInt(QueryResourceDTO::getResourceSort));
        return queryResourceDTOList;

    }

    /**
     * @Author:chenxf
     * @Description: 递归方法，完善父级资源的子资源数据
     * @Date: 16:46 2020/11/16
     * @Param: [queryResourceDTOList, childrenMap]
     * @Return:java.util.List<com.share.auth.domain.QueryResourceDTO>
     */
    private List<QueryResourceDTO> dealWithChildrenResource(List<QueryResourceDTO> queryResourceDTOList, Map<Long, List<QueryResourceDTO>> childrenMap, boolean isSort) {
        for (QueryResourceDTO parent : queryResourceDTOList) {
            List<QueryResourceDTO> childrenList = childrenMap.get(parent.getSysResourceId());
            if (CollectionUtils.isNotEmpty(childrenList)) {
                childrenList = dealWithChildrenResource(childrenList, childrenMap, isSort);
                parent.setHaveChildrenResource(true);
                if (isSort) {
                    childrenList.sort(Comparator.comparingInt(QueryResourceDTO::getResourceSort));
                }
                parent.setChildrenResourceList(childrenList);
            } else {
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
    public ResultHelper<Map<String, List<QueryResourceDTO>>> queryResourceAllSystem(SysResourceQueryVO sysResourceQueryVO) {
        IUser user = defaultUserService.getCurrentLoginUser();
        sysResourceQueryVO.setUid(Long.valueOf(user.getUserId().toString()));
        if (ObjectUtils.isEmpty(Long.valueOf(user.getUserId().toString()))) {
            return CommonResult.getFaildResultData("请先登入系统！");
        }
//        if (Objects.isNull(sysResourceQueryVO.getClientId())){
//            log.info("入参客户端id为空");
//            return CommonResult.getFaildResultData("入参客户端id为空");
//        }
        if (Objects.isNull(sysResourceQueryVO.getUid())) {
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
            if (!CodeFinal.AUDIT_STATUS_ONE.equals(uemUser.getAuditStatus())) {
                log.info("用户{}未实名认证通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户未实名认证，请前往用户中心实名认证！");
            }
            if (Objects.isNull(uemUser.getBlindCompanny())) {
                log.info("用户{}未绑定企业，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户未绑定企业，请前往用户中心绑定企业！");
            }
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            if (Objects.isNull(uemCompany) || !CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())) {
                log.info("用户{}绑定的企业未审批通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户绑定的企业未审批通过！");
            }
        }

        Expression expression = null;
        // 判断根据是否有当前用户角色id来选择查询条件（调度系统传来的当前用户角色id） -- modified by huanghwh
        if (Objects.isNull(sysResourceQueryVO.getSysRoleId())) {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        } else {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.sysRoleId.eq$(sysResourceQueryVO.getSysRoleId()))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        }

        // 根据应用id，用户id查出该应用该用户启用的角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.sysRoleId)
                .where(expression)
                .execute();
        if (CollectionUtils.isEmpty(uemUserRoleList)) {
            // 平台客服登录
            if (Objects.nonNull(sysPlatformUser)) {
                UemUserRole uemUserRole = new UemUserRole();
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
                log.info("该用户在该应用没有角色，clientId:" + sysResourceQueryVO.getClientId() + "，用户id:" + sysResourceQueryVO.getUid());
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
                .sorting(QSysResource.resourceSort.asc(), QSysResource.sysResourceId.asc())
                .execute();
        Map<Long, List<QueryResourceDTO>> queryResourceDTOMap = queryResourceDTOList.stream().collect(Collectors.groupingBy(QueryResourceDTO::getSysApplicationId));
        Map<String, List<QueryResourceDTO>> queryResourceDTOListMap = new HashMap<>();
        //所有系统转换
        List<Long> sysApplicationIds = new ArrayList<>(queryResourceDTOMap.keySet());
        List<SysApplication> sysApplicationList = QSysApplication.sysApplication.select().where(QSysApplication.sysApplicationId.in$(sysApplicationIds)).execute();
        Map<Long, SysApplication> sysApplicationMap = sysApplicationList.stream().collect(Collectors.toMap(SysApplication::getSysApplicationId, a -> a, (k1, k2) -> k1));
        for (Long key : queryResourceDTOMap.keySet()) {
            List<QueryResourceDTO> dtoList = queryResourceDTOMap.get(key);
            SysApplication sysApplication = sysApplicationMap.get(key);
            dtoList = dealWithResource(dtoList, true);
            //dtoList.sort(Comparator.comparing(QueryResourceDTO::getSysResourceId));
            queryResourceDTOListMap.put(sysApplication.getApplicationName(), dtoList);
        }
        return CommonResult.getSuccessResultData(queryResourceDTOListMap);
    }

    /**
     * 根据应用ID、用户ID和页面URL获取页面的按钮列表
     *
     * @param sysResourceQueryVO -
     * @return 页面中允许的按钮列表
     */
    @Override
    public ResultHelper<List<QueryResourceDTO>> queryButtonInPage(SysResourceQueryVO sysResourceQueryVO) {
        IUser user = defaultUserService.getCurrentLoginUser();
        sysResourceQueryVO.setUid(Long.valueOf(user.getUserId().toString()));
        if (ObjectUtils.isEmpty(Long.valueOf(user.getUserId().toString()))) {
            return CommonResult.getFaildResultData("请先登入系统！");
        }
//        if (Objects.isNull(sysResourceQueryVO.getClientId())){
//            log.info("入参客户端id为空");
//            return CommonResult.getFaildResultData("入参客户端id为空");
//        }
        if (Objects.isNull(sysResourceQueryVO.getUid())) {
            log.info("入参用户id为空");
            return CommonResult.getFaildResultData("入参用户id为空，请确认！");
        }
        if (Objects.isNull(sysResourceQueryVO.getResourceUrl())) {
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
            if (!CodeFinal.AUDIT_STATUS_ONE.equals(uemUser.getAuditStatus())) {
                log.info("用户{}未实名认证通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户未实名认证，请前往用户中心实名认证！");
            }
            if (Objects.isNull(uemUser.getBlindCompanny())) {
                log.info("用户{}未绑定企业，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户未绑定企业，请前往用户中心绑定企业！");
            }
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            if (Objects.isNull(uemCompany) || !CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())) {
                log.info("用户{}绑定的企业未审批通过，获取资源菜单失败", uemUser.getAccount());
                return CommonResult.getFaildResultDataWithErrorCode(400000302, "用户绑定的企业未审批通过！");
            }
        }

        Expression expression = null;
        // 判断根据是否有当前用户角色id来选择查询条件（调度系统传来的当前用户角色id） -- modified by huanghwh
        if (Objects.isNull(sysResourceQueryVO.getSysRoleId())) {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        } else {
            expression = QUemUserRole.uemUserId.eq$(sysResourceQueryVO.getUid()).and(QUemUserRole.isValid.eq$(true))
                    .and(QUemUserRole.sysRoleId.eq$(sysResourceQueryVO.getSysRoleId()))
                    .and(QUemUserRole.uemUserRole.chain(QSysApplication.isValid).eq$(true));
        }

        // 根据应用id，用户id查出该应用该用户启用的角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.sysRoleId)
                .where(expression)
                .execute();
        if (CollectionUtils.isEmpty(uemUserRoleList)) {
            // 平台客服登录
            if (Objects.nonNull(sysPlatformUser)) {
                UemUserRole uemUserRole = new UemUserRole();
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
                log.info("该用户在该应用没有角色，clientId:" + sysResourceQueryVO.getClientId() + "，用户id:" + sysResourceQueryVO.getUid());
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
                .sorting(QSysResource.resourceSort.asc(), QSysResource.sysResourceId.asc())
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
                .where(QSysResource.isValid.eq$(true).and(QSysResource.isDeleted.eq$(false)))
                .mapperTo(QueryResourceDTO.class)
                .execute();
        queryResourceDTOList = dealWithResource(queryResourceDTOList, false);
        return CommonResult.getSuccessResultData(queryResourceDTOList);
    }

    /**
     * @Author:wzr
     * @Description: 新增菜单信息
     * @Date: 2022/7/25
     */
    @Override
    public ResultHelper<Object> saveResource(SysResourceDTO sysResourceDTO) {
        SysResource sysResource = new SysResource();
        Long sysApplicationId = sysResourceDTO.getSysApplicationId();
        String resourceTitle = sysResourceDTO.getResourceTitle();
        String resourceLogo = sysResourceDTO.getResourceLogo();
        String resourceUrl = sysResourceDTO.getResourceUrl();
        Integer resourceSort = sysResourceDTO.getResourceSort();
        String resourceRemark = sysResourceDTO.getResourceRemark();
        Long sysResourceId = sysResourceDTO.getSysResourceId();
        Long resourcePid = sysResourceDTO.getResourcePid();
        String component = sysResourceDTO.getComponent();
        sysResource.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysResource.setResourceLogo(resourceLogo);
        sysResource.setSysApplicationId(sysApplicationId);
        sysResource.setComponent(component);
        sysResource.setIsValid(true);
        sysResource.setSysResourceId(sysResourceId);
        sysResource.setResourcePid(resourcePid);
        sysResource.setResourceTitle(resourceTitle);
        sysResource.setResourceUrl(resourceUrl);
        sysResource.setResourceSort(resourceSort);
        sysResource.setResourceRemark(resourceRemark);
        sysResource.setResourcePid(resourcePid);
        QSysResource.sysResource.save(sysResource);
        return CommonResult.getSuccessResultData("新增成功");
    }

    /**
     * @Author:wzr
     * @Description: 分页带条件查询菜单信息
     * @Date: 2022/7/25
     */
    @Override
    public Page<SysResourceDTO> queryResourceByPage(SysResourceDTO sysResourceDTO) {
        Integer currentPage = sysResourceDTO.getCurrentPage();
        Integer pageSize = sysResourceDTO.getPageSize();
        //模糊查询拼字段名称
        if (!StringUtils.isEmpty(sysResourceDTO.getResourceTitle())) {
            sysResourceDTO.setResourceTitle("%" + sysResourceDTO.getResourceTitle() + "%");
        }
        return DSContext.customization("WL-ERM_queryResourceByPage").select()
                .paging((currentPage == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : currentPage, (pageSize == null)
                        ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize).mapperTo(SysResourceDTO.class)
                .sorting()
                .execute(sysResourceDTO);
    }

    /**
     * @Author:wzr
     * @Description: 根据id查询菜单信息
     * @Date: 2022/7/25
     */
    @Override
    public SysResourceDTO queryResourceById(Long sysResourceId) {
        return QSysResource.sysResource.selectOne().where(
                        QSysResource.sysResourceId.eq$(sysResourceId)
                )
                .mapperTo(SysResourceDTO.class)
                .execute();
    }

    /**
     * @Author:wzr
     * @Description: 修改菜单信息
     * @Date: 2022/7/25
     */
    @Override
    public ResultHelper<Object> updateResource(SysResourceDTO sysResourceDTO) {
        Long resourceId = sysResourceDTO.getSysResourceId();
        if (Objects.isNull(resourceId)) {
            return CommonResult.getFaildResultData("资源id不能为空");
        }
        String resourceTitle = sysResourceDTO.getResourceTitle();
        Long resourcePid = sysResourceDTO.getResourcePid();
        String resourceUrl = sysResourceDTO.getResourceUrl();
        Integer resourceSort = sysResourceDTO.getResourceSort();
        String resourceRemark = sysResourceDTO.getResourceRemark();
        String component = sysResourceDTO.getComponent();
        String resourceLogo = sysResourceDTO.getResourceLogo();
        Long sysApplicationId = sysResourceDTO.getSysApplicationId();
        SysResource sysResource = QSysResource.sysResource
                .selectOne(QSysResource.sysResource.fieldContainer()).byId(resourceId);
        if (sysResource == null) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        sysResource.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysResource.setResourcePid(resourcePid);
        sysResource.setSysApplicationId(sysApplicationId);
        sysResource.setResourceLogo(resourceLogo);
        sysResource.setComponent(component);
        sysResource.setSysResourceId(resourceId);
        sysResource.setResourceTitle(resourceTitle);
        sysResource.setResourceUrl(resourceUrl);
        sysResource.setResourceSort(resourceSort);
        sysResource.setResourceRemark(resourceRemark);
        QSysResource.sysResource.save(sysResource);
        return CommonResult.getSuccessResultData("修改成功");
    }

    /**
     * @Author:wzr
     * @Description: 菜单信息是否禁用状态
     * @Date: 2022/7/26
     */
    @Override
    public ResultHelper<Object> updateResourceStatus(SysResourceDTO sysResourceDTO) {
        Long resourceId = sysResourceDTO.getSysResourceId();
        if (Objects.isNull(resourceId)) {
            return CommonResult.getFaildResultData("资源id不能为空");
        }
        //是否禁用（0禁用，1启用）
        Boolean isValid = sysResourceDTO.getIsValid();

        int updateCount = QSysResource.sysResource
                .update(
                        QSysResource.isValid,
                        QSysResource.invalidTime
                )
                .where(QSysResource.sysResourceId.eq$(resourceId))
                .execute(isValid, new Date(), resourceId);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("禁用成功");
        } else {
            return CommonResult.getFaildResultData("禁用失败");
        }
    }

    /**
     * 逻辑删除菜单信息
     *
     * @author wzr
     * @date 2022-07-26
     */
    @Override
    public ResultHelper<Object> deleteResource(Long sysResourceId) {
        int result = QSysResource.sysResource.deleteById(sysResourceId);
        if (result == 1) {
            return CommonResult.getSuccessResultData("删除成功");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }

    }

    @Override
    public List<SysResourceDTO> queryParentResource() {
        return DSContext.customization("WL-ERM_queryResourceTitle").select()
                .mapperTo(SysResourceDTO.class)
                .execute();
    }
}
