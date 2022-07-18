package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.CombinedExpression;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.exception.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.QueryCompanyManagerDTO;
import com.share.auth.model.entity.UemCompanyManager;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.entity.UemUserCompany;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.CompanyManagerQueryVO;
import com.share.auth.service.UemCompanyManagerService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxf
 * @date 2020-10-30 15:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UemCompanyManagerServiceImpl implements UemCompanyManagerService{

    /**审批成功短信模版编号*/
    @Value("${msg.sms.template.review_success}")
    private String reviewSuccessMsgTemplate;

    /**审批失败短信模版编号*/
    @Value("${msg.sms.template.review_failed}")
    private String reviewFailedMsgTemplate;


    @Autowired
    private DefaultUserService userService;
    @Autowired
    private MsgApiService msgApiService;

    @Autowired
    private UemUserService uemUserService;
    /**
     * @Author:chenxf
     * @Description: 管理员申请审核列表分页查询
     * @Date: 14:52 2020/11/3
     * @Param: [companyManagerQueryVO]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.QueryCompanyManagerDTO>
     *
     */
    @Override
    public Page<QueryCompanyManagerDTO> queryByPage(CompanyManagerQueryVO companyManagerQueryVO) {
        if (StringUtils.isNotEmpty(companyManagerQueryVO.getAccount())){
            companyManagerQueryVO.setAccount("%" + companyManagerQueryVO.getAccount() + "%");
        }
        if (StringUtils.isNotEmpty(companyManagerQueryVO.getName())){
            companyManagerQueryVO.setName("%" + companyManagerQueryVO.getName() + "%");
        }
        List<UemCompanyManager> uemCompanyManagerList = QUemCompanyManager.uemCompanyManager.select(QUemCompanyManager.uemCompanyId).where(
                QUemCompanyManager.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ONE)
        ).execute();
        Set<Long> hasMangeCompanyIdSet = uemCompanyManagerList.stream().map(UemCompanyManager::getUemCompanyId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(hasMangeCompanyIdSet)){
            hasMangeCompanyIdSet.add(Long.valueOf("1"));
        }
        List<UemCompanyManager> uemCompanyManagerList1 = QUemCompanyManager.uemCompanyManager.select(QUemCompanyManager.uemCompanyManagerId).where(
                QUemCompanyManager.uemUserCompany.chain(QUemUserCompany.quitTime).isNull()
                        .and(QUemCompanyManager.uemUserCompany.chain(QUemUserCompany.auditStatus).eq$(CodeFinal.AUDIT_STATUS_ZERO))
                        .and(QUemCompanyManager.uemCompany.chain(QUemCompany.uemCompanyId).notIn("uemCompanyIdList"))
        ).execute(ImmutableMap.of("uemCompanyIdList", hasMangeCompanyIdSet));
        Set<Long> notMangeCompanyUserManagerList = uemCompanyManagerList1.stream().map(UemCompanyManager::getUemCompanyManagerId).collect(Collectors.toSet());
        CombinedExpression andExpression = QUemCompanyManager.uemCompanyManager.chain(QUemUser.account).like$(companyManagerQueryVO.getAccount())
                .and(QUemCompanyManager.uemCompanyManager.chain(QUemUser.name).like$(companyManagerQueryVO.getName()))
                .and(QUemCompanyManager.uemCompanyManager.chain(QUemCompany.companyNameCn).eq$(companyManagerQueryVO.getCompanyName()))
                .and(QUemCompanyManager.uemCompanyManager.chain(QUemCompany.auditStatus).eq$(CodeFinal.AUDIT_STATUS_ONE))
                .and(QUemCompanyManager.uemCompanyManager.chain(QUemCompany.isValid).eq$(Boolean.TRUE));
        if (CollectionUtils.isNotEmpty(notMangeCompanyUserManagerList)){
            andExpression = andExpression.and(QUemCompanyManager.uemCompanyManager.chain(QUemUser.blindCompanny).notNull().or(QUemCompanyManager.uemCompanyManagerId.in$(notMangeCompanyUserManagerList)));
        } else {
            andExpression = andExpression.and(QUemCompanyManager.uemCompanyManager.chain(QUemUser.blindCompanny).notNull());
        }
        if (!CodeFinal.AUDIT_STATUS_ALL.equals(companyManagerQueryVO.getAuditStatus())){
            Expression expression = QUemCompanyManager.auditStatus.eq$(companyManagerQueryVO.getAuditStatus());
            andExpression = andExpression.and(expression);
        }
        // 分页查询企业历史记录表
        return QUemCompanyManager.uemCompanyManager.select(
                QUemCompanyManager.uemCompanyManagerId,
                QUemCompanyManager.uemUserId,
                QUemCompanyManager.uemCompanyId,
                QUemCompanyManager.auditStatus,
                QUemCompanyManager.auditTime,
                QUemCompanyManager.createTime,
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.account).as("account"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.name).as("name"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.mobile).as("mobile"),
                QUemCompanyManager.uemCompanyManager.chain(QUemCompany.companyNameCn).as("companyNameCn")
        ).where(
                andExpression
        ).paging(companyManagerQueryVO.getCurrentPage(), companyManagerQueryVO.getPageSize())
                .sorting(QUemCompanyManager.createTime.desc())
                .mapperTo(QueryCompanyManagerDTO.class)
                .execute();
    }
    /**
     * @Author:chenxf
     * @Description: 根据企业管理员表id查询管理员申请信息
     * @Date: 14:53 2020/11/3
     * @Param: [uemCompanyManagerId]
     * @Return:com.share.auth.domain.QueryCompanyManagerDTO
     *
     */
    @Override
    public QueryCompanyManagerDTO queryByUemCompanyManagerId(Long uemCompanyManagerId) {
        List<QueryCompanyManagerDTO> queryCompanyManagerDTO = QUemCompanyManager.uemCompanyManager.select(
                QUemCompanyManager.uemCompanyManagerId,
                QUemCompanyManager.uemUserId,
                QUemCompanyManager.uemCompanyId,
                QUemCompanyManager.auditStatus,
                QUemCompanyManager.auditTime,
                QUemCompanyManager.auditor,
                QUemCompanyManager.auditRemark,
                QUemCompanyManager.fileUrlId,
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.account).as("account"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.name).as("name"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.mobile).as("mobile"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.email).as("email"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.sex).as("sex"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.idCard).as("idCard"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.cardBackUrlId).as("cardBackUrlId"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.cardPositiveUrlId).as("cardPositiveUrlId"),
                QUemCompanyManager.uemCompanyManager.chain(QUemUser.auditStatus).as("idCardStatus"),
                QUemCompanyManager.uemCompanyManager.chain(QUemCompany.companyNameCn).as("companyNameCn"),
                QUemCompanyManager.uemCompanyManager.chain(QSysPlatformUser.name).as("auditorName")
        ).mapperTo(QueryCompanyManagerDTO.class)
                .byId(uemCompanyManagerId);
        return queryCompanyManagerDTO.get(0);
    }
    /**
     * @Author:chenxf
     * @Description: 企业管理员申请审核
     * @Date: 14:53 2020/11/3
     * @Param: [queryCompanyManagerDTO]
     * @Return:void
     *
     */
    @Override
    public void reviewCompanyManager(QueryCompanyManagerDTO queryCompanyManagerDTO) {

        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
        // 更新企业管理员表数据
        UemCompanyManager uemCompanyManager = QUemCompanyManager.uemCompanyManager.selectOne().byId(queryCompanyManagerDTO.getUemCompanyManagerId());
        if(Objects.isNull(uemCompanyManager)){
            log.error("根据uemCompanyManagerId：{}，查询管理员申请表uem_company_manager，未查询到记录", queryCompanyManagerDTO.getUemCompanyManagerId());
            throw new BusinessRuntimeException("管理员申请记录不存在，请确认！");
        }
        if(!CodeFinal.AUDIT_STATUS_ZERO.equals(uemCompanyManager.getAuditStatus())){
            log.error("根据uemCompanyManagerId：{}，查询管理员申请表uem_company_manager的记录审核状态为：{}，为已审核", queryCompanyManagerDTO.getUemCompanyManagerId(), uemCompanyManager.getAuditStatus());
            throw new BusinessRuntimeException("当前管理员申请已被审核，请确认！");
        }
        List<UemCompanyManager> uemCompanyManagerList = QUemCompanyManager.uemCompanyManager.select().where(
                QUemCompanyManager.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ONE)
                        .and(QUemCompanyManager.uemCompanyId.eq$(uemCompanyManager.getUemCompanyId()))).execute();
        // 企业用户关系表数据
        UemUserCompany uemUserCompany =  QUemUserCompany.uemUserCompany.selectOne().where(
                QUemUserCompany.uemUserId.eq(":uemUserId")
                        .and(QUemUserCompany.uemCompanyId.eq(":uemCompanyId")
                                .and(QUemUserCompany.quitTime.isNull()))
        ).execute(ImmutableMap.of("uemUserId",uemCompanyManager.getUemUserId(), "uemCompanyId", uemCompanyManager.getUemCompanyId()));
        if (CodeFinal.AUDIT_STATUS_ZERO.equals(uemUserCompany.getAuditStatus()) && CollectionUtils.isNotEmpty(uemCompanyManagerList)){
            log.error("当前用户申请绑定企业审核状态为：{}，审批未通过，无法审批管理员申请！", uemUserCompany.getAuditStatus());
            throw new BusinessRuntimeException("当前用户绑定企业审批未通过，无法审批管理员申请！");
        }
        uemCompanyManager.setAuditStatus(queryCompanyManagerDTO.getAuditStatus());
        uemCompanyManager.setAuditRemark(queryCompanyManagerDTO.getAuditRemark());
        uemCompanyManager.setAuditTime(new Date());
        uemCompanyManager.setAuditor(user.getUemUserId());
        uemCompanyManager.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemCompanyManager.uemCompanyManager.save(uemCompanyManager);
        // 被审核的用户
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemCompanyManager.getUemUserId());


        // 短信发送vo
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();

        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(uemUser.getMobile());
        // 消息宏参数mgp
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("messageType", "管理员申请");
        if (CodeFinal.AUDIT_STATUS_ONE.equals(queryCompanyManagerDTO.getAuditStatus())) {
            uemUserCompany.setUserRole(true);
            if (CodeFinal.AUDIT_STATUS_ZERO.equals(uemUserCompany.getAuditStatus())) {
                uemUserCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
                uemUser.setBlindCompanny(uemUserCompany.getUemCompanyId());
                uemUser.setBlindCompannyTime(new Date());
                // 赋予默认用户角色
                uemUserService.defaultUemUserRole(uemUserCompany.getUemUserId(), uemUserCompany.getUemCompanyId());
            }
            // 更新用户企业关系表数据
            uemUserCompany.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QUemUserCompany.uemUserCompany.save(uemUserCompany);
            uemUser.setUserType(CodeFinal.USER_TYPE_TWO);
            // 审核通过：“【国交账号权限】您的管理员申请已通过，感谢使用。”；
            // 短信发送模版编号
            msgSmsApiVO.setSmsTemplateCode(reviewSuccessMsgTemplate);
        }else {
            uemUserCompany.setUserRole(false);
            uemUser.setUserType(CodeFinal.USER_TYPE_ONE);
            if (CodeFinal.AUDIT_STATUS_ZERO.equals(uemUserCompany.getAuditStatus())){
                QUemUserCompany.uemUserCompany.delete(uemUserCompany);
                uemUser.setUserType(CodeFinal.USER_TYPE_ZERO);
                uemUser.setBlindCompannyTime(null);
                uemUser.setBlindCompanny(null);
            }
            // 审核拒绝：“【国交账号权限】您的管理员申请已拒绝，拒绝信息：XXXXXXX（如未录入审批备注，则无该段描述），感谢使用。”
            // 短信发送模版编号
            msgSmsApiVO.setSmsTemplateCode(reviewFailedMsgTemplate);
            if (StringUtils.isNotEmpty(queryCompanyManagerDTO.getAuditRemark())){
                content.put("reviewRemark","拒绝信息：" + queryCompanyManagerDTO.getAuditRemark() + ",");
            }else {
                content.put("reviewRemark","");
            }
        }

        // 更新用户表数据
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemUser.uemUser.save(uemUser);
        marco.put(CodeFinal.CONTENT, content);

        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        log.info("短信消息宏参数传参： {}", marco);
        // 调用公共服务接口发送短信
        msgApiService.sendMsg(msgSmsApiVO);
    }

}
