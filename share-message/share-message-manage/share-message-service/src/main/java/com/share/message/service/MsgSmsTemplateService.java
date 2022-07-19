package com.share.message.service;

import com.share.message.dto.MsgSmsTemplateDto;

import java.util.Map;

/**
 * @Description 短信模板
 * @Author nily
 * @Date 2021/1/14
 * @Time 上午10:19
 */
public interface MsgSmsTemplateService {

    /**
     * 保存短信模板
     * @param msgSmsTemplateDto
     * @return
     * @author nily
     */
    Map<String, Object> saveSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto);

    /**
     * 编辑短信模板
     * @param msgSmsTemplateDto
     * @return
     * @author nily
     */
    Map<String, Object> updateSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto);

    /**
     * 获取短信模板通过 smsTemplateCode systemId
     * @param smsTemplateCode
     * @param systemId
     * @return
     * @author nily
     */
    MsgSmsTemplateDto getSmsTemplateById(String smsTemplateCode, String systemId);

}
