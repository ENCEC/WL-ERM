package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.exception.BusinessRuntimeException;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.ReviewCompanyDTO;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemCompanyHistory;
import com.share.auth.model.entity.UemCompanyManager;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.CompanyCheckQueryVO;
import com.share.auth.service.MsgSendService;
import com.share.auth.service.UemCompanyHistoryService;
import com.share.auth.service.UemCompanyService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxf
 * @date 2020-10-29 10:50
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UemCompanyHistoryServiceImpl implements UemCompanyHistoryService {



    /**审批成功短信模版编号*/
    @Value("${msg.sms.template.review_success}")
    private String reviewSuccessMsgTemplate;

    /**审批失败短信模版编号*/
    @Value("${msg.sms.template.review_failed}")
    private String reviewFailedMsgTemplate;
    @Autowired
    private MsgApiService msgApiService;
    @Autowired
    private DefaultUserService userService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private UemCompanyService uemCompanyService;

    @Autowired
    private UemUserService uemUserService;

    /**
     * @Author:chenxf
     * @Description: 分页查询企业历史审核记录表审核记录
     * @Date: 15:55 2020/10/29
     * @Param: [companyCheckQueryVO]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.entity.UemCompany>
     *
     */
    @Override
    public Page<ReviewCompanyDTO> queryByPage(CompanyCheckQueryVO companyCheckQueryVO) {
        // 加上模糊查询
        if (StringUtils.isNotEmpty(companyCheckQueryVO.getCompanyCode())){
            companyCheckQueryVO.setCompanyCode("%" + companyCheckQueryVO.getCompanyCode() + "%");
        }
        if (StringUtils.isNotEmpty(companyCheckQueryVO.getCompanyNameCn())){
            companyCheckQueryVO.setCompanyNameCn("%" + companyCheckQueryVO.getCompanyNameCn() + "%");
        }
        // 如果用户没选择审批状态查询，默认查询未审批记录
        if (Objects.isNull(companyCheckQueryVO.getAuditStatus())){
            companyCheckQueryVO.setAuditStatus(CodeFinal.AUDIT_STATUS_ZERO);
        }
        // 分页查询企业历史记录表
        Page<ReviewCompanyDTO> uemCompanyHistoryPage;
        if (CodeFinal.AUDIT_STATUS_ALL.equals(companyCheckQueryVO.getAuditStatus())){
            // 查询所有审批状态的数据
            uemCompanyHistoryPage = QUemCompany.uemCompany
                    .select(
                            QUemCompany.uemCompany.fieldContainer()
                    )
                    .where(
                            QUemCompany.companyNameCn.like(":companyNameCn")
                                    .and(QUemCompany.companyCode.like(":companyCode"))
                                    .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO))
                    )
                    .paging(companyCheckQueryVO.getCurrentPage(), companyCheckQueryVO.getPageSize())
                    .mapperTo(ReviewCompanyDTO.class)
                    .sorting(QUemCompany.createTime.desc())
                    .execute(companyCheckQueryVO);
        }else {
            // 查询指定审批状态数据
            uemCompanyHistoryPage = QUemCompany.uemCompany
                    .select(
                            QUemCompany.uemCompany.fieldContainer()
                    )
                    .where(
                            QUemCompany.companyNameCn.like(":companyNameCn")
                                    .and(QUemCompany.companyCode.like(":companyCode"))
                                    .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO))
                                    .and(QUemCompany.auditStatus.eq(":auditStatus"))
                    )
                    .paging(companyCheckQueryVO.getCurrentPage(), companyCheckQueryVO.getPageSize())
                    .mapperTo(ReviewCompanyDTO.class)
                    .sorting(QUemCompany.createTime.desc())
                    .execute(companyCheckQueryVO);
        }
        return uemCompanyHistoryPage;
    }
    /**
     * @Author:chenxf
     * @Description: 企业资质审核操作接口
     * @Date: 15:55 2020/10/29
     * @Param: [ReviewCompanyDTO]
     * @Return:void
     *
     */
    @Override
    public void reviewCompany(ReviewCompanyDTO uemCompany) {
        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
        UemCompany uemCompanyOld = QUemCompany.uemCompany.selectOne().byId(uemCompany.getUemCompanyId());
        // 查询当前历史记录表数据
        UemCompanyHistory oldCompanyHistory = QUemCompanyHistory.uemCompanyHistory.selectOne().byId(uemCompanyOld.getUemCompanyHistoryId());
        if(Objects.isNull(oldCompanyHistory)){
            log.error("根据uemCompanyHistoryId：{}，无法查询到uem_company_history表的企业信息", uemCompanyOld.getUemCompanyHistoryId());
            throw new BusinessRuntimeException("企业记录不存在，请确认！");
        }
        if(!CodeFinal.AUDIT_STATUS_ZERO.equals(oldCompanyHistory.getAuditStatus())){
            log.error("根据uemCompanyHistoryId：{}，查询到uem_company_history表的企业信息状态为：{}，已审核", uemCompanyOld.getUemCompanyHistoryId(), oldCompanyHistory.getAuditStatus());
            throw new BusinessRuntimeException("当前企业已被审核，请确认！");
        }
        Date date = new Date();
        // 设置审批信息
        oldCompanyHistory.setAuditRemark(uemCompany.getAuditRemark());
        oldCompanyHistory.setAuditTime(date);
        oldCompanyHistory.setAuditor(user.getUemUserId());
        oldCompanyHistory.setAuditStatus(uemCompany.getAuditStatus());
        uemCompanyOld.setAuditRemark(uemCompany.getAuditRemark());
        uemCompanyOld.setAuditTime(date);
        uemCompanyOld.setAuditor(user.getUemUserId());
        uemCompanyOld.setAuditStatus(uemCompany.getAuditStatus());
        uemCompanyOld.setIsFocusCompany(uemCompany.getIsFocusCompany());
        //企业新增审核失败时，管理员申请同时被拒绝；
        int companyManagerFail = 0;
        // 判断是审批通过还是审批不通过
        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
            // 审批通过
            oldCompanyHistory.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
            if (StringUtils.isEmpty(uemCompanyOld.getCompanyCode())){
                // 如果企业没有物流交换代码，则需要生成物流交换代码，由于数据服务单号生成只支持新增时生成，所以这边先删掉历史记录表记录，再重新保存，生成物流交换代码
                QUemCompanyHistory.uemCompanyHistory.deleteById(oldCompanyHistory.getUemCompanyHistoryId());
                oldCompanyHistory.setUemCompanyHistoryId(null);
                oldCompanyHistory.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                oldCompanyHistory.setCompanyCode(null);
                QUemCompanyHistory.uemCompanyHistory.tag("CompanyCode").save(oldCompanyHistory);
                log.info("重新保存企业记录，生成物流交换代码：" + oldCompanyHistory.getCompanyCode());
                uemCompanyOld.setCompanyCode(oldCompanyHistory.getCompanyCode());
                uemCompanyOld.setUemCompanyHistoryId(oldCompanyHistory.getUemCompanyHistoryId());
            }else {
                // 有物流交换代码，则正常更新历史记录表数据即可
                oldCompanyHistory.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                QUemCompanyHistory.uemCompanyHistory.save(oldCompanyHistory);
            }
            // 保存企业权限配置
            uemCompanyService.addUemCompanyRole(uemCompany.getUemCompanyRoleVoList(), uemCompany.getUemCompanyId());
        }else{
            // 审批不通过，则正常更新历史记录表数据
            oldCompanyHistory.setAuditStatus(CodeFinal.AUDIT_STATUS_TWO);
            oldCompanyHistory.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QUemCompanyHistory.uemCompanyHistory.save(oldCompanyHistory);
             companyManagerFail = QUemCompanyManager.uemCompanyManager.update(QUemCompanyManager.auditStatus, QUemCompanyManager.auditRemark, QUemCompanyManager.auditTime, QUemCompanyManager.auditor)
                    .where(QUemCompanyManager.uemCompanyId.eq(":uemCompanyId"))
                    .execute(CodeFinal.AUDIT_STATUS_TWO, "企业新增审核失败", new Date(), user.getUemUserId(), uemCompany.getUemCompanyId());

        }
        // 更新企业信息表数据
        uemCompanyOld.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemCompanyOld.setCarrierType(CodeFinal.WAY_ZERO);
        QUemCompany.uemCompany.save(uemCompanyOld);

        //绑定这个企业的用户的用户角色，用户类型
        List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.blindCompanny.eq$(uemCompanyOld.getUemCompanyId())).execute();
        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
            // 审核通过更新用户角色为企业普通用户
            uemUserList.forEach(uemUser -> {
                uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                uemUser.setUserType(CodeFinal.USER_TYPE_ONE);
                // 设置默认角色
                uemUserService.defaultUemUserRole(uemUser.getUemUserId(), uemCompanyOld.getUemCompanyId());
            });
        }else {
            // 审批不通过解除绑定用户绑定
            uemUserList.forEach(uemUser -> {
                uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                uemUser.setBlindCompanny(null);
                uemUser.setBlindCompannyTime(null);
            });
            List<Long> uemUserIdList = uemUserList.stream().map(UemUser::getUemUserId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(uemUserIdList)){
                QUemUserRole.uemUserRole.delete().where(QUemUserRole.uemUserId.in$(uemUserIdList)).execute();
            }
            QUemUserCompany.uemUserCompany.delete().where(QUemUserCompany.uemCompanyId.eq$(uemCompanyOld.getUemCompanyId())).execute();
        }
        QUemUser.uemUser.save(uemUserList);
        // 发送短信
        this.sendAuditResultSmsMsg(uemCompany, uemCompanyOld,companyManagerFail);

        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())) {
            //审批通过，查询是否有待审核的企业管理员
            List<UemCompanyManager> uemCompanyManagerList = QUemCompanyManager.uemCompanyManager.select()
                    .where(QUemCompanyManager.uemCompanyId.eq$(uemCompany.getUemCompanyId())
                            .and(QUemCompanyManager.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ZERO)))
                    .execute();
            if (!CollectionUtils.isEmpty(uemCompanyManagerList)) {
                //通知平台客服进行企业管理员审核
                msgSendService.notifyAuditCompanyManage(uemCompanyOld.getCompanyNameCn());
            }
        }
    }

    /**
     * 发送审核结果短信
     * @param uemCompany 审核信息
     * @param uemCompanyOld 旧的公司信息
     */
    private void sendAuditResultSmsMsg(ReviewCompanyDTO uemCompany, UemCompany uemCompanyOld,int companyManagerFail) {
        // 短信发送vo
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(MessageUtil.getCreatePhone(uemCompanyOld.getCreatorId()));
        // 消息宏参数mgp
        Map<String,Object> marco = new HashMap<>(16);
        Map<String,Object> content = new HashMap<>(16);
        content.put("messageType", "企业认证");
        if(companyManagerFail>0){
            content.put("messageType", "企业认证以及管理员申请");
        }

        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemCompany.getAuditStatus())){
            msgSmsApiVO.setSmsTemplateCode(reviewSuccessMsgTemplate);
        } else {
            msgSmsApiVO.setSmsTemplateCode(reviewFailedMsgTemplate);
            if (StringUtils.isNotEmpty(uemCompany.getAuditRemark())){
                content.put("reviewRemark","不通过原因：" + uemCompany.getAuditRemark()+",");
            }else {
                content.put("reviewRemark","");
            }
        }
        marco.put(CodeFinal.CONTENT, content);
        // 短信消息宏参数
        log.info("短信消息宏参数传参： " + marco.toString());
        msgSmsApiVO.setMarco(marco);
        // 调用公共服务接口发送短信
        msgApiService.sendMsg(msgSmsApiVO);
    }

}
