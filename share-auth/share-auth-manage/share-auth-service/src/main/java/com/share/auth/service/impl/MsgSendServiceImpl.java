package com.share.auth.service.impl;

import com.share.auth.constants.CodeFinal;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.QSysPlatformUser;
import com.share.auth.model.querymodels.QUemCompanyManager;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.service.MsgSendService;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendMsgReturnVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tujx
 * @description 短信发送接口实现
 * @date 2021/02/02
 */
@Slf4j
@Service
public class MsgSendServiceImpl implements MsgSendService {

    @Autowired
    private MsgApiService msgApiService;

    @Value("${msg.systemCode}")
    private String systemId;

    @Value("${msg.sms.template.user_regist_success}")
    private String userRegistSuccessMsgTemplate;

    @Value("${msg.sms.template.modify_audit_realname}")
    private String modifyAuditRealnameMsgTemplate;

    @Value("${msg.sms.template.modify_audit_company}")
    private String modifyAuditCompanyMsgTemplate;

    @Value("${msg.sms.template.modify_audit_company_manager}")
    private String modifyAuditCompanyManagerMsgTemplate;

    @Value("${msg.sms.template.modify_audit_company_user}")
    private String modifyAuditCompanyUserMsgTemplate;

    /**
     * 发送短信返回状态代码
     */
    private static final String SEND_MSG_RETURN_CODE = "0";

    /**
     * 待审核事项名称-实名认证
     */
    private static final String AUDIT_TYPE_REAL_NAME_AUTH = "实名认证";


    /**
     * 短信发送
     *
     * @param templateNo 模板编码
     * @param marcoMap 参数键值对
     * @param receiveMobileStr 接收手机号码
     */
    @Override
    public void sendMobileMsg(String templateNo, Map<String, Object> marcoMap, String receiveMobileStr) {
        log.info("调用短信发送，模板编码：" + templateNo + ",接收手机号码：" + receiveMobileStr);
        if (StringUtils.isNotBlank(receiveMobileStr)) {
            Map<String, Object> paramMap = new HashMap<>(16);
            paramMap.put("content", marcoMap);
            MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
            msgSmsApiVO.setSystemId(systemId);
            msgSmsApiVO.setSmsTemplateCode(templateNo);
            msgSmsApiVO.setMarco(paramMap);
            msgSmsApiVO.setAcceptNo(receiveMobileStr);
            try {
                //调用消息服务进行短信发送
                SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
                if (!SEND_MSG_RETURN_CODE.equals(sendMsgReturnVo.getResultCode())) {
                    //发送失败
                    log.error("短信发送失败，" + sendMsgReturnVo.getResultMsg());
                }
            } catch (Exception e) {
                //发送异常
                log.error("短信发送异常", e);
            }
        }
    }


    /**
     * 短信通知-用户注册成功
     *
     * @param account 账号
     * @param mobile 手机号码
     * @return
     * @throws
     * @author tujx
     */
    @Override
    @Async
    public void registeSuccessSendMsg(String account, String mobile) {
        Map<String, Object> marcoMap = new HashMap<>(16);
        if (StringUtils.isNotBlank(account)) {
            marcoMap.put("account", account);
        }
        sendMobileMsg(userRegistSuccessMsgTemplate, marcoMap, mobile);
    }


    /**
     * 短信通知平台客服-有待审核企业资质
     *
     * @param companyName 企业名称
     * @return
     * @throws
     * @author tujx
     */
    @Override
    @Async
    public void notifyAuditCompany(String companyName) {
        Map<String, Object> marcoMap = new HashMap<>(16);
        if (StringUtils.isNotBlank(companyName)) {
            marcoMap.put("companyName", companyName);
            sendMsgToPlatformService(modifyAuditCompanyMsgTemplate, marcoMap);
        }
    }

    /**
     * 短信通知平台客服-有待审核实名认证
     *
     * @return
     * @throws
     * @author tujx
     */
    @Override
    @Async
    public void notifyAuditRealNameAuth() {
        Map<String, Object> marcoMap = new HashMap<>(16);
        marcoMap.put("messageType", AUDIT_TYPE_REAL_NAME_AUTH);
        sendMsgToPlatformService(modifyAuditRealnameMsgTemplate, marcoMap);
    }

    /**
     * 短信通知平台客服-有待审核企业管理员
     *
     * @param companyName 企业名称
     * @return
     * @throws
     * @author tujx
     */
    @Override
    @Async
    public void notifyAuditCompanyManage(String companyName) {
        Map<String, Object> marcoMap = new HashMap<>(16);
        if (StringUtils.isNotBlank(companyName)) {
            marcoMap.put("companyName", companyName);
            sendMsgToPlatformService(modifyAuditCompanyManagerMsgTemplate, marcoMap);
        }
    }

    /**
     * @Author:chenxf
     * @Description: 短信通知企业管理员-有待审核绑定用户
     * @Date: 14:41 2021/2/24
     * @Param: [account, uemCompanyId]
     * @Return:void
     *
     */
    @Override
    public void notifyAuditCompanyUser(String account, Long uemCompanyId) {
        Map<String, Object> marcoMap = new HashMap<>(16);
        if (StringUtils.isNotBlank(account)) {
            marcoMap.put("account", account);
            sendMsgToCompanyManage(modifyAuditCompanyUserMsgTemplate, marcoMap,uemCompanyId);
        }
    }

    /**
     * @Author:chenxf
     * @Description: 发送短信给企业管理员
     * @Date: 14:42 2021/2/24
     * @Param: [modifyAuditCompanyManagerMsgTemplate, marcoMap, uemCompanyId]
     * @Return:void
     *
     */
    private void sendMsgToCompanyManage(String templateNo, Map<String, Object> marcoMap, Long uemCompanyId) {
        String mobile = "";
        List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.mobile).where(
                QUemUser.uemCompanyManager.chain(QUemCompanyManager.uemCompanyId).eq$(uemCompanyId)
                .and(QUemUser.uemCompanyManager.chain(QUemCompanyManager.auditStatus).eq$(CodeFinal.AUDIT_STATUS_ONE))
                .and(QUemUser.isValid.eq$(true))
        ).execute();
        if (CollectionUtils.isNotEmpty(uemUserList)){
            List<String> mobileList = uemUserList.stream().map(UemUser::getMobile).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(mobileList)) {
                //手机号码拼接字符串
                mobile = StringUtils.join(mobileList, ",");
            }
            if (StringUtils.isNotBlank(mobile)) {
                //发送短信
                sendMobileMsg(templateNo, marcoMap, mobile);
            }
        }
    }

    /**
     * 发送短信给客服
     *
     * @param templateNo 模板编码
     * @param marcoMap   参数键值对
     * @return
     * @throws
     * @author tujx
     */
    public void sendMsgToPlatformService(String templateNo, Map<String, Object> marcoMap) {
        String mobile = "";
        //获取平台客服账号
        List<SysPlatformUser> platformUserList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.tel)
                .where(QSysPlatformUser.isValid.eq$(true))
                .execute();
        if (CollectionUtils.isNotEmpty(platformUserList)) {
            List<String> telList = platformUserList.stream().map(SysPlatformUser::getTel).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(telList)) {
                //手机号码拼接字符串
                mobile = StringUtils.join(telList, ",");
            }
        }
        if (StringUtils.isNotBlank(mobile)) {
            //发送短信
            sendMobileMsg(templateNo, marcoMap, mobile);
        }
    }
}
