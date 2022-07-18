package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.CsCust;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QCsCust extends BaseModelExpression<CsCust, Long> {

    public static final BaseModelExpression<CsCust, Long> csCust = new QCsCust();
    public static final FieldExpression<Long> csCustId = csCust.fieldOf("csCustId", Long.class);
    public static final FieldExpression<String> bizCsCustId = csCust.fieldOf("bizCsCustId", String.class);
    public static final FieldExpression<String> bizSystemType = csCust.fieldOf("bizSystemType", String.class);
    public static final FieldExpression<String> custCode = csCust.fieldOf("custCode", String.class);
    public static final FieldExpression<String> custAlias = csCust.fieldOf("custAlias", String.class);
    public static final FieldExpression<String> custNameEn = csCust.fieldOf("custNameEn", String.class);
    public static final FieldExpression<String> custNameCn = csCust.fieldOf("custNameCn", String.class);
    public static final FieldExpression<String> countryCode = csCust.fieldOf("countryCode", String.class);
    public static final FieldExpression<String> countryNameCn = csCust.fieldOf("countryNameCn", String.class);
    public static final FieldExpression<String> stateCode = csCust.fieldOf("stateCode", String.class);
    public static final FieldExpression<String> stateName = csCust.fieldOf("stateName", String.class);
    public static final FieldExpression<String> cityCode = csCust.fieldOf("cityCode", String.class);
    public static final FieldExpression<String> cityName = csCust.fieldOf("cityName", String.class);
    public static final FieldExpression<String> zip = csCust.fieldOf("zip", String.class);
    public static final FieldExpression<String> addrCn = csCust.fieldOf("addrCn", String.class);
    public static final FieldExpression<String> addrEn = csCust.fieldOf("addrEn", String.class);
    public static final FieldExpression<String> active = csCust.fieldOf("active", String.class);
    public static final FieldExpression<String> isDeleted = csCust.fieldOf("isDeleted", String.class);
    public static final FieldExpression<String> isOneTimeCustomers = csCust.fieldOf("isOneTimeCustomers", String.class);
    public static final FieldExpression<String> invalidName = csCust.fieldOf("invalidName", String.class);
    public static final FieldExpression<String> invalidTime = csCust.fieldOf("invalidTime", String.class);
    public static final FieldExpression<String> scale = csCust.fieldOf("scale", String.class);
    public static final FieldExpression<String> ownership = csCust.fieldOf("ownership", String.class);
    public static final FieldExpression<String> salesChannels = csCust.fieldOf("salesChannels", String.class);
    public static final FieldExpression<String> isSettlement = csCust.fieldOf("isSettlement", String.class);
    public static final FieldExpression<String> settleCustCode = csCust.fieldOf("settleCustCode", String.class);
    public static final FieldExpression<String> settleCustName = csCust.fieldOf("settleCustName", String.class);
    public static final FieldExpression<String> csFlag = csCust.fieldOf("csFlag", String.class);
    public static final FieldExpression<String> supplierFlag = csCust.fieldOf("supplierFlag", String.class);
    public static final FieldExpression<String> registeredCapitqal = csCust.fieldOf("registeredCapitqal", String.class);
    public static final FieldExpression<String> registeredTime = csCust.fieldOf("registeredTime", String.class);
    public static final FieldExpression<String> unifiedCreditNo = csCust.fieldOf("unifiedCreditNo", String.class);
    public static final FieldExpression<String> clientNo = csCust.fieldOf("clientNo", String.class);
    public static final FieldExpression<String> taxpayerIdentificatioNo = csCust.fieldOf("taxpayerIdentificatioNo", String.class);
    public static final FieldExpression<String> aicRegisteredNo = csCust.fieldOf("aicRegisteredNo", String.class);
    public static final FieldExpression<String> iqbRegisteredNo = csCust.fieldOf("iqbRegisteredNo", String.class);
    public static final FieldExpression<String> customesRegisteredNo = csCust.fieldOf("customesRegisteredNo", String.class);
    public static final FieldExpression<String> fictitiousPerson = csCust.fieldOf("fictitiousPerson", String.class);
    public static final FieldExpression<String> tel = csCust.fieldOf("tel", String.class);
    public static final FieldExpression<String> mobileNo = csCust.fieldOf("mobileNo", String.class);
    public static final FieldExpression<String> email = csCust.fieldOf("email", String.class);
    public static final FieldExpression<String> fax = csCust.fieldOf("fax", String.class);
    public static final FieldExpression<String> custStatus = csCust.fieldOf("custStatus", String.class);
    public static final FieldExpression<String> cdhCode = csCust.fieldOf("cdhCode", String.class);
    public static final FieldExpression<String> vCdhCode = csCust.fieldOf("vCdhCode", String.class);
    public static final FieldExpression<String> custType = csCust.fieldOf("custType", String.class);
    public static final FieldExpression<String> creator = csCust.fieldOf("creator", String.class);
    public static final FieldExpression<String> createOffice = csCust.fieldOf("createOffice", String.class);
    public static final FieldExpression<String> createTime = csCust.fieldOf("createTime", String.class);
    public static final FieldExpression<String> createTimeZone = csCust.fieldOf("createTimeZone", String.class);
    public static final FieldExpression<String> lastModifyor = csCust.fieldOf("lastModifyor", String.class);
    public static final FieldExpression<String> lastModifyOffice = csCust.fieldOf("lastModifyOffice", String.class);
    public static final FieldExpression<String> lastModifyTime = csCust.fieldOf("lastModifyTime", String.class);
    public static final FieldExpression<String> lastModifyTimeZone = csCust.fieldOf("lastModifyTimeZone", String.class);
    public static final FieldExpression<Long> recordVersion = csCust.fieldOf("recordVersion", Long.class);
    public static final FieldExpression<String> principalGroupCode = csCust.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<String> lndustryType = csCust.fieldOf("lndustryType", String.class);
    public static final FieldExpression<String> subLndustryType = csCust.fieldOf("subLndustryType", String.class);
    public static final FieldExpression<String> defaultPaymentTerm = csCust.fieldOf("defaultPaymentTerm", String.class);
    public static final FieldExpression<String> defaultCollectionTerm = csCust.fieldOf("defaultCollectionTerm", String.class);
    public static final FieldExpression<String> remarks = csCust.fieldOf("remarks", String.class);
    public static final FieldExpression<String> taxNature = csCust.fieldOf("taxNature", String.class);
    public static final FieldExpression<String> stakeRelation = csCust.fieldOf("stakeRelation", String.class);
    public static final FieldExpression<String> type = csCust.fieldOf("type", String.class);
    public static final FieldExpression<String> stakeProportion = csCust.fieldOf("stakeProportion", String.class);
    public static final FieldExpression<String> recoveryDate = csCust.fieldOf("recoveryDate", String.class);
    public static final FieldExpression<String> expiryDate = csCust.fieldOf("expiryDate", String.class);
    public static final FieldExpression<String> invoiceMaximum = csCust.fieldOf("invoiceMaximum", String.class);
    public static final FieldExpression<String> cdhMaximum = csCust.fieldOf("cdhMaximum", String.class);
    public static final FieldExpression<String> registeredDate = csCust.fieldOf("registeredDate", String.class);
    public static final FieldExpression<String> importRemark = csCust.fieldOf("importRemark", String.class);
    public static final FieldExpression<String> custOfficeCode = csCust.fieldOf("custOfficeCode", String.class);
    public static final FieldExpression<String> memoryCode = csCust.fieldOf("memoryCode", String.class);
    public static final FieldExpression<String> custOfficeName = csCust.fieldOf("custOfficeName", String.class);
    public static final FieldExpression<String> mdgCustCode = csCust.fieldOf("mdgCustCode", String.class);


    public QCsCust() {
        super("CsCust", CsCust.class);
    }

    QCsCust(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "CsCust", CsCust.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return csCustId;
    }
}
