package com.share.message.service;

import com.share.message.vo.MsgEmailTemplateVO;
import com.share.message.vo.SendEmailVO;
import com.share.support.result.ResultHelper;

import java.util.Map;

/**
 * 邮件模板配置接口
 *
 * @author tujx
 * @date 2020/10/20
 */
public interface MsgEmailTemplateService {

    /**
     * 新增邮件模板
     *
     * @param msgEmailTemplateVO 新增邮件模板信息VO
     * @return Map
     */
    ResultHelper<String> saveEmailTemplate(MsgEmailTemplateVO msgEmailTemplateVO);


    /**
     * 编辑邮件模板
     *
     * @param msgEmailTemplateVO 编辑邮件模板信息VO
     * @return Map
     * @author tujx
     */
    ResultHelper<String> updateEmailTemplate(MsgEmailTemplateVO msgEmailTemplateVO);

    /**
     * 根据使用方系统code以及邮件模板编码获取模板的宏参数
     *
     * @param systemCode 使用方系统code
     * @param emailTemplateCode 邮件模板编码
     * @return Map
     * @author tujx
     */
    Map<String, Object> getEmailTemplateMarcoByCode(String systemCode, String emailTemplateCode);

    /**
     * 根据模板发送邮件
     *
     * @param sendEmailVO 邮件发送信息VO
     * @return Map
     * @author tujx
     */
    Map<String, Object> sendEmailByTemplate(SendEmailVO sendEmailVO);
}
