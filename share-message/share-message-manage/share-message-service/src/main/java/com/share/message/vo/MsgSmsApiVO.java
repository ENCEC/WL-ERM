package com.share.message.vo;

import lombok.Data;

import java.util.Map;

/**
 * 短信发送接收外部参数
 * @author wangcl
 * @date wangcl
 */
@Data
public class MsgSmsApiVO {
    /**
     * 使用方业务系统id
     */
    private String systemId;
    /**
     * 短信模板code
     */
    private String smsTemplateCode;
    /**
     * 接收手机号
     */
    private String acceptNo;
    /**
     * 短信消息宏
     */
    private Map<String, Object> marco;
}
