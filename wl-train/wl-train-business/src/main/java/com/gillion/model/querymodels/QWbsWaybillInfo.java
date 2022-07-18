package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.WbsWaybillInfo;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QWbsWaybillInfo extends BaseModelExpression<WbsWaybillInfo, Long> {

    public static final BaseModelExpression<WbsWaybillInfo, Long> wbsWaybillInfo = new QWbsWaybillInfo();
    public static final FieldExpression<Date> collectDatetime = wbsWaybillInfo.fieldOf("collectDatetime", Date.class);
    public static final FieldExpression<Date> estimateDeliveryTime = wbsWaybillInfo.fieldOf("estimateDeliveryTime", Date.class);
    public static final FieldExpression<String> sendAddressDetail = wbsWaybillInfo.fieldOf("sendAddressDetail", String.class);
    public static final FieldExpression<String> shipperCname = wbsWaybillInfo.fieldOf("shipperCname", String.class);
    public static final FieldExpression<Integer> principalGroupCode = wbsWaybillInfo.fieldOf("principalGroupCode", Integer.class);
    public static final FieldExpression<String> deliveryAreaCode = wbsWaybillInfo.fieldOf("deliveryAreaCode", String.class);
    public static final FieldExpression<String> consigneeCname = wbsWaybillInfo.fieldOf("consigneeCname", String.class);
    public static final FieldExpression<String> shipperMobile = wbsWaybillInfo.fieldOf("shipperMobile", String.class);
    public static final FieldExpression<String> collectEmployeeMobile = wbsWaybillInfo.fieldOf("collectEmployeeMobile", String.class);
    public static final FieldExpression<String> consigneeName = wbsWaybillInfo.fieldOf("consigneeName", String.class);
    public static final FieldExpression<Date> lastModifyTime = wbsWaybillInfo.fieldOf("lastModifyTime", Date.class);
    public static final FieldExpression<String> sendAreaCode = wbsWaybillInfo.fieldOf("sendAreaCode", String.class);
    public static final FieldExpression<String> deliveryAddressDetail = wbsWaybillInfo.fieldOf("deliveryAddressDetail", String.class);
    public static final FieldExpression<Long> waybillId = wbsWaybillInfo.fieldOf("waybillId", Long.class);
    public static final FieldExpression<String> consigneeMobile = wbsWaybillInfo.fieldOf("consigneeMobile", String.class);
    public static final FieldExpression<String> productTypeCode = wbsWaybillInfo.fieldOf("productTypeCode", String.class);
    public static final FieldExpression<Long> collectEmployeeId = wbsWaybillInfo.fieldOf("collectEmployeeId", Long.class);


    public QWbsWaybillInfo() {
        super("WbsWaybillInfo", WbsWaybillInfo.class);
    }

    QWbsWaybillInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "WbsWaybillInfo", WbsWaybillInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return waybillId;
    }
}
