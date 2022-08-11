package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.EbContry;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QEbContry extends BaseModelExpression<EbContry, Long> {

    public static final BaseModelExpression<EbContry, Long> ebContry = new QEbContry();
    public static final FieldExpression<Date> createTime = ebContry.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> creator = ebContry.fieldOf("creator", String.class);
    public static final FieldExpression<String> ebcyCode = ebContry.fieldOf("ebcyCode", String.class);
    public static final FieldExpression<Long> ebcyId = ebContry.fieldOf("ebcyId", Long.class);
    public static final FieldExpression<String> ebcyNameCn = ebContry.fieldOf("ebcyNameCn", String.class);
    public static final FieldExpression<String> ebcyNameEn = ebContry.fieldOf("ebcyNameEn", String.class);
    public static final FieldExpression<String> ebcyShortNameEn = ebContry.fieldOf("ebcyShortNameEn", String.class);
    public static final FieldExpression<Date> ebcySubdate1 = ebContry.fieldOf("ebcySubdate1", Date.class);
    public static final FieldExpression<Date> ebcySubdate2 = ebContry.fieldOf("ebcySubdate2", Date.class);
    public static final FieldExpression<Date> ebcySubdate3 = ebContry.fieldOf("ebcySubdate3", Date.class);
    public static final FieldExpression<BigDecimal> ebcySubnum1 = ebContry.fieldOf("ebcySubnum1", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebcySubnum2 = ebContry.fieldOf("ebcySubnum2", BigDecimal.class);
    public static final FieldExpression<BigDecimal> ebcySubnum3 = ebContry.fieldOf("ebcySubnum3", BigDecimal.class);
    public static final FieldExpression<String> ebcySubstr1 = ebContry.fieldOf("ebcySubstr1", String.class);
    public static final FieldExpression<String> ebcySubstr2 = ebContry.fieldOf("ebcySubstr2", String.class);
    public static final FieldExpression<String> ebcySubstr3 = ebContry.fieldOf("ebcySubstr3", String.class);
    public static final FieldExpression<String> modifier = ebContry.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = ebContry.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<BigDecimal> recVer = ebContry.fieldOf("recVer", BigDecimal.class);


    public QEbContry() {
        super("EbContry", EbContry.class);
    }

    QEbContry(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "EbContry", EbContry.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return ebcyId;
    }
}
