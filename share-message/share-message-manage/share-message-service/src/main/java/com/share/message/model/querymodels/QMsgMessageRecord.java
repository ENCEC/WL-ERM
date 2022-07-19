package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgMessageRecord;

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
public class QMsgMessageRecord extends BaseModelExpression<MsgMessageRecord, Long> {

    public static final BaseModelExpression<MsgMessageRecord, Long> msgMessageRecord = new QMsgMessageRecord();
    public static final FieldExpression<String> businessSystemId = msgMessageRecord.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Long> createCompanyId = msgMessageRecord.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgMessageRecord.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgMessageRecord.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgMessageRecord.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgMessageRecord.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgMessageRecord.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isSuccess = msgMessageRecord.fieldOf("isSuccess", Boolean.class);
    public static final FieldExpression<String> messageContent = msgMessageRecord.fieldOf("messageContent", String.class);
    public static final FieldExpression<String> messageLevel = msgMessageRecord.fieldOf("messageLevel", String.class);
    public static final FieldExpression<String> messageTitle = msgMessageRecord.fieldOf("messageTitle", String.class);
    public static final FieldExpression<String> messageType = msgMessageRecord.fieldOf("messageType", String.class);
    public static final FieldExpression<Long> modifierId = msgMessageRecord.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgMessageRecord.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgMessageRecord.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgMessageRecord.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgMessageRecord.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgMessageId = msgMessageRecord.fieldOf("msgMessageId", Long.class);
    public static final FieldExpression<String> reason = msgMessageRecord.fieldOf("reason", String.class);
    public static final FieldExpression<Integer> recordVersion = msgMessageRecord.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> targetSystemId = msgMessageRecord.fieldOf("targetSystemId", String.class);


    public QMsgMessageRecord() {
        super("MsgMessageRecord", MsgMessageRecord.class);
    }

    QMsgMessageRecord(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgMessageRecord", MsgMessageRecord.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgMessageId;
    }
}
