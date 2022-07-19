package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgSmsTemplate;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgSmsTemplate extends BaseModelExpression<MsgSmsTemplate, Long> {

    public static final BaseModelExpression<MsgSmsTemplate, Long> msgSmsTemplate = new QMsgSmsTemplate();
    public static final FieldExpression<String> businessSystemId = msgSmsTemplate.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> content = msgSmsTemplate.fieldOf("content", String.class);
    public static final FieldExpression<Long> createCompanyId = msgSmsTemplate.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgSmsTemplate.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgSmsTemplate.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgSmsTemplate.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgSmsTemplate.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> description = msgSmsTemplate.fieldOf("description", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgSmsTemplate.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgSmsTemplate.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgSmsTemplate.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgSmsTemplate.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgSmsTemplate.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgSmsTemplate.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgSmsTemplate.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgSmsTemplateId = msgSmsTemplate.fieldOf("msgSmsTemplateId", Long.class);
    public static final FieldExpression<Integer> recordVersion = msgSmsTemplate.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> smsTemplateCode = msgSmsTemplate.fieldOf("smsTemplateCode", String.class);
    public static final FieldExpression<String> smsTemplateName = msgSmsTemplate.fieldOf("smsTemplateName", String.class);
    public static final FieldExpression<String> smsType = msgSmsTemplate.fieldOf("smsType", String.class);


    public QMsgSmsTemplate() {
        super("MsgSmsTemplate", MsgSmsTemplate.class);
    }

    QMsgSmsTemplate(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgSmsTemplate", MsgSmsTemplate.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgSmsTemplateId;
    }
}
