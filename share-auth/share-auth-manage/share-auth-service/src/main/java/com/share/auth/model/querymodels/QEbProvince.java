package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.EbProvince;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QEbProvince extends BaseModelExpression<EbProvince, Long> {

    public static final BaseModelExpression<EbProvince, Long> ebProvince = new QEbProvince();
    public static final FieldExpression<Date> createTime = ebProvince.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> creator = ebProvince.fieldOf("creator", String.class);
    public static final FieldExpression<String> ebpeAreaCode = ebProvince.fieldOf("ebpeAreaCode", String.class);
    public static final FieldExpression<String> ebpeContryCode = ebProvince.fieldOf("ebpeContryCode", String.class);
    public static final FieldExpression<Long> ebpeEbcyId = ebProvince.fieldOf("ebpeEbcyId", Long.class);
    public static final FieldExpression<Long> ebpeId = ebProvince.fieldOf("ebpeId", Long.class);
    public static final FieldExpression<String> ebpeProvinceCode = ebProvince.fieldOf("ebpeProvinceCode", String.class);
    public static final FieldExpression<String> ebpeProvinceNameCn = ebProvince.fieldOf("ebpeProvinceNameCn", String.class);
    public static final FieldExpression<String> ebpeProvinceNameEn = ebProvince.fieldOf("ebpeProvinceNameEn", String.class);
    public static final FieldExpression<String> ebpeShortname = ebProvince.fieldOf("ebpeShortname", String.class);
    public static final FieldExpression<String> ebpeStatus = ebProvince.fieldOf("ebpeStatus", String.class);
    public static final FieldExpression<Date> ebpeSubdate1 = ebProvince.fieldOf("ebpeSubdate1", Date.class);
    public static final FieldExpression<Date> ebpeSubdate2 = ebProvince.fieldOf("ebpeSubdate2", Date.class);
    public static final FieldExpression<Date> ebpeSubdate3 = ebProvince.fieldOf("ebpeSubdate3", Date.class);
    public static final FieldExpression<BigDecimal> ebpeSubnum1 = ebProvince.fieldOf("ebpeSubnum1", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebpeSubnum2 = ebProvince.fieldOf("ebpeSubnum2", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebpeSubnum3 = ebProvince.fieldOf("ebpeSubnum3", BigDecimal.class);
    public static final FieldExpression<String> ebpeSubstr1 = ebProvince.fieldOf("ebpeSubstr1", String.class);
    public static final FieldExpression<String> ebpeSubstr2 = ebProvince.fieldOf("ebpeSubstr2", String.class);
    public static final FieldExpression<String> ebpeSubstr3 = ebProvince.fieldOf("ebpeSubstr3", String.class);
    public static final FieldExpression<String> modifier = ebProvince.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = ebProvince.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<BigDecimal> recVer = ebProvince.fieldOf("recVer", BigDecimal.class);


    public QEbProvince() {
        super("EbProvince", EbProvince.class);
    }

    QEbProvince(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "EbProvince", EbProvince.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return ebpeId;
    }
}
