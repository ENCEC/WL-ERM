package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.StrictNumber;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QStrictNumber extends BaseModelExpression<StrictNumber, Long> {

    public static final BaseModelExpression<StrictNumber, Long> strictNumber = new QStrictNumber();
    public static final FieldExpression<Long> id = strictNumber.fieldOf("id", Long.class);
    public static final FieldExpression<String> numberKey = strictNumber.fieldOf("numberKey", String.class);
    public static final FieldExpression<Integer> status = strictNumber.fieldOf("status", Integer.class);
    public static final FieldExpression<Integer> lastSn = strictNumber.fieldOf("lastSn", Integer.class);
    public static final FieldExpression<Date> createTime = strictNumber.fieldOf("createTime", Date.class);
    public static final FieldExpression<Date> updateTime = strictNumber.fieldOf("updateTime", Date.class);


    public QStrictNumber() {
        super("StrictNumber", StrictNumber.class);
    }

    QStrictNumber(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "StrictNumber", StrictNumber.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
