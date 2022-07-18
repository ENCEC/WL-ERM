package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.LooseNumber;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QLooseNumber extends BaseModelExpression<LooseNumber, Long> {

    public static final BaseModelExpression<LooseNumber, Long> looseNumber = new QLooseNumber();
    public static final FieldExpression<String> numberKey = looseNumber.fieldOf("numberKey", String.class);
    public static final FieldExpression<Date> createTime = looseNumber.fieldOf("createTime", Date.class);
    public static final FieldExpression<Integer> lastSn = looseNumber.fieldOf("lastSn", Integer.class);
    public static final FieldExpression<Date> updateTime = looseNumber.fieldOf("updateTime", Date.class);
    public static final FieldExpression<Long> id = looseNumber.fieldOf("id", Long.class);


    public QLooseNumber() {
        super("LooseNumber", LooseNumber.class);
    }

    QLooseNumber(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "LooseNumber", LooseNumber.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
