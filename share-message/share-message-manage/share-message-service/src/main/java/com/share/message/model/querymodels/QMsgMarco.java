package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgMarco;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgMarco extends BaseModelExpression<MsgMarco, Long> {

    public static final BaseModelExpression<MsgMarco, Long> msgMarco = new QMsgMarco();
    public static final FieldExpression<String> businessSystemId = msgMarco.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Long> createCompanyId = msgMarco.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgMarco.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgMarco.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgMarco.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgMarco.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> fieldName = msgMarco.fieldOf("fieldName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgMarco.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgMarco.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> marcoNameCn = msgMarco.fieldOf("marcoNameCn", String.class);
    public static final FieldExpression<String> marcoNameEn = msgMarco.fieldOf("marcoNameEn", String.class);
    public static final FieldExpression<Long> modifierId = msgMarco.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgMarco.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgMarco.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgMarco.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgMarco.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgMarcoId = msgMarco.fieldOf("msgMarcoId", Long.class);
    public static final FieldExpression<Integer> recordVersion = msgMarco.fieldOf("recordVersion", Integer.class);


    public QMsgMarco() {
        super("MsgMarco", MsgMarco.class);
    }

    QMsgMarco(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgMarco", MsgMarco.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgMarcoId;
    }
}
