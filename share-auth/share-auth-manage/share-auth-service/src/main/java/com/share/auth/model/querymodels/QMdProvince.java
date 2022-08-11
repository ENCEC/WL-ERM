package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.MdProvince;

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
public class QMdProvince extends BaseModelExpression<MdProvince, Long> {

    public static final BaseModelExpression<MdProvince, Long> mdProvince = new QMdProvince();
    public static final FieldExpression<String> countryCode = mdProvince.fieldOf("countryCode", String.class);
    public static final FieldExpression<String> countryNameCn = mdProvince.fieldOf("countryNameCn", String.class);
    public static final FieldExpression<String> countryNameEn = mdProvince.fieldOf("countryNameEn", String.class);
    public static final FieldExpression<Long> createCompanyId = mdProvince.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = mdProvince.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = mdProvince.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = mdProvince.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = mdProvince.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = mdProvince.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = mdProvince.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> mdCountryId = mdProvince.fieldOf("mdCountryId", Long.class);
    public static final FieldExpression<Long> mdProvinceId = mdProvince.fieldOf("mdProvinceId", Long.class);
    public static final FieldExpression<Long> modifierId = mdProvince.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = mdProvince.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = mdProvince.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = mdProvince.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = mdProvince.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> provinceCode = mdProvince.fieldOf("provinceCode", String.class);
    public static final FieldExpression<String> provinceNameCn = mdProvince.fieldOf("provinceNameCn", String.class);
    public static final FieldExpression<String> provinceNameEn = mdProvince.fieldOf("provinceNameEn", String.class);
    public static final FieldExpression<Integer> recordVersion = mdProvince.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = mdProvince.fieldOf("remark", String.class);


    public QMdProvince() {
        super("MdProvince", MdProvince.class);
    }

    QMdProvince(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MdProvince", MdProvince.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return mdProvinceId;
    }
}
