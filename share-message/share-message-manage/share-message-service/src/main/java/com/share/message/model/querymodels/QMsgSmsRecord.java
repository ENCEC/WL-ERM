package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgSmsRecord;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgSmsRecord extends BaseModelExpression<MsgSmsRecord, Long> {

    public static final BaseModelExpression<MsgSmsRecord, Long> msgSmsRecord = new QMsgSmsRecord();
    public static final FieldExpression<String> businessSystemId = msgSmsRecord.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> channelName = msgSmsRecord.fieldOf("channelName", String.class);
    public static final FieldExpression<String> content = msgSmsRecord.fieldOf("content", String.class);
    public static final FieldExpression<Long> createCompanyId = msgSmsRecord.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgSmsRecord.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgSmsRecord.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgSmsRecord.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgSmsRecord.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> errorCode = msgSmsRecord.fieldOf("errorCode", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgSmsRecord.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isSuccess = msgSmsRecord.fieldOf("isSuccess", Boolean.class);
    public static final FieldExpression<String> mobile = msgSmsRecord.fieldOf("mobile", String.class);
    public static final FieldExpression<Long> modifierId = msgSmsRecord.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgSmsRecord.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgSmsRecord.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgSmsRecord.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgSmsRecord.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgSmsRecordId = msgSmsRecord.fieldOf("msgSmsRecordId", Long.class);
    public static final FieldExpression<String> nationCode = msgSmsRecord.fieldOf("nationCode", String.class);
    public static final FieldExpression<String> reason = msgSmsRecord.fieldOf("reason", String.class);
    public static final FieldExpression<Integer> recordVersion = msgSmsRecord.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> retryCount = msgSmsRecord.fieldOf("retryCount", Integer.class);
    public static final FieldExpression<String> smsTemplateCode = msgSmsRecord.fieldOf("smsTemplateCode", String.class);
    public static final FieldExpression<String> smsTemplateName = msgSmsRecord.fieldOf("smsTemplateName", String.class);
    public static final FieldExpression<String> smsType = msgSmsRecord.fieldOf("smsType", String.class);


    public QMsgSmsRecord() {
        super("MsgSmsRecord", MsgSmsRecord.class);
    }

    QMsgSmsRecord(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgSmsRecord", MsgSmsRecord.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgSmsRecordId;
    }
}
