package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.EbCounty;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QEbCounty extends BaseModelExpression<EbCounty, Long> {

    public static final BaseModelExpression<EbCounty, Long> ebCounty = new QEbCounty();
    public static final FieldExpression<Date> createTime = ebCounty.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> creator = ebCounty.fieldOf("creator", String.class);
    public static final FieldExpression<String> ebcoCityCode = ebCounty.fieldOf("ebcoCityCode", String.class);
    public static final FieldExpression<String> ebcoCountyCode = ebCounty.fieldOf("ebcoCountyCode", String.class);
    public static final FieldExpression<String> ebcoCountyNameCn = ebCounty.fieldOf("ebcoCountyNameCn", String.class);
    public static final FieldExpression<String> ebcoCountyNameEn = ebCounty.fieldOf("ebcoCountyNameEn", String.class);
    public static final FieldExpression<Long> ebcoEbciId = ebCounty.fieldOf("ebcoEbciId", Long.class);
    public static final FieldExpression<Long> ebcoId = ebCounty.fieldOf("ebcoId", Long.class);
    public static final FieldExpression<Date> ebcoSubdate1 = ebCounty.fieldOf("ebcoSubdate1", Date.class);
    public static final FieldExpression<Date> ebcoSubdate2 = ebCounty.fieldOf("ebcoSubdate2", Date.class);
    public static final FieldExpression<Date> ebcoSubdate3 = ebCounty.fieldOf("ebcoSubdate3", Date.class);
    public static final FieldExpression<BigDecimal> ebcoSubnum1 = ebCounty.fieldOf("ebcoSubnum1", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebcoSubnum2 = ebCounty.fieldOf("ebcoSubnum2", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebcoSubnum3 = ebCounty.fieldOf("ebcoSubnum3", BigDecimal.class);
    public static final FieldExpression<String> ebcoSubstr1 = ebCounty.fieldOf("ebcoSubstr1", String.class);
    public static final FieldExpression<String> ebcoSubstr2 = ebCounty.fieldOf("ebcoSubstr2", String.class);
    public static final FieldExpression<String> ebcoSubstr3 = ebCounty.fieldOf("ebcoSubstr3", String.class);
    public static final FieldExpression<String> modifier = ebCounty.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = ebCounty.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<BigDecimal> recVer = ebCounty.fieldOf("recVer", BigDecimal.class);


    public QEbCounty() {
        super("EbCounty", EbCounty.class);
    }

    QEbCounty(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "EbCounty", EbCounty.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return ebcoId;
    }
}
