package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.GlVoucher;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QGlVoucher extends BaseModelExpression<GlVoucher, Long> {

    public static final BaseModelExpression<GlVoucher, Long> glVoucher = new QGlVoucher();
    public static final FieldExpression<Long> glVoucherId = glVoucher.fieldOf("glVoucherId", Long.class);
    public static final FieldExpression<String> voucherRemarks = glVoucher.fieldOf("voucherRemarks", String.class);
    public static final FieldExpression<String> voucherCategory = glVoucher.fieldOf("voucherCategory", String.class);
    public static final FieldExpression<String> voucherType = glVoucher.fieldOf("voucherType", String.class);
    public static final FieldExpression<String> voucherNo = glVoucher.fieldOf("voucherNo", String.class);
    public static final FieldExpression<String> externalVoucherNo = glVoucher.fieldOf("externalVoucherNo", String.class);
    public static final FieldExpression<String> seqNo = glVoucher.fieldOf("seqNo", String.class);
    public static final FieldExpression<BigDecimal> attachment = glVoucher.fieldOf("attachment", BigDecimal.class);
    public static final FieldExpression<String> transactionSource = glVoucher.fieldOf("transactionSource", String.class);
    public static final FieldExpression<String> creator = glVoucher.fieldOf("creator", String.class);
    public static final FieldExpression<String> createOffice = glVoucher.fieldOf("createOffice", String.class);
    public static final FieldExpression<String> createTime = glVoucher.fieldOf("createTime", String.class);
    public static final FieldExpression<String> createTimeZone = glVoucher.fieldOf("createTimeZone", String.class);
    public static final FieldExpression<String> lastModifyor = glVoucher.fieldOf("lastModifyor", String.class);
    public static final FieldExpression<String> principalGroupCode = glVoucher.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<String> lastModifyOffice = glVoucher.fieldOf("lastModifyOffice", String.class);
    public static final FieldExpression<String> lastModifyTime = glVoucher.fieldOf("lastModifyTime", String.class);
    public static final FieldExpression<String> lastModifyTimeZone = glVoucher.fieldOf("lastModifyTimeZone", String.class);
    public static final FieldExpression<Long> recordVersion = glVoucher.fieldOf("recordVersion", Long.class);
    public static final FieldExpression<String> transactionNo = glVoucher.fieldOf("transactionNo", String.class);
    public static final FieldExpression<String> voucherDate = glVoucher.fieldOf("voucherDate", String.class);
    public static final FieldExpression<String> voucherState = glVoucher.fieldOf("voucherState", String.class);
    public static final FieldExpression<String> voucherExportState = glVoucher.fieldOf("voucherExportState", String.class);
    public static final FieldExpression<String> voucherExportTime = glVoucher.fieldOf("voucherExportTime", String.class);
    public static final FieldExpression<String> voucherExportPerson = glVoucher.fieldOf("voucherExportPerson", String.class);
    public static final FieldExpression<String> validateUser = glVoucher.fieldOf("validateUser", String.class);
    public static final FieldExpression<String> companyCode = glVoucher.fieldOf("companyCode", String.class);
    public static final FieldExpression<String> referenceCode = glVoucher.fieldOf("referenceCode", String.class);
    public static final FieldExpression<String> fiscalPeriod = glVoucher.fieldOf("fiscalPeriod", String.class);
    public static final FieldExpression<String> voucherExportPersonName = glVoucher.fieldOf("voucherExportPersonName", String.class);
    public static final FieldExpression<String> validateUserName = glVoucher.fieldOf("validateUserName", String.class);
    public static final FieldExpression<String> profitCenter = glVoucher.fieldOf("profitCenter", String.class);
    public static final FieldExpression<String> departmentCode = glVoucher.fieldOf("departmentCode", String.class);
    public static final FieldExpression<String> companyName = glVoucher.fieldOf("companyName", String.class);
    public static final FieldExpression<String> isDiffTaxes = glVoucher.fieldOf("isDiffTaxes", String.class);
    public static final FieldExpression<String> coaName = glVoucher.fieldOf("coaName", String.class);


    public QGlVoucher() {
        super("GlVoucher", GlVoucher.class);
    }

    QGlVoucher(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "GlVoucher", GlVoucher.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return glVoucherId;
    }
}
