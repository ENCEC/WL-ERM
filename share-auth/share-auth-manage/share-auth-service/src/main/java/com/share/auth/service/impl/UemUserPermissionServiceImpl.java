package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.exception.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.share.auth.constants.CodeFinal;
import com.share.auth.constants.GlobalConstant;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemUserPermission;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QSysPlatformUser;
import com.share.auth.model.querymodels.QUemCompany;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.model.querymodels.QUemUserPermission;
import com.share.auth.model.vo.UemUserPermissionVO;
import com.share.auth.service.UemUserPermissionService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendMsgReturnVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 权限审核ServiceImpl
 * @author chenhy
 * @date 2021-05-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class UemUserPermissionServiceImpl implements UemUserPermissionService {

    @Autowired
    private DefaultUserService userService;
    @Autowired
    private MsgApiService msgApiService;

    @Value("${msg.sms.template.permission_audit_pass}")
    private String permissionAuditPassMsgCode;

    @Value("${msg.sms.template.permission_audit_reject}")
    private String permissionAuditRejectMsgCode;

    @Override
    public void saveUemUserPermission(UemUserPermissionVO uemUserPermissionVO) {
        // 校验
        if (Objects.isNull(uemUserPermissionVO.getSysApplicationId())) {
            throw new BusinessRuntimeException("应用id不能为空");
        }
        if (StringUtils.isBlank(uemUserPermissionVO.getApplyReason())) {
            throw new BusinessRuntimeException("申请理由不能为空");
        }
        // 用户信息
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (!Objects.equals(userInfoModel.getSource(), GlobalEnum.UserSource.NTIP.getCode())) {
            throw new BusinessRuntimeException("非国家综合交通运输信息平台用户，无法申请权限");
        }
        // 已申请过，不需要再次申请（待审核/审核通过）
        UemUserPermission uemUserPermission = QUemUserPermission.uemUserPermission.selectOne(QUemUserPermission.uemUserPermission.fieldContainer())
                .where(QUemUserPermission.sysApplicationId.eq$(uemUserPermissionVO.getSysApplicationId())
                        .and(QUemUserPermission.uemUserId.eq$(userInfoModel.getUemUserId()))
                        .and(QUemUserPermission.auditStatus.ne$(GlobalEnum.AuditStatusEnum.AUDIT_REJECT.getCode())))
                .execute();
        if (Objects.nonNull(uemUserPermission)) {
            throw new BusinessRuntimeException("已申请该应用权限，请勿再次申请");
        }
        uemUserPermission = new UemUserPermission();
        uemUserPermission.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        // 申请应用
        uemUserPermission.setSysApplicationId(uemUserPermissionVO.getSysApplicationId());
        // 申请理由
        uemUserPermission.setApplyReason(uemUserPermissionVO.getApplyReason());
        // 申请用户
        uemUserPermission.setUemUserId(userInfoModel.getUemUserId());
        // 审批状态默认0-未审批
        uemUserPermission.setAuditStatus(GlobalEnum.AuditStatusEnum.NO_AUDIT.getCode());
        // 新增
        QUemUserPermission.uemUserPermission.save(uemUserPermission);
    }

    @Override
    public Page<UemUserPermissionVO> queryUemUserPermission(UemUserPermissionVO uemUserPermissionVO) {
        HashMap<String, Object> paramMa = new HashMap<>();
        if (StringUtils.isNotBlank(uemUserPermissionVO.getName())) {
            paramMa.put("name",uemUserPermissionVO.getName());
        }
        if (StringUtils.isNotBlank(uemUserPermissionVO.getCompanyNameCn())) {
            paramMa.put("companyNameCn", uemUserPermissionVO.getCompanyNameCn());
        }
        if (StringUtils.isNotBlank(uemUserPermissionVO.getAuditStatus())) {
            paramMa.put("auditStatus", uemUserPermissionVO.getAuditStatus());
        }
        if (StringUtils.isNotBlank(uemUserPermissionVO.getMobile())) {
            paramMa.put("mobile", uemUserPermissionVO.getMobile());
        }
        //当前页
        int currentPage = uemUserPermissionVO.getCurrentPage() == null ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserPermissionVO.getCurrentPage();
        //size
        int pageSize = uemUserPermissionVO.getPageSize() == null ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserPermissionVO.getPageSize();
        return DSContext.customization("CTZ_UemUserPermissionToUemUser").select()
                .mapperTo(UemUserPermissionVO.class)
                .paging(currentPage, pageSize)
                .execute(paramMa);
//        // 查询
//        return QUemUserPermission.uemUserPermission.select(
//                QUemUserPermission.uemUserPermissionId,
//                QUemUserPermission.uemUser.chain(QUemUser.account).as("account"),
//                QUemUserPermission.uemUser.chain(QUemUser.name).as("name"),
//                QUemUserPermission.uemUser.chain(QUemCompany.companyNameCn).as("companyNameCn"),
//                QUemUserPermission.uemUser.chain(QUemUser.mobile).as(GlobalConstant.ALIAS_MOBILE),
//                QUemUserPermission.sysApplication.chain(QSysApplication.applicationName).as(GlobalConstant.ALIAS_APPLICATION_NAME),
//                QUemUserPermission.sysPlatformUser.chain(QSysPlatformUser.name).as("auditorName"),
//                QUemUserPermission.userAuditor.chain(QUemUser.name).as("userAuditorName"),
//                QUemUserPermission.auditStatus,
//                QUemUserPermission.createTime
//        ).where(
//                QUemUserPermission.auditStatus.eq(":auditStatus")
//                        .and(QUemUserPermission.uemUser.chain(QUemUser.name).like(":name"))
//                        .and(QUemUserPermission.uemUser.chain(QUemUser.mobile).eq(":mobile"))
//                        .and(QUemUserPermission.uemUser.chain(QUemCompany.companyNameCn).like(":companyNameCn"))
//        ).paging((uemUserPermissionVO.getCurrentPage() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserPermissionVO.getCurrentPage(), (uemUserPermissionVO.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserPermissionVO.getPageSize())
//                .sorting(QUemUserPermission.createTime.desc())
//                .mapperTo(UemUserPermissionVO.class)
//                .execute(uemUserPermissionVO);
    }

    @Override
    public UemUserPermissionVO getUemUserPermissionById(Long uemUserPermissionId) {
        List<UemUserPermissionVO> list = QUemUserPermission.uemUserPermission.select(
                QUemUserPermission.uemUserPermissionId,
                QUemUserPermission.uemUser.chain(QUemUser.account).as("account"),
                QUemUserPermission.uemUser.chain(QUemUser.name).as("name"),
                QUemUserPermission.uemUser.chain(QUemCompany.companyNameCn).as("companyNameCn"),
                QUemUserPermission.uemUser.chain(QUemUser.mobile).as(GlobalConstant.ALIAS_MOBILE),
                QUemUserPermission.sysApplication.chain(QSysApplication.applicationName).as(GlobalConstant.ALIAS_APPLICATION_NAME),
                QUemUserPermission.sysPlatformUser.chain(QSysPlatformUser.name).as("auditorName"),
                QUemUserPermission.userAuditor.chain(QUemUser.name).as("userAuditorName"),
                QUemUserPermission.applyReason,
                QUemUserPermission.auditor,
                QUemUserPermission.auditRemark,
                QUemUserPermission.auditStatus,
                QUemUserPermission.auditTime,
                QUemUserPermission.createTime,
                QUemUserPermission.creatorId,
                QUemUserPermission.creatorName,
                QUemUserPermission.modifierId,
                QUemUserPermission.modifierName,
                QUemUserPermission.modifyTime,
                QUemUserPermission.recordVersion,
                QUemUserPermission.sysApplicationId,
                QUemUserPermission.uemUserId
        ).where(
                QUemUserPermission.uemUserPermissionId.eq(":uemUserPermissionId")
        ).mapperTo(
                UemUserPermissionVO.class
        ).execute(ImmutableMap.of("uemUserPermissionId", uemUserPermissionId));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public void auditUemUserPermission(UemUserPermissionVO uemUserPermissionVO) {
        // 用户信息
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        // 校验
        if (!Objects.equals(uemUserPermissionVO.getAuditStatus(), GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode())
                && !Objects.equals(uemUserPermissionVO.getAuditStatus(), GlobalEnum.AuditStatusEnum.AUDIT_REJECT.getCode())) {
            throw new BusinessRuntimeException("审核状态错误");
        }
        if (Objects.equals(uemUserPermissionVO.getAuditStatus(), GlobalEnum.AuditStatusEnum.AUDIT_REJECT.getCode())
                && StringUtils.isBlank(uemUserPermissionVO.getAuditRemark())) {
            throw new BusinessRuntimeException("审核拒绝时，拒绝理由不能为空");
        }
        if (Objects.isNull(uemUserPermissionVO.getUemUserPermissionId())) {
            throw new BusinessRuntimeException("权限id不能为空");
        }
        // 查询更新数据
        UemUserPermission uemUserPermission = QUemUserPermission.uemUserPermission.selectOne(QUemUserPermission.uemUserPermission.fieldContainer()).byId(uemUserPermissionVO.getUemUserPermissionId());
        if (Objects.isNull(uemUserPermission)) {
            throw new BusinessRuntimeException("权限审核记录不存在");
        }
        if (!Objects.equals(uemUserPermission.getAuditStatus(), GlobalEnum.AuditStatusEnum.NO_AUDIT.getCode())) {
            throw new BusinessRuntimeException("权限审核记录已审核");
        }
        uemUserPermission.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        // 审批状态
        uemUserPermission.setAuditStatus(uemUserPermissionVO.getAuditStatus());
        // 备注
        uemUserPermission.setAuditRemark(uemUserPermissionVO.getAuditRemark());
        // 审批人信息
        uemUserPermission.setAuditor(userInfoModel.getUemUserId());
        // 审批时间
        uemUserPermission.setAuditTime(new Date());
        // 更新
        QUemUserPermission.uemUserPermission.save(uemUserPermission);

        // 发送短信线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("auditUemUserPermission-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        // 发送信息的权限审核信息
        UemUserPermissionVO userPermissionVO = this.getUemUserPermissionById(uemUserPermission.getUemUserPermissionId());
        if (Objects.equals(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode(), userPermissionVO.getAuditStatus())){
            // 审核通过
            singleThreadPool.execute(()-> this.sendMsgByAuditPass(userPermissionVO));
        } else {
            // 审核拒绝
            singleThreadPool.execute(()-> this.sendMsgByAuditReject(userPermissionVO));
        }
        // 关闭线程池
        singleThreadPool.shutdown();
    }

    /**
     * 审批拒绝发送短信
     * @param userPermissionVO 权限审核信息
     */
    private void sendMsgByAuditReject(UemUserPermissionVO userPermissionVO) {
        // 短信VO
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(userPermissionVO.getMobile());
        // 短信模板
        msgSmsApiVO.setSmsTemplateCode(permissionAuditRejectMsgCode);
        // 消息宏参数map
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("auditRemark", userPermissionVO.getAuditRemark());
        content.put("name", userPermissionVO.getName());
        content.put(GlobalConstant.ALIAS_APPLICATION_NAME, userPermissionVO.getApplicationName());
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： {}", marco.toString());
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        // 调用公共服务接口发送短信
        try{
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())){
                log.info("权限审核【拒绝】，短信发送成功");
            } else {
                log.error("权限审核【拒绝】，短信发送失败，失败原因：{}", sendMsgReturnVo.getResultMsg());
            }
        } catch (Exception e){
            log.error("调用推送服务失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 审批通过发送短信
     * @param userPermissionVO 权限审核信息
     */
    private void sendMsgByAuditPass(UemUserPermissionVO userPermissionVO) {
        // 短信VO
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信模板
        msgSmsApiVO.setSmsTemplateCode(permissionAuditPassMsgCode);
        // 消息宏参数map
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put(GlobalConstant.ALIAS_MOBILE, userPermissionVO.getMobile());
        content.put("name", userPermissionVO.getName());
        content.put("applicationName", userPermissionVO.getApplicationName());
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： {}", marco.toString());
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);

        // 客服
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.sysPlatformUser.fieldContainer()).where(QSysPlatformUser.isValid.eq$(true)).execute();
        for (SysPlatformUser sysPlatformUser : sysPlatformUserList) {
            // 短信发送手机号
            msgSmsApiVO.setAcceptNo(sysPlatformUser.getTel());
            // 调用公共服务接口发送短信
            try{
                SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
                if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())){
                    log.info("权限审核【通过】，短信发送成功");
                } else {
                    log.error("权限审核【通过】，短信发送失败，失败原因：{}", sendMsgReturnVo.getResultMsg());
                }
            } catch (Exception e){
                log.error("调用推送服务失败：{}", e.getMessage(), e);
            }
        }
    }


}
