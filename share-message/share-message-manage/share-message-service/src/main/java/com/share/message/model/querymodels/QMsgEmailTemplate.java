package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgEmailAttachment;
import com.share.message.model.entity.MsgEmailConfig;
import com.share.message.model.entity.MsgEmailTemplate;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgEmailTemplate extends BaseModelExpression<MsgEmailTemplate, Long> {

    public static final BaseModelExpression<MsgEmailTemplate, Long> msgEmailTemplate = new QMsgEmailTemplate();
    public static final FieldExpression<String> businessSystemId = msgEmailTemplate.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> content = msgEmailTemplate.fieldOf("content", String.class);
    public static final FieldExpression<Long> createCompanyId = msgEmailTemplate.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgEmailTemplate.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgEmailTemplate.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgEmailTemplate.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgEmailTemplate.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> description = msgEmailTemplate.fieldOf("description", String.class);
    public static final FieldExpression<String> emailTemplateCode = msgEmailTemplate.fieldOf("emailTemplateCode", String.class);
    public static final FieldExpression<String> emailTemplateName = msgEmailTemplate.fieldOf("emailTemplateName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgEmailTemplate.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgEmailTemplate.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgEmailTemplate.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgEmailTemplate.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgEmailTemplate.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgEmailTemplate.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgEmailTemplate.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgEmailConfigId = msgEmailTemplate.fieldOf("msgEmailConfigId", Long.class);
    public static final FieldExpression<Long> msgEmailTemplateId = msgEmailTemplate.fieldOf("msgEmailTemplateId", Long.class);
    public static final FieldExpression<Integer> recordVersion = msgEmailTemplate.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> subject = msgEmailTemplate.fieldOf("subject", String.class);

    public static final BaseModelExpression<MsgEmailAttachment, Long> msgEmailAttachment = new QMsgEmailAttachment(msgEmailTemplate, "msgEmailAttachment");
    public static final BaseModelExpression<MsgEmailConfig, Long> msgEmailConfig = new QMsgEmailConfig(msgEmailTemplate, "msgEmailConfig");

    public QMsgEmailTemplate() {
        super("MsgEmailTemplate", MsgEmailTemplate.class);
    }

    QMsgEmailTemplate(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgEmailTemplate", MsgEmailTemplate.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgEmailTemplateId;
    }
}
