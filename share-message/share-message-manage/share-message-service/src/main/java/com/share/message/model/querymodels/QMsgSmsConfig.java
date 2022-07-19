package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgSmsConfig;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgSmsConfig extends BaseModelExpression<MsgSmsConfig, Long> {

    public static final BaseModelExpression<MsgSmsConfig, Long> msgSmsConfig = new QMsgSmsConfig();
    public static final FieldExpression<String> appKey = msgSmsConfig.fieldOf("appKey", String.class);
    public static final FieldExpression<String> appSecret = msgSmsConfig.fieldOf("appSecret", String.class);
    public static final FieldExpression<String> businessSystemId = msgSmsConfig.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<String> channelName = msgSmsConfig.fieldOf("channelName", String.class);
    public static final FieldExpression<Long> createCompanyId = msgSmsConfig.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgSmsConfig.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgSmsConfig.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgSmsConfig.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgSmsConfig.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgSmsConfig.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<String> ispAccount = msgSmsConfig.fieldOf("ispAccount", String.class);
    public static final FieldExpression<String> ispNo = msgSmsConfig.fieldOf("ispNo", String.class);
    public static final FieldExpression<String> ispPassword = msgSmsConfig.fieldOf("ispPassword", String.class);
    public static final FieldExpression<Boolean> isValid = msgSmsConfig.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> keyword = msgSmsConfig.fieldOf("keyword", String.class);
    public static final FieldExpression<Long> modifierId = msgSmsConfig.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgSmsConfig.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgSmsConfig.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgSmsConfig.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgSmsConfig.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgSmsConfigId = msgSmsConfig.fieldOf("msgSmsConfigId", Long.class);
    public static final FieldExpression<Integer> priority = msgSmsConfig.fieldOf("priority", Integer.class);
    public static final FieldExpression<Integer> recordVersion = msgSmsConfig.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> smsServiceType = msgSmsConfig.fieldOf("smsServiceType", String.class);
    public static final FieldExpression<String> smsUrl = msgSmsConfig.fieldOf("smsUrl", String.class);


    public QMsgSmsConfig() {
        super("MsgSmsConfig", MsgSmsConfig.class);
    }

    QMsgSmsConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgSmsConfig", MsgSmsConfig.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgSmsConfigId;
    }
}
