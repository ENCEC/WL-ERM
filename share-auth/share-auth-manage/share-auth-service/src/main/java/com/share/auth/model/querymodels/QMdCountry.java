package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.MdCountry;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMdCountry extends BaseModelExpression<MdCountry, Long> {

    public static final BaseModelExpression<MdCountry, Long> mdCountry = new QMdCountry();
    public static final FieldExpression<String> countryCode = mdCountry.fieldOf("countryCode", String.class);
    public static final FieldExpression<String> countryNameCn = mdCountry.fieldOf("countryNameCn", String.class);
    public static final FieldExpression<String> countryNameEn = mdCountry.fieldOf("countryNameEn", String.class);
    public static final FieldExpression<Long> createCompanyId = mdCountry.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = mdCountry.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = mdCountry.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = mdCountry.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = mdCountry.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = mdCountry.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = mdCountry.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> mdCountryId = mdCountry.fieldOf("mdCountryId", Long.class);
    public static final FieldExpression<Long> modifierId = mdCountry.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = mdCountry.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = mdCountry.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = mdCountry.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = mdCountry.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = mdCountry.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = mdCountry.fieldOf("remark", String.class);


    public QMdCountry() {
        super("MdCountry", MdCountry.class);
    }

    QMdCountry(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MdCountry", MdCountry.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return mdCountryId;
    }
}
