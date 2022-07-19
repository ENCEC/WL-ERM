package com.share.message.service.impl;

import com.gillion.ds.utils.ResultUtils;
import com.share.message.constants.GlobalConstant;
import com.share.message.dto.MsgSmsTemplateDto;
import com.share.message.model.entity.MsgSmsTemplate;
import com.share.message.model.querymodels.QMsgSmsTemplate;
import com.share.message.service.MsgSmsTemplateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 消息宏模板实现类
 * @Author nily
 * @Date 2020/11/2
 * @Time 2:28 下午
 */
@Service
public class MsgSmsTemplateServiceImpl implements MsgSmsTemplateService {

    /**
     * @param msgSmsTemplateDto
     * @return java.util.Map<java.lang.String ,   java.lang.Object>
     * @Description 保存短信模板
     */
    @Override
    public Map<String, Object> saveSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto) {
        Map<String, Object> resultData = validateMsgSmsTemplateValue(msgSmsTemplateDto);
        if (resultData != null) {
            return resultData;
        }
        MsgSmsTemplate msgSmsTemplate = convertToEntity(msgSmsTemplateDto);
        msgSmsTemplate.setRowStatus(GlobalConstant.SAVE);
        List<MsgSmsTemplate> list = QMsgSmsTemplate.msgSmsTemplate.select(QMsgSmsTemplate.msgSmsTemplate.fieldContainer()).where(QMsgSmsTemplate.businessSystemId.eq$(msgSmsTemplate.getBusinessSystemId())).execute();
        for (MsgSmsTemplate smsTemplate : list) {
            if (StringUtils.equals(smsTemplate.getSmsTemplateName(), msgSmsTemplate.getSmsTemplateName())) {
                resultData = ResultUtils.getFailedResultData("模板名称具有唯一性，不能重复");
                return resultData;
            }

        }

        try {
            int save = QMsgSmsTemplate.msgSmsTemplate.tag("MsgSmsTemplateCode").save(msgSmsTemplate);
            if (save == 1) {
                resultData = ResultUtils.getSuccessResultData(msgSmsTemplate);
            } else {
                resultData = ResultUtils.getFailedResultData("saveSmsTemplate 保存失败");
            }
        } catch (Exception e) {
            resultData = ResultUtils.getFailedResultData(" 短信模板保存发生异常");
            return resultData;
        }
        return resultData;
    }


    /**
     * @param msgSmsTemplateDto
     * @return java.util.Map<java.lang.String ,   java.lang.Object>
     * @Description 编辑短信模板
     */
    @Override
    public Map<String, Object> updateSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto) {
        Map<String, Object> resultData = validateMsgSmsTemplateValue(msgSmsTemplateDto);
        if (resultData != null) {
            return resultData;
        }
        MsgSmsTemplate msgSmsTemplate = convertToEntity(msgSmsTemplateDto);
        msgSmsTemplate.setRowStatus(GlobalConstant.UPDATE);

        List<MsgSmsTemplate> list = QMsgSmsTemplate.msgSmsTemplate.select(QMsgSmsTemplate.msgSmsTemplate.fieldContainer()).where(QMsgSmsTemplate.businessSystemId.eq$(msgSmsTemplate.getBusinessSystemId()).and(QMsgSmsTemplate.msgSmsTemplateId.ne$(msgSmsTemplate.getMsgSmsTemplateId()))).execute();
        for (MsgSmsTemplate smsTemplate : list) {
            if (smsTemplate.getSmsTemplateName().equals(msgSmsTemplate.getSmsTemplateName())) {
                resultData = ResultUtils.getFailedResultData("模板名称具有唯一性，不能重复");
                return resultData;
            }
        }

        MsgSmsTemplate msgSmsTemplate1 = QMsgSmsTemplate.msgSmsTemplate.selectOne().byId(msgSmsTemplate.getMsgSmsTemplateId());
        if (msgSmsTemplate1 != null) {
            int update = QMsgSmsTemplate.msgSmsTemplate.save(msgSmsTemplate);
            if (update == 1) {
                resultData = ResultUtils.getSuccessResultData(msgSmsTemplate);
            } else {
                resultData = ResultUtils.getFailedResultData("updateSmsTemplate 更新失败");
            }
        } else {
            resultData = ResultUtils.getFailedResultData("updateSmsTemplate 更新失败 没查到对应msgSmsTemplate");
        }

        return resultData;
    }


    /**
     * @param msgSmsTemplateDto
     * @return com.share.message.model.entity.MsgSmsTemplate
     * @Description dto 转化成 entity
     */

    private MsgSmsTemplate convertToEntity(MsgSmsTemplateDto msgSmsTemplateDto) {
        MsgSmsTemplate msgSmsTemplate = new MsgSmsTemplate();
        if (msgSmsTemplateDto != null) {
            msgSmsTemplate.setMsgSmsTemplateId(msgSmsTemplateDto.getMsgSmsTemplateId());
            msgSmsTemplate.setBusinessSystemId(msgSmsTemplateDto.getBusinessSystemId());
            msgSmsTemplate.setSmsTemplateCode(msgSmsTemplateDto.getSmsTemplateCode());
            msgSmsTemplate.setSmsTemplateName(msgSmsTemplateDto.getSmsTemplateName());
            msgSmsTemplate.setDescription(msgSmsTemplateDto.getDescription());
            msgSmsTemplate.setContent(msgSmsTemplateDto.getContent());
            msgSmsTemplate.setSmsType(msgSmsTemplateDto.getSmsType());
            msgSmsTemplate.setIsValid(true);
            if (msgSmsTemplateDto.getSmsType() == null || msgSmsTemplateDto.getSmsType().length() == 0) {
                msgSmsTemplate.setSmsType("0");
                msgSmsTemplate.setIsValid(true);
            }
        }
        return msgSmsTemplate;
    }

    /**
     * @param smsTemplateCode
     * @param systemId
     * @return com.share.message.dto.MsgSmsTemplateDto
     * @Description 获取短信模板通过 smsTemplateCode systemId
     */
    @Override
    public MsgSmsTemplateDto getSmsTemplateById(String smsTemplateCode, String systemId) {
        MsgSmsTemplate msgSmsTemplate = QMsgSmsTemplate.msgSmsTemplate.selectOne()
                .where(QMsgSmsTemplate.smsTemplateCode.eq$(smsTemplateCode).
                        and(QMsgSmsTemplate.businessSystemId.eq$(systemId)).and(QMsgSmsTemplate.isValid.eq$(true))).execute();
        MsgSmsTemplateDto msgSmsTemplateDto = null;
        if (msgSmsTemplate != null) {
            msgSmsTemplateDto = convertToDto(msgSmsTemplate);
        }

        return msgSmsTemplateDto;
    }

    /**
     * @param msgSmsTemplate
     * @return com.share.message.dto.MsgSmsTemplateDto
     * @Description entity 转成 dto
     */
    private MsgSmsTemplateDto convertToDto(MsgSmsTemplate msgSmsTemplate) {
        MsgSmsTemplateDto msgSmsTemplateDto = new MsgSmsTemplateDto();
        if (msgSmsTemplate != null) {
            msgSmsTemplateDto.setMsgSmsTemplateId(msgSmsTemplate.getMsgSmsTemplateId());
            msgSmsTemplateDto.setBusinessSystemId(msgSmsTemplate.getBusinessSystemId());
            msgSmsTemplateDto.setSmsTemplateCode(msgSmsTemplate.getSmsTemplateCode());
            msgSmsTemplateDto.setSmsTemplateName(msgSmsTemplate.getSmsTemplateName());
            msgSmsTemplateDto.setDescription(msgSmsTemplate.getDescription());
            msgSmsTemplateDto.setContent(msgSmsTemplate.getContent());
            msgSmsTemplateDto.setSmsType(msgSmsTemplate.getSmsType());
            msgSmsTemplateDto.setValid(msgSmsTemplate.getIsValid());
        }
        return msgSmsTemplateDto;
    }


    /**
     * @param msgSmsTemplateDto
     * @return java.util.Map<java.lang.String ,   java.lang.Object>
     * @Description 验证必填参数
     */
    private Map<String, Object> validateMsgSmsTemplateValue(MsgSmsTemplateDto msgSmsTemplateDto) {
        int nameLength = 100;
        int contentLength = 1024;
        int desLength = 500;
        if (StringUtils.isEmpty(msgSmsTemplateDto.getSmsTemplateName())) {
            return ResultUtils.getFailedResultData("消息模板对象 模板名 必填字段为空 ");
        } else if (msgSmsTemplateDto.getSmsTemplateName().length() > nameLength) {
            return ResultUtils.getFailedResultData("消息模板对象 模板名 大于100字符 ");

        }
        if (StringUtils.isEmpty(msgSmsTemplateDto.getContent())) {
            return ResultUtils.getFailedResultData("消息模板对象 模板内容 必填字段为空");
        } else if (msgSmsTemplateDto.getContent().length() > contentLength) {
            return ResultUtils.getFailedResultData("消息模板对象 模板内容 大于1024字符");
        }

        if (msgSmsTemplateDto.getDescription().length() > desLength) {
            return ResultUtils.getFailedResultData("消息模板对象 描述 字段值大于500字符");
        }
        return null;
    }


}
