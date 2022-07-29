package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ProduceMessage;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QProduceMessage extends BaseModelExpression<ProduceMessage, Long> {

    public static final BaseModelExpression<ProduceMessage, Long> produceMessage = new QProduceMessage();
    public static final FieldExpression<Date> createTime = produceMessage.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> createUser = produceMessage.fieldOf("createUser", String.class);
    public static final FieldExpression<Long> id = produceMessage.fieldOf("id", Long.class);
    public static final FieldExpression<byte[]> message = produceMessage.fieldOf("message", byte[].class);
    public static final FieldExpression<String> msgId = produceMessage.fieldOf("msgId", String.class);
    public static final FieldExpression<Integer> retryTimes = produceMessage.fieldOf("retryTimes", Integer.class);
    public static final FieldExpression<Boolean> status = produceMessage.fieldOf("status", Boolean.class);
    public static final FieldExpression<String> topic = produceMessage.fieldOf("topic", String.class);
    public static final FieldExpression<Date> updateTime = produceMessage.fieldOf("updateTime", Date.class);
    public static final FieldExpression<String> updateUser = produceMessage.fieldOf("updateUser", String.class);


    public QProduceMessage() {
        super("ProduceMessage", ProduceMessage.class);
    }

    QProduceMessage(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ProduceMessage", ProduceMessage.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
