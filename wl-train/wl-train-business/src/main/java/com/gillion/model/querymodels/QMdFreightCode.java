package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.MdFreightCode;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMdFreightCode extends BaseModelExpression<MdFreightCode, Long> {

    public static final BaseModelExpression<MdFreightCode, Long> mdFreightCode = new QMdFreightCode();
    public static final FieldExpression<Long> mdFreightCodeId = mdFreightCode.fieldOf("mdFreightCodeId", Long.class);
    public static final FieldExpression<String> bizSystemId = mdFreightCode.fieldOf("bizSystemId", String.class);
    public static final FieldExpression<String> bizSystemType = mdFreightCode.fieldOf("bizSystemType", String.class);
    public static final FieldExpression<String> freightCode = mdFreightCode.fieldOf("freightCode", String.class);
    public static final FieldExpression<String> memoryCode = mdFreightCode.fieldOf("memoryCode", String.class);
    public static final FieldExpression<String> freightNameEn = mdFreightCode.fieldOf("freightNameEn", String.class);
    public static final FieldExpression<String> freightNameCn = mdFreightCode.fieldOf("freightNameCn", String.class);
    public static final FieldExpression<String> freightGlobalCode = mdFreightCode.fieldOf("freightGlobalCode", String.class);
    public static final FieldExpression<String> feightGlobalName = mdFreightCode.fieldOf("feightGlobalName", String.class);
    public static final FieldExpression<String> shortName = mdFreightCode.fieldOf("shortName", String.class);
    public static final FieldExpression<String> freightType = mdFreightCode.fieldOf("freightType", String.class);
    public static final FieldExpression<String> businessType = mdFreightCode.fieldOf("businessType", String.class);
    public static final FieldExpression<String> invoiceName = mdFreightCode.fieldOf("invoiceName", String.class);
    public static final FieldExpression<String> reportName = mdFreightCode.fieldOf("reportName", String.class);
    public static final FieldExpression<String> currency = mdFreightCode.fieldOf("currency", String.class);
    public static final FieldExpression<String> active = mdFreightCode.fieldOf("active", String.class);
    public static final FieldExpression<String> isDeleted = mdFreightCode.fieldOf("isDeleted", String.class);
    public static final FieldExpression<String> remark = mdFreightCode.fieldOf("remark", String.class);
    public static final FieldExpression<String> settleOffice = mdFreightCode.fieldOf("settleOffice", String.class);
    public static final FieldExpression<String> settleOfficeName = mdFreightCode.fieldOf("settleOfficeName", String.class);
    public static final FieldExpression<String> creator = mdFreightCode.fieldOf("creator", String.class);
    public static final FieldExpression<String> createOffice = mdFreightCode.fieldOf("createOffice", String.class);
    public static final FieldExpression<String> createTime = mdFreightCode.fieldOf("createTime", String.class);
    public static final FieldExpression<String> createTimeZone = mdFreightCode.fieldOf("createTimeZone", String.class);
    public static final FieldExpression<String> lastModifyor = mdFreightCode.fieldOf("lastModifyor", String.class);
    public static final FieldExpression<String> lastModifyOffice = mdFreightCode.fieldOf("lastModifyOffice", String.class);
    public static final FieldExpression<String> lastModifyTime = mdFreightCode.fieldOf("lastModifyTime", String.class);
    public static final FieldExpression<String> lastModifyTimeZone = mdFreightCode.fieldOf("lastModifyTimeZone", String.class);
    public static final FieldExpression<String> principalGroupCode = mdFreightCode.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<Long> recordVersion = mdFreightCode.fieldOf("recordVersion", Long.class);
    public static final FieldExpression<String> invoiceFreightCode = mdFreightCode.fieldOf("invoiceFreightCode", String.class);


    public QMdFreightCode() {
        super("MdFreightCode", MdFreightCode.class);
    }

    QMdFreightCode(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MdFreightCode", MdFreightCode.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return mdFreightCodeId;
    }
}
