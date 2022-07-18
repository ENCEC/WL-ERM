package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.BcPublicOrder;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QBcPublicOrder extends BaseModelExpression<BcPublicOrder, Long> {

    public static final BaseModelExpression<BcPublicOrder, Long> bcPublicOrder = new QBcPublicOrder();
    public static final FieldExpression<Long> bcPublicOrderId = bcPublicOrder.fieldOf("bcPublicOrderId", Long.class);
    public static final FieldExpression<Long> publicGoOrderId = bcPublicOrder.fieldOf("publicGoOrderId", Long.class);
    public static final FieldExpression<Long> publicProjectId = bcPublicOrder.fieldOf("publicProjectId", Long.class);
    public static final FieldExpression<String> publicBizSystemType = bcPublicOrder.fieldOf("publicBizSystemType", String.class);
    public static final FieldExpression<String> publicSourceType = bcPublicOrder.fieldOf("publicSourceType", String.class);
    public static final FieldExpression<String> publicOrgId = bcPublicOrder.fieldOf("publicOrgId", String.class);
    public static final FieldExpression<String> publicBusinessType = bcPublicOrder.fieldOf("publicBusinessType", String.class);
    public static final FieldExpression<String> publicSubBusinessType = bcPublicOrder.fieldOf("publicSubBusinessType", String.class);
    public static final FieldExpression<String> publicBusinessOrderId = bcPublicOrder.fieldOf("publicBusinessOrderId", String.class);
    public static final FieldExpression<String> publicBusinessDate = bcPublicOrder.fieldOf("publicBusinessDate", String.class);
    public static final FieldExpression<String> publicBusinessFinishDate = bcPublicOrder.fieldOf("publicBusinessFinishDate", String.class);
    public static final FieldExpression<String> publicBusinessBegDate = bcPublicOrder.fieldOf("publicBusinessBegDate", String.class);
    public static final FieldExpression<String> publicBusinessEndDate = bcPublicOrder.fieldOf("publicBusinessEndDate", String.class);
    public static final FieldExpression<String> publicCustBusinessNo = bcPublicOrder.fieldOf("publicCustBusinessNo", String.class);
    public static final FieldExpression<String> publicMblNo = bcPublicOrder.fieldOf("publicMblNo", String.class);
    public static final FieldExpression<String> publicHblNo = bcPublicOrder.fieldOf("publicHblNo", String.class);
    public static final FieldExpression<String> publicJobNo = bcPublicOrder.fieldOf("publicJobNo", String.class);
    public static final FieldExpression<String> publicVesselCode = bcPublicOrder.fieldOf("publicVesselCode", String.class);
    public static final FieldExpression<String> publicVesselName = bcPublicOrder.fieldOf("publicVesselName", String.class);
    public static final FieldExpression<String> publicVoyage = bcPublicOrder.fieldOf("publicVoyage", String.class);
    public static final FieldExpression<String> publicSettleOffice = bcPublicOrder.fieldOf("publicSettleOffice", String.class);
    public static final FieldExpression<String> publicSettleOfficeName = bcPublicOrder.fieldOf("publicSettleOfficeName", String.class);
    public static final FieldExpression<String> publicCanvassionMode = bcPublicOrder.fieldOf("publicCanvassionMode", String.class);
    public static final FieldExpression<String> publicCanvassionDepartment = bcPublicOrder.fieldOf("publicCanvassionDepartment", String.class);
    public static final FieldExpression<String> publicPolCode = bcPublicOrder.fieldOf("publicPolCode", String.class);
    public static final FieldExpression<String> publicPol = bcPublicOrder.fieldOf("publicPol", String.class);
    public static final FieldExpression<String> publicPodCode = bcPublicOrder.fieldOf("publicPodCode", String.class);
    public static final FieldExpression<String> publicPod = bcPublicOrder.fieldOf("publicPod", String.class);
    public static final FieldExpression<String> publicPortOfDestCode = bcPublicOrder.fieldOf("publicPortOfDestCode", String.class);
    public static final FieldExpression<String> publicPortOfDestination = bcPublicOrder.fieldOf("publicPortOfDestination", String.class);
    public static final FieldExpression<String> publicConsignorCode = bcPublicOrder.fieldOf("publicConsignorCode", String.class);
    public static final FieldExpression<String> publicConsignorName = bcPublicOrder.fieldOf("publicConsignorName", String.class);
    public static final FieldExpression<String> publicConsigneeCode = bcPublicOrder.fieldOf("publicConsigneeCode", String.class);
    public static final FieldExpression<String> publicConsigneeName = bcPublicOrder.fieldOf("publicConsigneeName", String.class);
    public static final FieldExpression<String> publicShipperCode = bcPublicOrder.fieldOf("publicShipperCode", String.class);
    public static final FieldExpression<String> publicShipperName = bcPublicOrder.fieldOf("publicShipperName", String.class);
    public static final FieldExpression<String> publicNotifyCode = bcPublicOrder.fieldOf("publicNotifyCode", String.class);
    public static final FieldExpression<String> publicNotifyName = bcPublicOrder.fieldOf("publicNotifyName", String.class);
    public static final FieldExpression<String> publicDestAgentCode = bcPublicOrder.fieldOf("publicDestAgentCode", String.class);
    public static final FieldExpression<String> publicDestAgentName = bcPublicOrder.fieldOf("publicDestAgentName", String.class);
    public static final FieldExpression<String> publicCarrierCode = bcPublicOrder.fieldOf("publicCarrierCode", String.class);
    public static final FieldExpression<String> publicCarrierName = bcPublicOrder.fieldOf("publicCarrierName", String.class);
    public static final FieldExpression<String> publicBookingAgencyCode = bcPublicOrder.fieldOf("publicBookingAgencyCode", String.class);
    public static final FieldExpression<String> publicBookingAgencyName = bcPublicOrder.fieldOf("publicBookingAgencyName", String.class);
    public static final FieldExpression<String> publicBookingProtocolNo = bcPublicOrder.fieldOf("publicBookingProtocolNo", String.class);
    public static final FieldExpression<String> publicEtd = bcPublicOrder.fieldOf("publicEtd", String.class);
    public static final FieldExpression<String> publicEta = bcPublicOrder.fieldOf("publicEta", String.class);
    public static final FieldExpression<String> publicSalesCode = bcPublicOrder.fieldOf("publicSalesCode", String.class);
    public static final FieldExpression<String> publicSalesName = bcPublicOrder.fieldOf("publicSalesName", String.class);
    public static final FieldExpression<String> publicCsCode = bcPublicOrder.fieldOf("publicCsCode", String.class);
    public static final FieldExpression<String> publicCsName = bcPublicOrder.fieldOf("publicCsName", String.class);
    public static final FieldExpression<String> publicOpCode = bcPublicOrder.fieldOf("publicOpCode", String.class);
    public static final FieldExpression<String> publicOpName = bcPublicOrder.fieldOf("publicOpName", String.class);
    public static final FieldExpression<String> publicPaymentMode = bcPublicOrder.fieldOf("publicPaymentMode", String.class);
    public static final FieldExpression<String> publicTransportTerm = bcPublicOrder.fieldOf("publicTransportTerm", String.class);
    public static final FieldExpression<Long> publicNoOfPackage = bcPublicOrder.fieldOf("publicNoOfPackage", Long.class);
    public static final FieldExpression<String> publicPackageType = bcPublicOrder.fieldOf("publicPackageType", String.class);
    public static final FieldExpression<BigDecimal> publicGrossWeight = bcPublicOrder.fieldOf("publicGrossWeight", BigDecimal.class);
    public static final FieldExpression<String> publicGrossWeightUnit = bcPublicOrder.fieldOf("publicGrossWeightUnit", String.class);
    public static final FieldExpression<BigDecimal> publicChargingWeight = bcPublicOrder.fieldOf("publicChargingWeight", BigDecimal.class);
    public static final FieldExpression<String> publicChargingWeightUnit = bcPublicOrder.fieldOf("publicChargingWeightUnit", String.class);
    public static final FieldExpression<BigDecimal> publicCube = bcPublicOrder.fieldOf("publicCube", BigDecimal.class);
    public static final FieldExpression<String> publicCubeUnit = bcPublicOrder.fieldOf("publicCubeUnit", String.class);
    public static final FieldExpression<String> publicCargoDescriptionEn = bcPublicOrder.fieldOf("publicCargoDescriptionEn", String.class);
    public static final FieldExpression<String> publicCtnNum = bcPublicOrder.fieldOf("publicCtnNum", String.class);
    public static final FieldExpression<String> publicCtnNo = bcPublicOrder.fieldOf("publicCtnNo", String.class);
    public static final FieldExpression<String> publicCtnTeu = bcPublicOrder.fieldOf("publicCtnTeu", String.class);
    public static final FieldExpression<String> publicOrderRemark = bcPublicOrder.fieldOf("publicOrderRemark", String.class);
    public static final FieldExpression<String> publicIsHbl = bcPublicOrder.fieldOf("publicIsHbl", String.class);
    public static final FieldExpression<String> publicIsCustomsClearance = bcPublicOrder.fieldOf("publicIsCustomsClearance", String.class);
    public static final FieldExpression<String> publicIsInspection = bcPublicOrder.fieldOf("publicIsInspection", String.class);
    public static final FieldExpression<String> publicIsTruck = bcPublicOrder.fieldOf("publicIsTruck", String.class);
    public static final FieldExpression<String> publicIsWarehouse = bcPublicOrder.fieldOf("publicIsWarehouse", String.class);
    public static final FieldExpression<String> publicIsInsurance = bcPublicOrder.fieldOf("publicIsInsurance", String.class);
    public static final FieldExpression<String> publicIsApproval = bcPublicOrder.fieldOf("publicIsApproval", String.class);
    public static final FieldExpression<String> publicApprovalPersonCode = bcPublicOrder.fieldOf("publicApprovalPersonCode", String.class);
    public static final FieldExpression<String> publicApprovalPersonName = bcPublicOrder.fieldOf("publicApprovalPersonName", String.class);
    public static final FieldExpression<String> publicApprovalDate = bcPublicOrder.fieldOf("publicApprovalDate", String.class);
    public static final FieldExpression<String> isIndependent = bcPublicOrder.fieldOf("isIndependent", String.class);
    public static final FieldExpression<String> isNvocc = bcPublicOrder.fieldOf("isNvocc", String.class);
    public static final FieldExpression<String> flowDirection = bcPublicOrder.fieldOf("flowDirection", String.class);
    public static final FieldExpression<String> mfLineCode = bcPublicOrder.fieldOf("mfLineCode", String.class);
    public static final FieldExpression<String> mfLine = bcPublicOrder.fieldOf("mfLine", String.class);
    public static final FieldExpression<String> publicApprovalRemark = bcPublicOrder.fieldOf("publicApprovalRemark", String.class);
    public static final FieldExpression<String> creator = bcPublicOrder.fieldOf("creator", String.class);
    public static final FieldExpression<String> createOffice = bcPublicOrder.fieldOf("createOffice", String.class);
    public static final FieldExpression<String> createTime = bcPublicOrder.fieldOf("createTime", String.class);
    public static final FieldExpression<String> createTimeZone = bcPublicOrder.fieldOf("createTimeZone", String.class);
    public static final FieldExpression<String> lastModifyor = bcPublicOrder.fieldOf("lastModifyor", String.class);
    public static final FieldExpression<String> lastModifyOffice = bcPublicOrder.fieldOf("lastModifyOffice", String.class);
    public static final FieldExpression<String> lastModifyTime = bcPublicOrder.fieldOf("lastModifyTime", String.class);
    public static final FieldExpression<String> lastModifyTimeZone = bcPublicOrder.fieldOf("lastModifyTimeZone", String.class);
    public static final FieldExpression<String> principalGroupCode = bcPublicOrder.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<Long> recordVersion = bcPublicOrder.fieldOf("recordVersion", Long.class);
    public static final FieldExpression<String> publicCargoDescriptionCn = bcPublicOrder.fieldOf("publicCargoDescriptionCn", String.class);
    public static final FieldExpression<String> publicPackageTypeName = bcPublicOrder.fieldOf("publicPackageTypeName", String.class);
    public static final FieldExpression<String> bppCode = bcPublicOrder.fieldOf("bppCode", String.class);
    public static final FieldExpression<String> bppName = bcPublicOrder.fieldOf("bppName", String.class);
    public static final FieldExpression<String> smallSingleNo = bcPublicOrder.fieldOf("smallSingleNo", String.class);
    public static final FieldExpression<String> parentOrderId = bcPublicOrder.fieldOf("parentOrderId", String.class);
    public static final FieldExpression<String> parentOrderNo = bcPublicOrder.fieldOf("parentOrderNo", String.class);
    public static final FieldExpression<String> parentSubOrderId = bcPublicOrder.fieldOf("parentSubOrderId", String.class);
    public static final FieldExpression<String> parentSubOrderNo = bcPublicOrder.fieldOf("parentSubOrderNo", String.class);
    public static final FieldExpression<String> isInternalFrt = bcPublicOrder.fieldOf("isInternalFrt", String.class);
    public static final FieldExpression<String> publicBusinessTypeName = bcPublicOrder.fieldOf("publicBusinessTypeName", String.class);
    public static final FieldExpression<String> publicOperateType = bcPublicOrder.fieldOf("publicOperateType", String.class);
    public static final FieldExpression<String> publicOperateTypeName = bcPublicOrder.fieldOf("publicOperateTypeName", String.class);
    public static final FieldExpression<String> auditStatus = bcPublicOrder.fieldOf("auditStatus", String.class);
    public static final FieldExpression<String> auditDate = bcPublicOrder.fieldOf("auditDate", String.class);
    public static final FieldExpression<String> auditPersonCode = bcPublicOrder.fieldOf("auditPersonCode", String.class);
    public static final FieldExpression<String> auditPersonName = bcPublicOrder.fieldOf("auditPersonName", String.class);
    public static final FieldExpression<String> publicApprovalFiscalPeriod = bcPublicOrder.fieldOf("publicApprovalFiscalPeriod", String.class);
    public static final FieldExpression<String> publicCanvassionDeptCode = bcPublicOrder.fieldOf("publicCanvassionDeptCode", String.class);
    public static final FieldExpression<String> publicEstimatedIssueDate = bcPublicOrder.fieldOf("publicEstimatedIssueDate", String.class);
    public static final FieldExpression<String> publicEstimatedCompDate = bcPublicOrder.fieldOf("publicEstimatedCompDate", String.class);
    public static final FieldExpression<String> cimcNo = bcPublicOrder.fieldOf("cimcNo", String.class);
    public static final FieldExpression<String> cimcId = bcPublicOrder.fieldOf("cimcId", String.class);
    public static final FieldExpression<String> feeRemark = bcPublicOrder.fieldOf("feeRemark", String.class);
    public static final FieldExpression<BigDecimal> receiveInterest = bcPublicOrder.fieldOf("receiveInterest", BigDecimal.class);
    public static final FieldExpression<BigDecimal> paymentInterest = bcPublicOrder.fieldOf("paymentInterest", BigDecimal.class);
    public static final FieldExpression<BigDecimal> grossInterest = bcPublicOrder.fieldOf("grossInterest", BigDecimal.class);
    public static final FieldExpression<String> publicContactName = bcPublicOrder.fieldOf("publicContactName", String.class);
    public static final FieldExpression<String> publicCooperatorOfficeCode = bcPublicOrder.fieldOf("publicCooperatorOfficeCode", String.class);
    public static final FieldExpression<String> publicCooperatorOfficeName = bcPublicOrder.fieldOf("publicCooperatorOfficeName", String.class);
    public static final FieldExpression<String> publicOverseaOfficeCode = bcPublicOrder.fieldOf("publicOverseaOfficeCode", String.class);
    public static final FieldExpression<String> publicOverseaOfficeName = bcPublicOrder.fieldOf("publicOverseaOfficeName", String.class);
    public static final FieldExpression<String> publicFwPartnerCode = bcPublicOrder.fieldOf("publicFwPartnerCode", String.class);
    public static final FieldExpression<String> publicFwPartnerName = bcPublicOrder.fieldOf("publicFwPartnerName", String.class);
    public static final FieldExpression<String> publicCustomNo = bcPublicOrder.fieldOf("publicCustomNo", String.class);
    public static final FieldExpression<String> flightNo = bcPublicOrder.fieldOf("flightNo", String.class);
    public static final FieldExpression<String> payTypeCode = bcPublicOrder.fieldOf("payTypeCode", String.class);
    public static final FieldExpression<String> loadArea = bcPublicOrder.fieldOf("loadArea", String.class);
    public static final FieldExpression<String> discArea = bcPublicOrder.fieldOf("discArea", String.class);
    public static final FieldExpression<String> berthingTime = bcPublicOrder.fieldOf("berthingTime", String.class);
    public static final FieldExpression<String> publicAtd = bcPublicOrder.fieldOf("publicAtd", String.class);
    public static final FieldExpression<String> publicAta = bcPublicOrder.fieldOf("publicAta", String.class);
    public static final FieldExpression<String> acLockStatus = bcPublicOrder.fieldOf("acLockStatus", String.class);
    public static final FieldExpression<String> acLockDate = bcPublicOrder.fieldOf("acLockDate", String.class);
    public static final FieldExpression<String> acLockPersonCode = bcPublicOrder.fieldOf("acLockPersonCode", String.class);
    public static final FieldExpression<String> acLockPersonName = bcPublicOrder.fieldOf("acLockPersonName", String.class);
    public static final FieldExpression<String> voucherLockStatus = bcPublicOrder.fieldOf("voucherLockStatus", String.class);
    public static final FieldExpression<String> voucherLockDate = bcPublicOrder.fieldOf("voucherLockDate", String.class);
    public static final FieldExpression<String> voucherLockPersonCode = bcPublicOrder.fieldOf("voucherLockPersonCode", String.class);
    public static final FieldExpression<String> voucherLockPersonName = bcPublicOrder.fieldOf("voucherLockPersonName", String.class);
    public static final FieldExpression<String> payTerm = bcPublicOrder.fieldOf("payTerm", String.class);
    public static final FieldExpression<String> publicVesselNameCn = bcPublicOrder.fieldOf("publicVesselNameCn", String.class);


    public QBcPublicOrder() {
        super("BcPublicOrder", BcPublicOrder.class);
    }

    QBcPublicOrder(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "BcPublicOrder", BcPublicOrder.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return bcPublicOrderId;
    }
}
