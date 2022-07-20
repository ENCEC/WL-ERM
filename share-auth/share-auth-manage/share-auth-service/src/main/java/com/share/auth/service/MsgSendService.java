package com.share.auth.service;

import java.util.Map;

/**
 * @author tujx
 * @description 短信发送接口
 * @date 2021/02/20
 */
public interface MsgSendService {


    /**
     * 短信发送
     *
     * @param templateNo 模板编码
     * @param marcoMap 参数键值对
     * @param receiveMobileStr 接收手机号码
     */
    void sendMobileMsg(String templateNo, Map<String, Object> marcoMap, String receiveMobileStr);

    /**
     * 短信通知用户-用户注册成功
     *
     * @param account 账号
     * @param mobile 手机号
     * @return
     * @throws
     * @author tujx
     */
    void registeSuccessSendMsg(String account, String mobile);


    /**
     * 短信通知平台客服-有待审核实名认证
     *
     * @param
     * @return
     * @throws
     * @author tujx
     */
    void notifyAuditRealNameAuth();


    /**
     * 短信通知平台客服-有待审核企业资质
     *
     * @param companyName 企业名称
     * @return
     * @throws
     * @author tujx
     */
    void notifyAuditCompany(String companyName);


    /**
     * 短信通知平台客服-有待审核企业管理员
     *
     * @param companyName 企业名称
     * @return
     * @throws
     * @author tujx
     */
    void notifyAuditCompanyManage(String companyName);
}
