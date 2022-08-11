package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemCompanyHistory;

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
public class QUemCompanyHistory extends BaseModelExpression<UemCompanyHistory, Long> {

    public static final BaseModelExpression<UemCompanyHistory, Long> uemCompanyHistory = new QUemCompanyHistory();
    public static final FieldExpression<Long> auditor = uemCompanyHistory.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemCompanyHistory.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemCompanyHistory.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemCompanyHistory.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Long> belongCompany = uemCompanyHistory.fieldOf("belongCompany", Long.class);
    public static final FieldExpression<String> belongCompanyName = uemCompanyHistory.fieldOf("belongCompanyName", String.class);
    public static final FieldExpression<String> companyAbbreviName = uemCompanyHistory.fieldOf("companyAbbreviName", String.class);
    public static final FieldExpression<String> companyCode = uemCompanyHistory.fieldOf("companyCode", String.class);
    public static final FieldExpression<String> companyNameCn = uemCompanyHistory.fieldOf("companyNameCn", String.class);
    public static final FieldExpression<String> companyNameEn = uemCompanyHistory.fieldOf("companyNameEn", String.class);
    public static final FieldExpression<String> companyTel = uemCompanyHistory.fieldOf("companyTel", String.class);
    public static final FieldExpression<String> contact = uemCompanyHistory.fieldOf("contact", String.class);
    public static final FieldExpression<String> contactMail = uemCompanyHistory.fieldOf("contactMail", String.class);
    public static final FieldExpression<String> contactTel = uemCompanyHistory.fieldOf("contactTel", String.class);
    public static final FieldExpression<Date> createTime = uemCompanyHistory.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompanyHistory.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompanyHistory.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> dataSource = uemCompanyHistory.fieldOf("dataSource", String.class);
    public static final FieldExpression<String> fileUrlId = uemCompanyHistory.fieldOf("fileUrlId", String.class);
    public static final FieldExpression<Date> invalidTime = uemCompanyHistory.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isSuperior = uemCompanyHistory.fieldOf("isSuperior", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemCompanyHistory.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> legalCard = uemCompanyHistory.fieldOf("legalCard", String.class);
    public static final FieldExpression<String> legalName = uemCompanyHistory.fieldOf("legalName", String.class);
    public static final FieldExpression<String> legalType = uemCompanyHistory.fieldOf("legalType", String.class);
    public static final FieldExpression<String> locAddress = uemCompanyHistory.fieldOf("locAddress", String.class);
    public static final FieldExpression<String> locCityCode = uemCompanyHistory.fieldOf("locCityCode", String.class);
    public static final FieldExpression<String> locCityName = uemCompanyHistory.fieldOf("locCityName", String.class);
    public static final FieldExpression<String> locCountryCode = uemCompanyHistory.fieldOf("locCountryCode", String.class);
    public static final FieldExpression<String> locCountryName = uemCompanyHistory.fieldOf("locCountryName", String.class);
    public static final FieldExpression<String> locDistrictCode = uemCompanyHistory.fieldOf("locDistrictCode", String.class);
    public static final FieldExpression<String> locDistrictName = uemCompanyHistory.fieldOf("locDistrictName", String.class);
    public static final FieldExpression<String> locProvinceCode = uemCompanyHistory.fieldOf("locProvinceCode", String.class);
    public static final FieldExpression<String> locProvinceName = uemCompanyHistory.fieldOf("locProvinceName", String.class);
    public static final FieldExpression<String> memoryCode = uemCompanyHistory.fieldOf("memoryCode", String.class);
    public static final FieldExpression<Long> modifierId = uemCompanyHistory.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompanyHistory.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompanyHistory.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> organizationCode = uemCompanyHistory.fieldOf("organizationCode", String.class);
    public static final FieldExpression<String> organizationType = uemCompanyHistory.fieldOf("organizationType", String.class);
    public static final FieldExpression<Integer> recordVersion = uemCompanyHistory.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> score = uemCompanyHistory.fieldOf("score", Integer.class);
    public static final FieldExpression<Long> uemCompanyHistoryId = uemCompanyHistory.fieldOf("uemCompanyHistoryId", Long.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompanyHistory.fieldOf("uemCompanyId", Long.class);


    public QUemCompanyHistory() {
        super("UemCompanyHistory", UemCompanyHistory.class);
    }

    QUemCompanyHistory(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompanyHistory", UemCompanyHistory.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyHistoryId;
    }
}
