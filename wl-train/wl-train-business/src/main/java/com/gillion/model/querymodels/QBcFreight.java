package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.BcFreight;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QBcFreight extends BaseModelExpression<BcFreight, Long> {

    public static final BaseModelExpression<BcFreight, Long> bcFreight = new QBcFreight();
    public static final FieldExpression<String> rpFlag = bcFreight.fieldOf("rpFlag", String.class);
    public static final FieldExpression<Long> sourceFreightId = bcFreight.fieldOf("sourceFreightId", Long.class);
    public static final FieldExpression<String> settleCustCode = bcFreight.fieldOf("settleCustCode", String.class);
    public static final FieldExpression<String> settleCustName = bcFreight.fieldOf("settleCustName", String.class);
    public static final FieldExpression<String> settleCustDeptCode = bcFreight.fieldOf("settleCustDeptCode", String.class);
    public static final FieldExpression<String> settleCustDeptName = bcFreight.fieldOf("settleCustDeptName", String.class);
    public static final FieldExpression<String> settleOfficeDeptCode = bcFreight.fieldOf("settleOfficeDeptCode", String.class);
    public static final FieldExpression<String> settleOfficeDeptName = bcFreight.fieldOf("settleOfficeDeptName", String.class);
    public static final FieldExpression<String> mblNo = bcFreight.fieldOf("mblNo", String.class);
    public static final FieldExpression<String> whCode = bcFreight.fieldOf("whCode", String.class);
    public static final FieldExpression<String> whName = bcFreight.fieldOf("whName", String.class);
    public static final FieldExpression<String> vehicleBrand = bcFreight.fieldOf("vehicleBrand", String.class);
    public static final FieldExpression<String> payTerm = bcFreight.fieldOf("payTerm", String.class);
    public static final FieldExpression<String> ledgerTypeCode = bcFreight.fieldOf("ledgerTypeCode", String.class);
    public static final FieldExpression<Long> replaceId = bcFreight.fieldOf("replaceId", Long.class);
    public static final FieldExpression<String> isSpot = bcFreight.fieldOf("isSpot", String.class);
    public static final FieldExpression<BigDecimal> quantity = bcFreight.fieldOf("quantity", BigDecimal.class);
    public static final FieldExpression<String> unit = bcFreight.fieldOf("unit", String.class);
    public static final FieldExpression<BigDecimal> unitPrice = bcFreight.fieldOf("unitPrice", BigDecimal.class);
    public static final FieldExpression<String> settleCurrencyCode = bcFreight.fieldOf("settleCurrencyCode", String.class);
    public static final FieldExpression<BigDecimal> settleAmount = bcFreight.fieldOf("settleAmount", BigDecimal.class);
    public static final FieldExpression<String> settleOffice = bcFreight.fieldOf("settleOffice", String.class);
    public static final FieldExpression<String> settleOfficeName = bcFreight.fieldOf("settleOfficeName", String.class);
    public static final FieldExpression<String> requestStatus = bcFreight.fieldOf("requestStatus", String.class);
    public static final FieldExpression<String> requestNo = bcFreight.fieldOf("requestNo", String.class);
    public static final FieldExpression<String> requestApproveStatus = bcFreight.fieldOf("requestApproveStatus", String.class);
    public static final FieldExpression<String> listNo = bcFreight.fieldOf("listNo", String.class);
    public static final FieldExpression<String> isTax = bcFreight.fieldOf("isTax", String.class);
    public static final FieldExpression<String> isTaxFree = bcFreight.fieldOf("isTaxFree", String.class);
    public static final FieldExpression<String> taskCode = bcFreight.fieldOf("taskCode", String.class);
    public static final FieldExpression<String> taskName = bcFreight.fieldOf("taskName", String.class);
    public static final FieldExpression<String> subOrderNo = bcFreight.fieldOf("subOrderNo", String.class);
    public static final FieldExpression<String> remark = bcFreight.fieldOf("remark", String.class);
    public static final FieldExpression<BigDecimal> settlementRatio = bcFreight.fieldOf("settlementRatio", BigDecimal.class);
    public static final FieldExpression<String> writeoffType = bcFreight.fieldOf("writeoffType", String.class);
    public static final FieldExpression<String> writeoffStatus = bcFreight.fieldOf("writeoffStatus", String.class);
    public static final FieldExpression<Long> writeoffId = bcFreight.fieldOf("writeoffId", Long.class);
    public static final FieldExpression<Integer> noOfPeriod = bcFreight.fieldOf("noOfPeriod", Integer.class);
    public static final FieldExpression<String> lastModifyor = bcFreight.fieldOf("lastModifyor", String.class);
    public static final FieldExpression<String> lastModifyOffice = bcFreight.fieldOf("lastModifyOffice", String.class);
    public static final FieldExpression<String> lastModifyTime = bcFreight.fieldOf("lastModifyTime", String.class);
    public static final FieldExpression<String> lastModifyTimeZone = bcFreight.fieldOf("lastModifyTimeZone", String.class);
    public static final FieldExpression<String> principalGroupCode = bcFreight.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<Long> recordVersion = bcFreight.fieldOf("recordVersion", Long.class);
    public static final FieldExpression<BigDecimal> transferTaxes = bcFreight.fieldOf("transferTaxes", BigDecimal.class);
    public static final FieldExpression<String> sourceCtnId = bcFreight.fieldOf("sourceCtnId", String.class);
    public static final FieldExpression<String> sourceOrderCtn = bcFreight.fieldOf("sourceOrderCtn", String.class);
    public static final FieldExpression<String> returnDepositNo = bcFreight.fieldOf("returnDepositNo", String.class);
    public static final FieldExpression<String> replaceTypeCode = bcFreight.fieldOf("replaceTypeCode", String.class);
    public static final FieldExpression<String> replaceTypeName = bcFreight.fieldOf("replaceTypeName", String.class);
    public static final FieldExpression<String> receivedStatus = bcFreight.fieldOf("receivedStatus", String.class);
    public static final FieldExpression<String> chargeExpression = bcFreight.fieldOf("chargeExpression", String.class);
    public static final FieldExpression<String> bizReplaceId = bcFreight.fieldOf("bizReplaceId", String.class);
    public static final FieldExpression<String> freightEndDate = bcFreight.fieldOf("freightEndDate", String.class);
    public static final FieldExpression<BigDecimal> allocableBaseCurrencyValue = bcFreight.fieldOf("allocableBaseCurrencyValue", BigDecimal.class);
    public static final FieldExpression<String> bizSystemFreightId = bcFreight.fieldOf("bizSystemFreightId", String.class);
    public static final FieldExpression<String> cntType = bcFreight.fieldOf("cntType", String.class);
    public static final FieldExpression<Long> bcPaymentRequestDocId = bcFreight.fieldOf("bcPaymentRequestDocId", Long.class);
    public static final FieldExpression<String> compNo = bcFreight.fieldOf("compNo", String.class);
    public static final FieldExpression<BigDecimal> allocationExchangeRate = bcFreight.fieldOf("allocationExchangeRate", BigDecimal.class);
    public static final FieldExpression<String> businessCode = bcFreight.fieldOf("businessCode", String.class);
    public static final FieldExpression<BigDecimal> actualRateBase = bcFreight.fieldOf("actualRateBase", BigDecimal.class);
    public static final FieldExpression<String> isNotInvoice = bcFreight.fieldOf("isNotInvoice", String.class);
    public static final FieldExpression<String> confirmStatus = bcFreight.fieldOf("confirmStatus", String.class);
    public static final FieldExpression<String> createTimeZone = bcFreight.fieldOf("createTimeZone", String.class);
    public static final FieldExpression<String> freightCode = bcFreight.fieldOf("freightCode", String.class);
    public static final FieldExpression<String> invoiceSerialNo = bcFreight.fieldOf("invoiceSerialNo", String.class);
    public static final FieldExpression<BigDecimal> baseCurrencyValue = bcFreight.fieldOf("baseCurrencyValue", BigDecimal.class);
    public static final FieldExpression<String> actualSettleCustCode = bcFreight.fieldOf("actualSettleCustCode", String.class);
    public static final FieldExpression<String> estimatedInvoiceType = bcFreight.fieldOf("estimatedInvoiceType", String.class);
    public static final FieldExpression<String> invoiceCode = bcFreight.fieldOf("invoiceCode", String.class);
    public static final FieldExpression<String> createOffice = bcFreight.fieldOf("createOffice", String.class);
    public static final FieldExpression<String> feeRemark = bcFreight.fieldOf("feeRemark", String.class);
    public static final FieldExpression<BigDecimal> diffTaxes = bcFreight.fieldOf("diffTaxes", BigDecimal.class);
    public static final FieldExpression<BigDecimal> estimatedAmount = bcFreight.fieldOf("estimatedAmount", BigDecimal.class);
    public static final FieldExpression<String> compStatus = bcFreight.fieldOf("compStatus", String.class);
    public static final FieldExpression<String> deliveryJobNo = bcFreight.fieldOf("deliveryJobNo", String.class);
    public static final FieldExpression<String> agreementCurrencyCode = bcFreight.fieldOf("agreementCurrencyCode", String.class);
    public static final FieldExpression<String> glArpFiscalPeriod = bcFreight.fieldOf("glArpFiscalPeriod", String.class);
    public static final FieldExpression<Long> bcPublicOrderId = bcFreight.fieldOf("bcPublicOrderId", Long.class);
    public static final FieldExpression<Long> bcAllocationEventId = bcFreight.fieldOf("bcAllocationEventId", Long.class);
    public static final FieldExpression<String> isInternalFrt = bcFreight.fieldOf("isInternalFrt", String.class);
    public static final FieldExpression<Long> bcInvoiceId = bcFreight.fieldOf("bcInvoiceId", Long.class);
    public static final FieldExpression<String> businessOrderId = bcFreight.fieldOf("businessOrderId", String.class);
    public static final FieldExpression<Long> bcPaymentRequestId = bcFreight.fieldOf("bcPaymentRequestId", Long.class);
    public static final FieldExpression<Long> bcChargeId = bcFreight.fieldOf("bcChargeId", Long.class);
    public static final FieldExpression<String> frtBizTypeCode = bcFreight.fieldOf("frtBizTypeCode", String.class);
    public static final FieldExpression<String> bizSystemType = bcFreight.fieldOf("bizSystemType", String.class);
    public static final FieldExpression<String> allocationStatus = bcFreight.fieldOf("allocationStatus", String.class);
    public static final FieldExpression<String> auditDate = bcFreight.fieldOf("auditDate", String.class);
    public static final FieldExpression<String> isDiffTaxes = bcFreight.fieldOf("isDiffTaxes", String.class);
    public static final FieldExpression<String> allocationDate = bcFreight.fieldOf("allocationDate", String.class);
    public static final FieldExpression<String> creator = bcFreight.fieldOf("creator", String.class);
    public static final FieldExpression<BigDecimal> assignableAmount = bcFreight.fieldOf("assignableAmount", BigDecimal.class);
    public static final FieldExpression<String> allocationNo = bcFreight.fieldOf("allocationNo", String.class);
    public static final FieldExpression<String> createTime = bcFreight.fieldOf("createTime", String.class);
    public static final FieldExpression<String> agreementNo = bcFreight.fieldOf("agreementNo", String.class);
    public static final FieldExpression<String> auditPersonCode = bcFreight.fieldOf("auditPersonCode", String.class);
    public static final FieldExpression<String> auditStatus = bcFreight.fieldOf("auditStatus", String.class);
    public static final FieldExpression<String> actualSettleCustName = bcFreight.fieldOf("actualSettleCustName", String.class);
    public static final FieldExpression<String> estimatedInvoiceTypeName = bcFreight.fieldOf("estimatedInvoiceTypeName", String.class);
    public static final FieldExpression<String> isReplace = bcFreight.fieldOf("isReplace", String.class);
    public static final FieldExpression<String> businessName = bcFreight.fieldOf("businessName", String.class);
    public static final FieldExpression<BigDecimal> exchangeRate = bcFreight.fieldOf("exchangeRate", BigDecimal.class);
    public static final FieldExpression<BigDecimal> estimatedTaxes = bcFreight.fieldOf("estimatedTaxes", BigDecimal.class);
    public static final FieldExpression<String> agreedPaymentDate = bcFreight.fieldOf("agreedPaymentDate", String.class);
    public static final FieldExpression<BigDecimal> actualBaseCurrencyValue = bcFreight.fieldOf("actualBaseCurrencyValue", BigDecimal.class);
    public static final FieldExpression<BigDecimal> allocablePrimeCurrencyValue = bcFreight.fieldOf("allocablePrimeCurrencyValue", BigDecimal.class);
    public static final FieldExpression<String> invoiceNo = bcFreight.fieldOf("invoiceNo", String.class);
    public static final FieldExpression<String> attachmentFlag = bcFreight.fieldOf("attachmentFlag", String.class);
    public static final FieldExpression<String> frtBizTypeName = bcFreight.fieldOf("frtBizTypeName", String.class);
    public static final FieldExpression<String> compNoBatch = bcFreight.fieldOf("compNoBatch", String.class);
    public static final FieldExpression<String> bizSystemOrderId = bcFreight.fieldOf("bizSystemOrderId", String.class);
    public static final FieldExpression<String> chequeNo = bcFreight.fieldOf("chequeNo", String.class);
    public static final FieldExpression<String> isDataCleaned = bcFreight.fieldOf("isDataCleaned", String.class);
    public static final FieldExpression<String> cntNo = bcFreight.fieldOf("cntNo", String.class);
    public static final FieldExpression<Long> bcChequeId = bcFreight.fieldOf("bcChequeId", Long.class);
    public static final FieldExpression<String> isCommitGlVoucher = bcFreight.fieldOf("isCommitGlVoucher", String.class);
    public static final FieldExpression<String> glVoucherDate = bcFreight.fieldOf("glVoucherDate", String.class);
    public static final FieldExpression<String> confirmNo = bcFreight.fieldOf("confirmNo", String.class);
    public static final FieldExpression<Long> bcFreightId = bcFreight.fieldOf("bcFreightId", Long.class);
    public static final FieldExpression<Long> glVoucherId = bcFreight.fieldOf("glVoucherId", Long.class);
    public static final FieldExpression<String> actualInvoiceType = bcFreight.fieldOf("actualInvoiceType", String.class);
    public static final FieldExpression<BigDecimal> glRateBase = bcFreight.fieldOf("glRateBase", BigDecimal.class);
    public static final FieldExpression<String> cntId = bcFreight.fieldOf("cntId", String.class);
    public static final FieldExpression<String> freightBeginDate = bcFreight.fieldOf("freightBeginDate", String.class);
    public static final FieldExpression<String> diffTaxesArpFiscalPeriod = bcFreight.fieldOf("diffTaxesArpFiscalPeriod", String.class);
    public static final FieldExpression<String> freightSource = bcFreight.fieldOf("freightSource", String.class);
    public static final FieldExpression<String> freightType = bcFreight.fieldOf("freightType", String.class);
    public static final FieldExpression<String> auditPersonName = bcFreight.fieldOf("auditPersonName", String.class);
    public static final FieldExpression<String> isCharged = bcFreight.fieldOf("isCharged", String.class);
    public static final FieldExpression<String> freightNameCn = bcFreight.fieldOf("freightNameCn", String.class);
    public static final FieldExpression<String> isEdiEpayOut = bcFreight.fieldOf("isEdiEpayOut", String.class);
    public static final FieldExpression<BigDecimal> actualTaxes = bcFreight.fieldOf("actualTaxes", BigDecimal.class);
    public static final FieldExpression<String> isInvoice = bcFreight.fieldOf("isInvoice", String.class);
    public static final FieldExpression<BigDecimal> allocatedBaseCurrencyValue = bcFreight.fieldOf("allocatedBaseCurrencyValue", BigDecimal.class);
    public static final FieldExpression<String> chargedDate = bcFreight.fieldOf("chargedDate", String.class);
    public static final FieldExpression<BigDecimal> agreementRate = bcFreight.fieldOf("agreementRate", BigDecimal.class);
    public static final FieldExpression<Long> bcLedgerCompId = bcFreight.fieldOf("bcLedgerCompId", Long.class);
    public static final FieldExpression<String> baseCurrencyCode = bcFreight.fieldOf("baseCurrencyCode", String.class);
    public static final FieldExpression<String> hblNo = bcFreight.fieldOf("hblNo", String.class);
    public static final FieldExpression<BigDecimal> actualTaxRate = bcFreight.fieldOf("actualTaxRate", BigDecimal.class);
    public static final FieldExpression<BigDecimal> estimatedTaxRate = bcFreight.fieldOf("estimatedTaxRate", BigDecimal.class);
    public static final FieldExpression<Long> diffTaxesVoucherId = bcFreight.fieldOf("diffTaxesVoucherId", Long.class);
    public static final FieldExpression<String> invoiceStatus = bcFreight.fieldOf("invoiceStatus", String.class);
    public static final FieldExpression<String> businessType = bcFreight.fieldOf("businessType", String.class);
    public static final FieldExpression<BigDecimal> allocatedPrimeCurrencyValue = bcFreight.fieldOf("allocatedPrimeCurrencyValue", BigDecimal.class);


    public QBcFreight() {
        super("BcFreight", BcFreight.class);
    }

    QBcFreight(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "BcFreight", BcFreight.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return bcFreightId;
    }
}
