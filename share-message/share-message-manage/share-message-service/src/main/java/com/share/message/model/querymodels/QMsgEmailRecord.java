package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgEmailRecord;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgEmailRecord extends BaseModelExpression<MsgEmailRecord, Long> {

    public static final BaseModelExpression<MsgEmailRecord, Long> msgEmailRecord = new QMsgEmailRecord();
    public static final FieldExpression<String> attachmentUrl = msgEmailRecord.fieldOf("attachmentUrl", String.class);
    public static final FieldExpression<String> businessSystemId = msgEmailRecord.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> ccEmail = msgEmailRecord.fieldOf("ccEmail", String.class);
    public static final FieldExpression<String> content = msgEmailRecord.fieldOf("content", String.class);
    public static final FieldExpression<Long> createCompanyId = msgEmailRecord.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgEmailRecord.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgEmailRecord.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgEmailRecord.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgEmailRecord.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> emailTemplateCode = msgEmailRecord.fieldOf("emailTemplateCode", String.class);
    public static final FieldExpression<String> emailTemplateName = msgEmailRecord.fieldOf("emailTemplateName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgEmailRecord.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isSuccess = msgEmailRecord.fieldOf("isSuccess", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgEmailRecord.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgEmailRecord.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgEmailRecord.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgEmailRecord.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgEmailRecord.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgEmailRecordId = msgEmailRecord.fieldOf("msgEmailRecordId", Long.class);
    public static final FieldExpression<String> reason = msgEmailRecord.fieldOf("reason", String.class);
    public static final FieldExpression<Integer> recordVersion = msgEmailRecord.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> sendBatch = msgEmailRecord.fieldOf("sendBatch", String.class);
    public static final FieldExpression<String> subject = msgEmailRecord.fieldOf("subject", String.class);
    public static final FieldExpression<String> toEmail = msgEmailRecord.fieldOf("toEmail", String.class);


    public QMsgEmailRecord() {
        super("MsgEmailRecord", MsgEmailRecord.class);
    }

    QMsgEmailRecord(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgEmailRecord", MsgEmailRecord.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgEmailRecordId;
    }
}
