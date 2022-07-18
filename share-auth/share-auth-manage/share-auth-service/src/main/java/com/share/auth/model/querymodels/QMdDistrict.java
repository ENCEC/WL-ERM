package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.MdCity;
import com.share.auth.model.entity.MdDistrict;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMdDistrict extends BaseModelExpression<MdDistrict, Long> {

    public static final BaseModelExpression<MdDistrict, Long> mdDistrict = new QMdDistrict();
    public static final FieldExpression<String> cityCode = mdDistrict.fieldOf("cityCode", String.class);
    public static final FieldExpression<String> cityName = mdDistrict.fieldOf("cityName", String.class);
    public static final FieldExpression<String> cityNameEn = mdDistrict.fieldOf("cityNameEn", String.class);
    public static final FieldExpression<String> countryCode = mdDistrict.fieldOf("countryCode", String.class);
    public static final FieldExpression<String> countryNameCn = mdDistrict.fieldOf("countryNameCn", String.class);
    public static final FieldExpression<String> countryNameEn = mdDistrict.fieldOf("countryNameEn", String.class);
    public static final FieldExpression<Long> createCompanyId = mdDistrict.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = mdDistrict.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = mdDistrict.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = mdDistrict.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = mdDistrict.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> districtCode = mdDistrict.fieldOf("districtCode", String.class);
    public static final FieldExpression<String> districtName = mdDistrict.fieldOf("districtName", String.class);
    public static final FieldExpression<String> districtNameEn = mdDistrict.fieldOf("districtNameEn", String.class);
    public static final FieldExpression<Boolean> isDeleted = mdDistrict.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = mdDistrict.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> mdCityId = mdDistrict.fieldOf("mdCityId", Long.class);
    public static final FieldExpression<Long> mdCountryId = mdDistrict.fieldOf("mdCountryId", Long.class);
    public static final FieldExpression<Long> mdDistrictId = mdDistrict.fieldOf("mdDistrictId", Long.class);
    public static final FieldExpression<Long> mdProvinceId = mdDistrict.fieldOf("mdProvinceId", Long.class);
    public static final FieldExpression<Long> modifierId = mdDistrict.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = mdDistrict.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = mdDistrict.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = mdDistrict.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = mdDistrict.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> provinceCode = mdDistrict.fieldOf("provinceCode", String.class);
    public static final FieldExpression<String> provinceNameCn = mdDistrict.fieldOf("provinceNameCn", String.class);
    public static final FieldExpression<String> provinceNameEn = mdDistrict.fieldOf("provinceNameEn", String.class);
    public static final FieldExpression<Integer> recordVersion = mdDistrict.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = mdDistrict.fieldOf("remark", String.class);

    public static final BaseModelExpression<MdCity, Long> mdCity = new QMdCity(mdDistrict, "mdCity");

    public QMdDistrict() {
        super("MdDistrict", MdDistrict.class);
    }

    QMdDistrict(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MdDistrict", MdDistrict.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return mdDistrictId;
    }
}
