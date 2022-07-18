package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.QueryApplicationDTO;
import com.share.auth.domain.SysApplicationDto;
import com.share.auth.domain.UemUserDto;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.service.SysApplicationService;
import com.share.auth.service.SysRoleService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxf
 * @date 2020-10-26 16:20
 */
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class SysApplicationServiceImpl implements SysApplicationService {

    /**
     * 引用查询类型
     */
    private static final String YYCX = "yycx";

    @Autowired
    private DefaultUserService userService;
    @Autowired
    private SysRoleService sysRoleService;


    /**
     * @Author:chenxf
     * @Description: 查询应用接口
     * @Date: 14:55 2020/11/3
     * @Param: [opType]
     * @Return:java.util.List<com.share.auth.domain.QueryApplicationDTO>
     */
    @Override
    public List<QueryApplicationDTO> queryApplicationForOpType(String opType) {
        List<QueryApplicationDTO> queryApplicationDTOList = Lists.newArrayList();
        if (YYCX.equals(opType)) {
            queryApplicationDTOList = QSysApplication.sysApplication.select(
                    QSysApplication.sysApplicationId,
                    QSysApplication.applicationCode,
                    QSysApplication.applicationName,
                    QSysApplication.applicationAbbreviName
            ).where(QSysApplication.isValid.eq$(true)).mapperTo(QueryApplicationDTO.class).execute();
        }
        return queryApplicationDTOList;
    }

    /**
     * 应用管理
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return Page<SysApplication>
     * @author xrp
     */
    @Override
    public Page<SysApplicationDto> querySysApplication(SysApplicationDto sysApplicationDto) {

        //应用名称
        String applicationName = sysApplicationDto.getApplicationName();
        //应用代码
        String applicationCode = sysApplicationDto.getApplicationCode();

        if (!StringUtils.isEmpty(applicationName)) {
            sysApplicationDto.setApplicationName("%" + applicationName + "%");
        }
        if (!StringUtils.isEmpty(applicationCode)) {
            sysApplicationDto.setApplicationCode("%" + applicationCode + "%");
        }

        return QSysApplication.sysApplication
                .select(QSysApplication.sysApplicationId,
                        QSysApplication.applicationName,
                        QSysApplication.applicationCode,
                        QSysApplication.applicationUrl,
                        QSysApplication.isValid,
                        QSysApplication.invalidTime)
                .where(QSysApplication.applicationName.like(":applicationName")
                        .and(QSysApplication.isValid.eq(":isValid"))
                        .and(QSysApplication.applicationCode.like(":applicationCode")))
                .paging((sysApplicationDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : sysApplicationDto.getPageNo(), (sysApplicationDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : sysApplicationDto.getPageSize())
                .sorting(QSysApplication.createTime.desc())
                .mapperTo(SysApplicationDto.class)
                .execute(sysApplicationDto);
    }

    /**
     * 保存/更新应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    @Override
    public ResultHelper<Object> saveSysApplication(SysApplicationDto sysApplicationDto) {
        log.info("平台客服新增应用信息");
        //应用名称
        String applicationName = sysApplicationDto.getApplicationName();
        //应用简称
        String applicationAbbreviName = sysApplicationDto.getApplicationAbbreviName();
        //维护企业
        String relatedEnterprise = sysApplicationDto.getRelatedEnterprise();
        //维护联系人
        String contact = sysApplicationDto.getContact();
        //维护联系人手机
        String contactTel = sysApplicationDto.getContactTel();
        //应用地址
        String applicationUrl = sysApplicationDto.getApplicationUrl();
        //应用描述
        String applicationRemark = sysApplicationDto.getApplicationRemark();

        if (StringUtils.isEmpty(applicationName)) {
            return CommonResult.getFaildResultData("应用名称不能为空");
        }
        if (StringUtils.isEmpty(relatedEnterprise)) {
            return CommonResult.getFaildResultData("维护单位不能为空");
        }
        if (StringUtils.isEmpty(applicationUrl)) {
            return CommonResult.getFaildResultData("应用地址不能为空");
        }
        if (StringUtils.isEmpty(contactTel)) {
            return CommonResult.getFaildResultData("联系人手机不能为空");
        }
        if (StringUtils.isEmpty(contact)) {
            return CommonResult.getFaildResultData("维护联系人不能为空");
        }
        //新加操作
        SysApplication sysApplication = new SysApplication();
        sysApplication.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);

        sysApplication.setApplicationName(applicationName);
        sysApplication.setApplicationAbbreviName(applicationAbbreviName);
        sysApplication.setRelatedEnterprise(relatedEnterprise);
        sysApplication.setContact(contact);
        sysApplication.setContactTel(contactTel);
        sysApplication.setApplicationUrl(applicationUrl);
        sysApplication.setApplicationRemark(applicationRemark);
        sysApplication.setIsValid(true);
        //单号管理
        sysApplication.setApplicationCode(getMaxApplicationCode());
        int saveCount = QSysApplication.sysApplication.save(sysApplication);
        log.info("平台客服保存应用信息，返回保存行数为：{}", saveCount);
        if (saveCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("保存成功");
        } else {
            return CommonResult.getFaildResultData("保存失败");
        }

    }

    /**
     * 保存/更新应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    @Override
    public ResultHelper<Object> updateSysApplication(SysApplicationDto sysApplicationDto) {
        log.info("平台客服修改应用信息");
        //应用管理ID
        String sysApplicationId = sysApplicationDto.getSysApplicationId();
        //应用名称
        String applicationName = sysApplicationDto.getApplicationName();
        //应用简称
        String applicationAbbreviName = sysApplicationDto.getApplicationAbbreviName();
        //维护企业
        String relatedEnterprise = sysApplicationDto.getRelatedEnterprise();
        //维护联系人
        String contact = sysApplicationDto.getContact();
        //维护联系人手机
        String contactTel = sysApplicationDto.getContactTel();
        //应用地址
        String applicationUrl = sysApplicationDto.getApplicationUrl();
        //应用描述
        String applicationRemark = sysApplicationDto.getApplicationRemark();
        if (Objects.isNull(sysApplicationId)) {
            log.error("修改应用，应用ID不能为空");
            return CommonResult.getFaildResultData("应用ID不能为空");
        }
        if (StringUtils.isEmpty(applicationName)) {
            log.error("应用名称不能为空");
            return CommonResult.getFaildResultData("应用名称不能为空");
        }
        if (StringUtils.isEmpty(relatedEnterprise)) {
            log.error("应用名称不能为空");
            return CommonResult.getFaildResultData("维护单位不能为空");
        }
        if (StringUtils.isEmpty(applicationUrl)) {
            log.error("应用名称不能为空");
            return CommonResult.getFaildResultData("应用地址不能为空");
        }
        if (StringUtils.isEmpty(contactTel)) {
            log.error("应用名称不能为空");
            return CommonResult.getFaildResultData("联系人手机不能为空");
        }
        if (StringUtils.isEmpty(contact)) {
            log.error("应用名称不能为空");
            return CommonResult.getFaildResultData("维护联系人不能为空");
        }
        //sysApplicationId不为空，修改操作
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne(QSysApplication.sysApplication.fieldContainer()).byId(Long.valueOf(sysApplicationId));
        sysApplication.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);

        sysApplication.setApplicationAbbreviName(applicationAbbreviName);
        sysApplication.setRelatedEnterprise(relatedEnterprise);
        sysApplication.setContact(contact);
        sysApplication.setContactTel(contactTel);
        sysApplication.setApplicationUrl(applicationUrl);
        sysApplication.setApplicationRemark(applicationRemark);
        sysApplication.setApplicationName(applicationName);
        int updateCount = QSysApplication.sysApplication.save(sysApplication);
        log.info("平台客服修改应用信息，返回修改行数为：{}", updateCount);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("修改成功");
        } else {
            return CommonResult.getFaildResultData("修改失败");
        }
    }

    /**
     * 应用管理  应用详情
     *
     * @param sysApplicationId 应用表的ID
     * @return
     * @author xrp
     */
    @Override
    public List<SysApplication> getSysApplication(String sysApplicationId) {
        return QSysApplication.sysApplication
                .select(QSysApplication.sysApplication.fieldContainer())
                .where(QSysApplication.sysApplicationId.eq(":sysApplicationId"))
                .execute(ImmutableMap.of("sysApplicationId", sysApplicationId));
    }

    /**
     * @Author:chenxf
     * @Description: 权限分配应用下拉框查询接口
     * @Date: 18:58 2021/1/30
     * @Param: []
     * @Return:com.share.support.result.ResultHelper
     */
    @Override
    public ResultHelper<List<SysApplication>> queryApplicationForCompanyRole() {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData("获取用户信息失败！请重新登录");
        }
        if (Objects.isNull(userInfoModel.getBlindCompanny())) {
            return CommonResult.getFaildResultData("用户未绑定企业！请重新登录");
        }
        // 查询企业可分配权限的应用
        List<SysApplication> sysApplications = QSysRole.sysRole.select(
                QSysRole.sysApplicationId,
                QSysRole.sysRole.chain(QSysApplication.applicationName).as("applicationName"),
                QSysRole.sysRole.chain(QSysApplication.isValid).as("isValid")
        ).where(
                QSysRole.isValid.eq$(Boolean.TRUE)
                        .and(QSysRole.sysRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
                        .and(QSysRole.uemCompanyRole.chain(QUemCompanyRole.uemCompanyId).eq$(userInfoModel.getBlindCompanny()))
        ).groupBy(QSysRole.sysApplicationId)
                .mapperTo(SysApplication.class)
                .execute();
        return CommonResult.getSuccessResultData(sysApplications);
    }

    /**
     * @Author: cjh
     * @Description: 权限分配应用下拉框查询接口-用户管理
     * @Date: 18:58 2021/11/30
     * @Param: []
     * @Return:com.share.support.result.ResultHelper
     */
    @Override
    public ResultHelper<List<SysApplication>> queryApplicationForCompanyRoleByUser(Long uemUserId) {
        UemUser uemUserTemp = QUemUser.uemUser.selectOne().byId(uemUserId);

        if (Objects.isNull(uemUserTemp.getBlindCompanny())) {
            return CommonResult.getFaildResultData("用户未绑定企业！");
        }
        // 查询企业可分配权限的应用
        List<SysApplication> sysApplications = QSysRole.sysRole.select(
                QSysRole.sysApplicationId,
                QSysRole.sysRole.chain(QSysApplication.applicationName).as("applicationName"),
                QSysRole.sysRole.chain(QSysApplication.isValid).as("isValid")
        ).where(
                QSysRole.isValid.eq$(Boolean.TRUE)
                        .and(QSysRole.sysRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
                        .and(QSysRole.uemCompanyRole.chain(QUemCompanyRole.uemCompanyId).eq$(uemUserTemp.getBlindCompanny()))
        ).groupBy(QSysRole.sysApplicationId)
                .mapperTo(SysApplication.class)
                .execute();
        return CommonResult.getSuccessResultData(sysApplications);
    }

    @Override
    public ResultHelper<SysApplication> getSysApplicationByClientId(String clientId) {
        List<SysApplication> applications = QOauthClientDetails.oauthClientDetails.select(
                QOauthClientDetails.sysApplication.chain(QSysApplication.sysApplicationId).as("sysApplicationId"),
                QOauthClientDetails.sysApplication.chain(QSysApplication.applicationName).as("applicationName"),
                QOauthClientDetails.sysApplication.chain(QSysApplication.applicationRemark).as("applicationRemark"))
                .where(QOauthClientDetails.clientId.eq$(clientId)).mapperTo(SysApplication.class).execute();
        return CommonResult.getSuccessResultData(CollectionUtils.isEmpty(applications) ? null : applications.get(0));
    }

    /**
     * @return
     * @Author cec
     * @Description 获取applicationCode
     * @Date 2021/10/25 15:36
     * @Param
     **/
    private String getMaxApplicationCode() {
        String dateStr = DateUtils.getDateStr(DateUtils.getNowDate()).replaceAll("-","");
        //取出最后一条
        SysApplication sysApplication = DSContext.customization("CZT_getMaxApplicationCode").selectOne()
                .mapperTo(SysApplication.class)
                .execute();
        //判断是否为空
        if (ObjectUtils.isEmpty(sysApplication) || ObjectUtils.isEmpty(sysApplication.getApplicationCode())) {
            return "YYDM" + dateStr.substring(2,4) + "0001";
        }
        String oldCode = sysApplication.getApplicationCode();
        int newCode = Integer.valueOf(oldCode.substring(6)) + 1;
        String appCode = "YYDM" + dateStr.substring(2,4) + String.format("%04d", newCode);
        log.info("生成应用代码：{}", appCode);
        return appCode;
    }
}
