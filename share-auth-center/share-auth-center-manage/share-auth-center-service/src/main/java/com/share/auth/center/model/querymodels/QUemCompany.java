package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.UemCompany;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import com.share.auth.center.model.entity.SysPlatformUser;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemCompany extends BaseModelExpression<UemCompany, Long> {

    public static final BaseModelExpression<UemCompany, Long> uemCompany = new QUemCompany();
    public static final FieldExpression<Long> auditor = uemCompany.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemCompany.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemCompany.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemCompany.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Long> belongCompany = uemCompany.fieldOf("belongCompany", Long.class);
    public static final FieldExpression<String> belongCompanyName = uemCompany.fieldOf("belongCompanyName", String.class);
    public static final FieldExpression<String> carrierType = uemCompany.fieldOf("carrierType", String.class);
    public static final FieldExpression<String> companyAbbreviName = uemCompany.fieldOf("companyAbbreviName", String.class);
    public static final FieldExpression<String> companyCode = uemCompany.fieldOf("companyCode", String.class);
    public static final FieldExpression<String> companyNameCn = uemCompany.fieldOf("companyNameCn", String.class);
    public static final FieldExpression<String> companyNameEn = uemCompany.fieldOf("companyNameEn", String.class);
    public static final FieldExpression<String> companyTel = uemCompany.fieldOf("companyTel", String.class);
    public static final FieldExpression<String> contact = uemCompany.fieldOf("contact", String.class);
    public static final FieldExpression<String> contactMail = uemCompany.fieldOf("contactMail", String.class);
    public static final FieldExpression<String> contactTel = uemCompany.fieldOf("contactTel", String.class);
    public static final FieldExpression<Date> createTime = uemCompany.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompany.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompany.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> dataSource = uemCompany.fieldOf("dataSource", String.class);
    public static final FieldExpression<String> fileUrlId = uemCompany.fieldOf("fileUrlId", String.class);
    public static final FieldExpression<Date> invalidTime = uemCompany.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isFocusCompany = uemCompany.fieldOf("isFocusCompany", Boolean.class);
    public static final FieldExpression<Boolean> isSuperior = uemCompany.fieldOf("isSuperior", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemCompany.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> legalCard = uemCompany.fieldOf("legalCard", String.class);
    public static final FieldExpression<String> legalName = uemCompany.fieldOf("legalName", String.class);
    public static final FieldExpression<String> legalType = uemCompany.fieldOf("legalType", String.class);
    public static final FieldExpression<String> locAddress = uemCompany.fieldOf("locAddress", String.class);
    public static final FieldExpression<String> locCityCode = uemCompany.fieldOf("locCityCode", String.class);
    public static final FieldExpression<String> locCityName = uemCompany.fieldOf("locCityName", String.class);
    public static final FieldExpression<String> locCountryCode = uemCompany.fieldOf("locCountryCode", String.class);
    public static final FieldExpression<String> locCountryName = uemCompany.fieldOf("locCountryName", String.class);
    public static final FieldExpression<String> locDistrictCode = uemCompany.fieldOf("locDistrictCode", String.class);
    public static final FieldExpression<String> locDistrictName = uemCompany.fieldOf("locDistrictName", String.class);
    public static final FieldExpression<String> locProvinceCode = uemCompany.fieldOf("locProvinceCode", String.class);
    public static final FieldExpression<String> locProvinceName = uemCompany.fieldOf("locProvinceName", String.class);
    public static final FieldExpression<String> memoryCode = uemCompany.fieldOf("memoryCode", String.class);
    public static final FieldExpression<Long> modifierId = uemCompany.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompany.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompany.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> organizationCode = uemCompany.fieldOf("organizationCode", String.class);
    public static final FieldExpression<String> organizationType = uemCompany.fieldOf("organizationType", String.class);
    public static final FieldExpression<String> orgCode = uemCompany.fieldOf("orgCode", String.class);
    public static final FieldExpression<String> orgSeq = uemCompany.fieldOf("orgSeq", String.class);
    public static final FieldExpression<Integer> recordVersion = uemCompany.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> score = uemCompany.fieldOf("score", Integer.class);
    public static final FieldExpression<Long> topCompany = uemCompany.fieldOf("topCompany", Long.class);
    public static final FieldExpression<Long> uemCompanyHistoryId = uemCompany.fieldOf("uemCompanyHistoryId", Long.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompany.fieldOf("uemCompanyId", Long.class);
    public static final BaseModelExpression<SysPlatformUser, Long> sysPlatformUser = new QSysPlatformUser(uemCompany, "sysPlatformUser");

    public QUemCompany() {
        super("UemCompany", UemCompany.class);
    }

    QUemCompany(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompany", UemCompany.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyId;
    }
}
