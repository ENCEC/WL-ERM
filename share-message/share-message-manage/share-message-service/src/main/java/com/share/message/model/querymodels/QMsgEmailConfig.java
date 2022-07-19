package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgEmailConfig;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgEmailConfig extends BaseModelExpression<MsgEmailConfig, Long> {

    public static final BaseModelExpression<MsgEmailConfig, Long> msgEmailConfig = new QMsgEmailConfig();
    public static final FieldExpression<String> businessSystemId = msgEmailConfig.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Long> createCompanyId = msgEmailConfig.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgEmailConfig.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgEmailConfig.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgEmailConfig.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgEmailConfig.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> emailAddress = msgEmailConfig.fieldOf("emailAddress", String.class);
    public static final FieldExpression<String> emailConfigCode = msgEmailConfig.fieldOf("emailConfigCode", String.class);
    public static final FieldExpression<String> emailConfigName = msgEmailConfig.fieldOf("emailConfigName", String.class);
    public static final FieldExpression<String> emailPassword = msgEmailConfig.fieldOf("emailPassword", String.class);
    public static final FieldExpression<String> emailType = msgEmailConfig.fieldOf("emailType", String.class);
    public static final FieldExpression<String> emailUser = msgEmailConfig.fieldOf("emailUser", String.class);
    public static final FieldExpression<String> host = msgEmailConfig.fieldOf("host", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgEmailConfig.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgEmailConfig.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgEmailConfig.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgEmailConfig.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgEmailConfig.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgEmailConfig.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgEmailConfig.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgEmailConfigId = msgEmailConfig.fieldOf("msgEmailConfigId", Long.class);
    public static final FieldExpression<Integer> port = msgEmailConfig.fieldOf("port", Integer.class);
    public static final FieldExpression<String> protocol = msgEmailConfig.fieldOf("protocol", String.class);
    public static final FieldExpression<Integer> recordVersion = msgEmailConfig.fieldOf("recordVersion", Integer.class);


    public QMsgEmailConfig() {
        super("MsgEmailConfig", MsgEmailConfig.class);
    }

    QMsgEmailConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgEmailConfig", MsgEmailConfig.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgEmailConfigId;
    }
}
