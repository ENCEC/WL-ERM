package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.GtxTrans;

import java.lang.Long;
import java.lang.Short;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QGtxTrans extends BaseModelExpression<GtxTrans, Long> {

    public static final BaseModelExpression<GtxTrans, Long> gtxTrans = new QGtxTrans();
    public static final FieldExpression<Long> gtxTransId = gtxTrans.fieldOf("gtxTransId", Long.class);
    public static final FieldExpression<String> transName = gtxTrans.fieldOf("transName", String.class);
    public static final FieldExpression<String> xid = gtxTrans.fieldOf("xid", String.class);
    public static final FieldExpression<String> identifier = gtxTrans.fieldOf("identifier", String.class);
    public static final FieldExpression<String> participants = gtxTrans.fieldOf("participants", String.class);
    public static final FieldExpression<Short> txStatus = gtxTrans.fieldOf("txStatus", Short.class);
    public static final FieldExpression<Short> txMode = gtxTrans.fieldOf("txMode", Short.class);
    public static final FieldExpression<Date> txCreateTime = gtxTrans.fieldOf("txCreateTime", Date.class);
    public static final FieldExpression<Date> modifyTime = gtxTrans.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> modifyTimeSec = gtxTrans.fieldOf("modifyTimeSec", Long.class);
    public static final FieldExpression<Short> tryCount = gtxTrans.fieldOf("tryCount", Short.class);


    public QGtxTrans() {
        super("GtxTrans", GtxTrans.class);
    }

    QGtxTrans(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "GtxTrans", GtxTrans.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return gtxTransId;
    }
}
