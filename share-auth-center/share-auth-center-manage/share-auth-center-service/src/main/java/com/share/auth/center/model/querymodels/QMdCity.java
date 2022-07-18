package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.MdCity;
import com.share.auth.center.model.entity.MdProvince;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMdCity extends BaseModelExpression<MdCity, Long> {

    public static final BaseModelExpression<MdCity, Long> mdCity = new QMdCity();
    public static final FieldExpression<String> cityCode = mdCity.fieldOf("cityCode", String.class);
    public static final FieldExpression<String> cityName = mdCity.fieldOf("cityName", String.class);
    public static final FieldExpression<String> cityNameEn = mdCity.fieldOf("cityNameEn", String.class);
    public static final FieldExpression<String> countryCode = mdCity.fieldOf("countryCode", String.class);
    public static final FieldExpression<String> countryNameCn = mdCity.fieldOf("countryNameCn", String.class);
    public static final FieldExpression<String> countryNameEn = mdCity.fieldOf("countryNameEn", String.class);
    public static final FieldExpression<Long> createCompanyId = mdCity.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = mdCity.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = mdCity.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = mdCity.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = mdCity.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = mdCity.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = mdCity.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> mdCityId = mdCity.fieldOf("mdCityId", Long.class);
    public static final FieldExpression<Long> mdCountryId = mdCity.fieldOf("mdCountryId", Long.class);
    public static final FieldExpression<Long> mdProvinceId = mdCity.fieldOf("mdProvinceId", Long.class);
    public static final FieldExpression<Long> modifierId = mdCity.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = mdCity.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = mdCity.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = mdCity.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = mdCity.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> provinceCode = mdCity.fieldOf("provinceCode", String.class);
    public static final FieldExpression<String> provinceNameCn = mdCity.fieldOf("provinceNameCn", String.class);
    public static final FieldExpression<String> provinceNameEn = mdCity.fieldOf("provinceNameEn", String.class);
    public static final FieldExpression<Integer> recordVersion = mdCity.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = mdCity.fieldOf("remark", String.class);

    public static final BaseModelExpression<MdProvince, Long> mdProvince = new QMdProvince(mdCity, "mdProvince");

    public QMdCity() {
        super("MdCity", MdCity.class);
    }

    QMdCity(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MdCity", MdCity.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return mdCityId;
    }
}
