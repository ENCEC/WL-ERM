package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.WaybillFee;

import java.lang.Long;
import java.math.BigDecimal;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QWaybillFee extends BaseModelExpression<WaybillFee, Long> {

    public static final BaseModelExpression<WaybillFee, Long> waybillFee = new QWaybillFee();
    public static final FieldExpression<Long> waybillId = waybillFee.fieldOf("waybillId", Long.class);
    public static final FieldExpression<BigDecimal> totalFreight = waybillFee.fieldOf("totalFreight", BigDecimal.class);
    public static final FieldExpression<BigDecimal> insuranceFee = waybillFee.fieldOf("insuranceFee", BigDecimal.class);
    public static final FieldExpression<BigDecimal> weight = waybillFee.fieldOf("weight", BigDecimal.class);
    public static final FieldExpression<BigDecimal> volume = waybillFee.fieldOf("volume", BigDecimal.class);


    public QWaybillFee() {
        super("WaybillFee", WaybillFee.class);
    }

    QWaybillFee(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "WaybillFee", WaybillFee.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return waybillId;
    }
}
