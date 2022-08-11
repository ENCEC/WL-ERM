package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemCompanyUat;

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
public class QUemCompanyUat extends BaseModelExpression<UemCompanyUat, Long> {

    public static final BaseModelExpression<UemCompanyUat, Long> uemCompanyUat = new QUemCompanyUat();
    public static final FieldExpression<Long> auditor = uemCompanyUat.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemCompanyUat.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemCompanyUat.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemCompanyUat.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Long> belongCompany = uemCompanyUat.fieldOf("belongCompany", Long.class);
    public static final FieldExpression<String> belongCompanyName = uemCompanyUat.fieldOf("belongCompanyName", String.class);
    public static final FieldExpression<String> carrierType = uemCompanyUat.fieldOf("carrierType", String.class);
    public static final FieldExpression<String> companyAbbreviName = uemCompanyUat.fieldOf("companyAbbreviName", String.class);
    public static final FieldExpression<String> companyCode = uemCompanyUat.fieldOf("companyCode", String.class);
    public static final FieldExpression<String> companyNameCn = uemCompanyUat.fieldOf("companyNameCn", String.class);
    public static final FieldExpression<String> companyNameEn = uemCompanyUat.fieldOf("companyNameEn", String.class);
    public static final FieldExpression<String> companyTel = uemCompanyUat.fieldOf("companyTel", String.class);
    public static final FieldExpression<String> contact = uemCompanyUat.fieldOf("contact", String.class);
    public static final FieldExpression<String> contactMail = uemCompanyUat.fieldOf("contactMail", String.class);
    public static final FieldExpression<String> contactTel = uemCompanyUat.fieldOf("contactTel", String.class);
    public static final FieldExpression<Date> createTime = uemCompanyUat.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompanyUat.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompanyUat.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> dataSource = uemCompanyUat.fieldOf("dataSource", String.class);
    public static final FieldExpression<String> fileUrlId = uemCompanyUat.fieldOf("fileUrlId", String.class);
    public static final FieldExpression<String> internationalBusinessFileUrl = uemCompanyUat.fieldOf("internationalBusinessFileUrl", String.class);
    public static final FieldExpression<Date> invalidTime = uemCompanyUat.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isFocusCompany = uemCompanyUat.fieldOf("isFocusCompany", Boolean.class);
    public static final FieldExpression<Boolean> isSuperior = uemCompanyUat.fieldOf("isSuperior", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemCompanyUat.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> legalCard = uemCompanyUat.fieldOf("legalCard", String.class);
    public static final FieldExpression<String> legalName = uemCompanyUat.fieldOf("legalName", String.class);
    public static final FieldExpression<String> legalType = uemCompanyUat.fieldOf("legalType", String.class);
    public static final FieldExpression<String> locAddress = uemCompanyUat.fieldOf("locAddress", String.class);
    public static final FieldExpression<String> locCityCode = uemCompanyUat.fieldOf("locCityCode", String.class);
    public static final FieldExpression<String> locCityName = uemCompanyUat.fieldOf("locCityName", String.class);
    public static final FieldExpression<String> locCountryCode = uemCompanyUat.fieldOf("locCountryCode", String.class);
    public static final FieldExpression<String> locCountryName = uemCompanyUat.fieldOf("locCountryName", String.class);
    public static final FieldExpression<String> locDistrictCode = uemCompanyUat.fieldOf("locDistrictCode", String.class);
    public static final FieldExpression<String> locDistrictName = uemCompanyUat.fieldOf("locDistrictName", String.class);
    public static final FieldExpression<String> locProvinceCode = uemCompanyUat.fieldOf("locProvinceCode", String.class);
    public static final FieldExpression<String> locProvinceName = uemCompanyUat.fieldOf("locProvinceName", String.class);
    public static final FieldExpression<String> memoryCode = uemCompanyUat.fieldOf("memoryCode", String.class);
    public static final FieldExpression<Long> modifierId = uemCompanyUat.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompanyUat.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompanyUat.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> organizationCode = uemCompanyUat.fieldOf("organizationCode", String.class);
    public static final FieldExpression<String> organizationType = uemCompanyUat.fieldOf("organizationType", String.class);
    public static final FieldExpression<String> orgCode = uemCompanyUat.fieldOf("orgCode", String.class);
    public static final FieldExpression<String> orgSeq = uemCompanyUat.fieldOf("orgSeq", String.class);
    public static final FieldExpression<Integer> recordVersion = uemCompanyUat.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> score = uemCompanyUat.fieldOf("score", Integer.class);
    public static final FieldExpression<Long> topCompany = uemCompanyUat.fieldOf("topCompany", Long.class);
    public static final FieldExpression<Long> uemCompanyHistoryId = uemCompanyUat.fieldOf("uemCompanyHistoryId", Long.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompanyUat.fieldOf("uemCompanyId", Long.class);


    public QUemCompanyUat() {
        super("UemCompanyUat", UemCompanyUat.class);
    }

    QUemCompanyUat(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompanyUat", UemCompanyUat.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyId;
    }
}
