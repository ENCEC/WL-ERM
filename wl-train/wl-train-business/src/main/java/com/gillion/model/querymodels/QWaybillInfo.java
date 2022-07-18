package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.WaybillInfo;

import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QWaybillInfo extends BaseModelExpression<WaybillInfo, Long> {

    public static final BaseModelExpression<WaybillInfo, Long> waybillInfo = new QWaybillInfo();
    public static final FieldExpression<Long> waybillId = waybillInfo.fieldOf("waybillId", Long.class);
    public static final FieldExpression<Long> collectEmployeeId = waybillInfo.fieldOf("collectEmployeeId", Long.class);
    public static final FieldExpression<String> collectEmployeeCname = waybillInfo.fieldOf("collectEmployeeCname", String.class);
    public static final FieldExpression<String> collectEmployeeMobile = waybillInfo.fieldOf("collectEmployeeMobile", String.class);
    public static final FieldExpression<Date> collectDatetime = waybillInfo.fieldOf("collectDatetime", Date.class);
    public static final FieldExpression<String> sendAreaCode = waybillInfo.fieldOf("sendAreaCode", String.class);
    public static final FieldExpression<String> sendAddressDetail = waybillInfo.fieldOf("sendAddressDetail", String.class);
    public static final FieldExpression<String> shipperCname = waybillInfo.fieldOf("shipperCname", String.class);
    public static final FieldExpression<String> shipperMobile = waybillInfo.fieldOf("shipperMobile", String.class);
    public static final FieldExpression<String> consigneeCname = waybillInfo.fieldOf("consigneeCname", String.class);
    public static final FieldExpression<String> consigneeMobile = waybillInfo.fieldOf("consigneeMobile", String.class);
    public static final FieldExpression<String> productTypeCode = waybillInfo.fieldOf("productTypeCode", String.class);
    public static final FieldExpression<String> deliveryAreaCode = waybillInfo.fieldOf("deliveryAreaCode", String.class);
    public static final FieldExpression<String> deliveryAddressDetail = waybillInfo.fieldOf("deliveryAddressDetail", String.class);
    public static final FieldExpression<Date> estimateDeliveryTime = waybillInfo.fieldOf("estimateDeliveryTime", Date.class);


    public QWaybillInfo() {
        super("WaybillInfo", WaybillInfo.class);
    }

    QWaybillInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "WaybillInfo", WaybillInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return waybillId;
    }
}
