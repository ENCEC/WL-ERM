package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgMessageNotifier;

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
public class QMsgMessageNotifier extends BaseModelExpression<MsgMessageNotifier, Long> {

    public static final BaseModelExpression<MsgMessageNotifier, Long> msgMessageNotifier = new QMsgMessageNotifier();
    public static final FieldExpression<Long> createCompanyId = msgMessageNotifier.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgMessageNotifier.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgMessageNotifier.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgMessageNotifier.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgMessageNotifier.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgMessageNotifier.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isReaded = msgMessageNotifier.fieldOf("isReaded", Boolean.class);
    public static final FieldExpression<Boolean> isValid = msgMessageNotifier.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgMessageNotifier.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgMessageNotifier.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgMessageNotifier.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgMessageNotifier.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgMessageNotifier.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgMessageId = msgMessageNotifier.fieldOf("msgMessageId", Long.class);
    public static final FieldExpression<Long> msgMessageNotifierId = msgMessageNotifier.fieldOf("msgMessageNotifierId", Long.class);
    public static final FieldExpression<Date> readedTime = msgMessageNotifier.fieldOf("readedTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = msgMessageNotifier.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> userId = msgMessageNotifier.fieldOf("userId", Long.class);
    public static final FieldExpression<String> userName = msgMessageNotifier.fieldOf("userName", String.class);


    public QMsgMessageNotifier() {
        super("MsgMessageNotifier", MsgMessageNotifier.class);
    }

    QMsgMessageNotifier(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgMessageNotifier", MsgMessageNotifier.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgMessageNotifierId;
    }
}
