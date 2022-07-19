package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgMessageTemplate;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgMessageTemplate extends BaseModelExpression<MsgMessageTemplate, Long> {

    public static final BaseModelExpression<MsgMessageTemplate, Long> msgMessageTemplate = new QMsgMessageTemplate();
    public static final FieldExpression<String> businessSystemId = msgMessageTemplate.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> content = msgMessageTemplate.fieldOf("content", String.class);
    public static final FieldExpression<Long> createCompanyId = msgMessageTemplate.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgMessageTemplate.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgMessageTemplate.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgMessageTemplate.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgMessageTemplate.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> description = msgMessageTemplate.fieldOf("description", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgMessageTemplate.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgMessageTemplate.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgMessageTemplate.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgMessageTemplate.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgMessageTemplate.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgMessageTemplate.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgMessageTemplate.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgMessageTemplateId = msgMessageTemplate.fieldOf("msgMessageTemplateId", Long.class);
    public static final FieldExpression<String> msgTemplateCode = msgMessageTemplate.fieldOf("msgTemplateCode", String.class);
    public static final FieldExpression<String> msgTemplateName = msgMessageTemplate.fieldOf("msgTemplateName", String.class);
    public static final FieldExpression<Integer> recordVersion = msgMessageTemplate.fieldOf("recordVersion", Integer.class);


    public QMsgMessageTemplate() {
        super("MsgMessageTemplate", MsgMessageTemplate.class);
    }

    QMsgMessageTemplate(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgMessageTemplate", MsgMessageTemplate.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgMessageTemplateId;
    }
}
