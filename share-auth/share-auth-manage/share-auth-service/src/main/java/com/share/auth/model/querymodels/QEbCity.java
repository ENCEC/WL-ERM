package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.EbCity;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QEbCity extends BaseModelExpression<EbCity, Long> {

    public static final BaseModelExpression<EbCity, Long> ebCity = new QEbCity();
    public static final FieldExpression<Date> createTime = ebCity.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> creator = ebCity.fieldOf("creator", String.class);
    public static final FieldExpression<String> ebciCityCode = ebCity.fieldOf("ebciCityCode", String.class);
    public static final FieldExpression<String> ebciCityNameCn = ebCity.fieldOf("ebciCityNameCn", String.class);
    public static final FieldExpression<String> ebciCityNameEn = ebCity.fieldOf("ebciCityNameEn", String.class);
    public static final FieldExpression<String> ebciContryCode = ebCity.fieldOf("ebciContryCode", String.class);
    public static final FieldExpression<Long> ebciEbpeId = ebCity.fieldOf("ebciEbpeId", Long.class);
    public static final FieldExpression<Long> ebciId = ebCity.fieldOf("ebciId", Long.class);
    public static final FieldExpression<String> ebciProvinceCode = ebCity.fieldOf("ebciProvinceCode", String.class);
    public static final FieldExpression<Date> ebciSubdate1 = ebCity.fieldOf("ebciSubdate1", Date.class);
    public static final FieldExpression<Date> ebciSubdate2 = ebCity.fieldOf("ebciSubdate2", Date.class);
    public static final FieldExpression<Date> ebciSubdate3 = ebCity.fieldOf("ebciSubdate3", Date.class);
    public static final FieldExpression<BigDecimal> ebciSubnum1 = ebCity.fieldOf("ebciSubnum1", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebciSubnum2 = ebCity.fieldOf("ebciSubnum2", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebciSubnum3 = ebCity.fieldOf("ebciSubnum3", BigDecimal.class);
    public static final FieldExpression<String> ebciSubstr1 = ebCity.fieldOf("ebciSubstr1", String.class);
    public static final FieldExpression<String> ebciSubstr2 = ebCity.fieldOf("ebciSubstr2", String.class);
    public static final FieldExpression<String> ebciSubstr3 = ebCity.fieldOf("ebciSubstr3", String.class);
    public static final FieldExpression<String> modifier = ebCity.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = ebCity.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<BigDecimal> recVer = ebCity.fieldOf("recVer", BigDecimal.class);


    public QEbCity() {
        super("EbCity", EbCity.class);
    }

    QEbCity(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "EbCity", EbCity.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return ebciId;
    }
}
