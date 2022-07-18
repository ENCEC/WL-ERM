package com.gillion.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gillion.ec.core.annotations.Generator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import com.gillion.ds.entity.base.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "bc_freight")
public class BcFreight extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**业务费用表主键ID*/
    @Id
    @Column(name = "bc_freight_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long bcFreightId;

    /**收付标志；R收，P付*/
    @Column(name = "rp_flag")
    private String rpFlag;

    /**费用分摊源ID*/
    @Column(name = "source_freight_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sourceFreightId;

    /**结算对象代码*/
    @Column(name = "settle_cust_code")
    private String settleCustCode;

    /**结算对象简称*/
    @Column(name = "settle_cust_name")
    private String settleCustName;

    /**--（字段作废）--*/
    @Column(name = "settle_cust_dept_code")
    private String settleCustDeptCode;

    /**--（字段作废）--*/
    @Column(name = "settle_cust_dept_name")
    private String settleCustDeptName;

    /**产值部门*/
    @Column(name = "settle_office_dept_code")
    private String settleOfficeDeptCode;

    /**产值部门名称*/
    @Column(name = "settle_office_dept_name")
    private String settleOfficeDeptName;

    /**提单号*/
    @Column(name = "mbl_no")
    private String mblNo;

    /**仓库编码*/
    @Column(name = "wh_code")
    private String whCode;

    /**仓库名称*/
    @Column(name = "wh_name")
    private String whName;

    /**车牌号*/
    @Column(name = "vehicle_brand")
    private String vehicleBrand;

    /**付款条款 ：PP预付 CC到付*/
    @Column(name = "pay_term")
    private String payTerm;

    /**财务费用类型（应收应付、代收代付）*/
    @Column(name = "ledger_type_code")
    private String ledgerTypeCode;

    /**代收代付互相关联用，收款存对应的付款费用ID，付款存对应的收款费用ID*/
    @Column(name = "replace_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long replaceId;

    /**是否现场结费（N：否；Y：是）默认N*/
    @Column(name = "is_spot")
    private String isSpot;

    /**数量*/
    @Column(name = "quantity")
    private BigDecimal quantity;

    /**单位*/
    @Column(name = "unit")
    private String unit;

    /**单价*/
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**币别*/
    @Column(name = "settle_currency_code")
    private String settleCurrencyCode;

    /**金额*/
    @Column(name = "settle_amount")
    private BigDecimal settleAmount;

    /**结算公司*/
    @Column(name = "settle_office")
    private String settleOffice;

    /**结算公司名称*/
    @Column(name = "settle_office_name")
    private String settleOfficeName;

    /**请款状态；N未请款，Y已请款；默认N；*/
    @Column(name = "request_status")
    private String requestStatus;

    /**请款单号*/
    @Column(name = "request_no")
    private String requestNo;

    /**批复状态；N未批复，Y已批复；默认N；*/
    @Column(name = "request_approve_status")
    private String requestApproveStatus;

    /**清单号（收据编号）*/
    @Column(name = "list_no")
    private String listNo;

    /**是否含税 （Y：含税，N：不含税） 默认为Y*/
    @Column(name = "is_tax")
    private String isTax;

    /**是否免税*/
    @Column(name = "is_tax_free")
    private String isTaxFree;

    /**任务代码*/
    @Column(name = "task_code")
    private String taskCode;

    /**任务名称（产装，院装）*/
    @Column(name = "task_name")
    private String taskName;

    /**任务单号*/
    @Column(name = "sub_order_no")
    private String subOrderNo;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**结算比例*/
    @Column(name = "settlement_ratio")
    private BigDecimal settlementRatio;

    /**冲销类型（CM（cross month）跨月红字发票，FC（freight change）费用更正 IS 发票拆分费用）*/
    @Column(name = "writeoff_type")
    private String writeoffType;

    /**冲销状态（W 冲销  A 增补 N默认 B 被冲销）*/
    @Column(name = "writeoff_status")
    private String writeoffStatus;

    /**冲销原费用ID*/
    @Column(name = "writeoff_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long writeoffId;

    /**航运租金计算期数*/
    @Column(name = "no_of_period")
    private Integer noOfPeriod;

    /**最后修改人*/
    @Column(name = "last_modifyor")
    private String lastModifyor;

    /**最后修改人所属组织*/
    @Column(name = "last_modify_office")
    private String lastModifyOffice;

    /**最后修改时间*/
    @Column(name = "last_modify_time")
    private String lastModifyTime;

    /**最后修改人时区*/
    @Column(name = "last_modify_time_zone")
    private String lastModifyTimeZone;

    /**云服务分组代码*/
    @Column(name = "principal_group_code")
    private String principalGroupCode;

    /**版本号*/
    @Column(name = "record_version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long recordVersion;

    /**进项税转出*/
    @Column(name = "transfer_taxes")
    private BigDecimal transferTaxes;

    /**箱来源ID*/
    @Column(name = "source_ctn_id")
    private String sourceCtnId;

    /**原提单+原箱号*/
    @Column(name = "source_order_ctn")
    private String sourceOrderCtn;

    /**退押金支票号*/
    @Column(name = "return_deposit_no")
    private String returnDepositNo;

    /**代垫类型CODE*/
    @Column(name = "replace_type_code")
    private String replaceTypeCode;

    /**代垫类型NAME*/
    @Column(name = "replace_type_name")
    private String replaceTypeName;

    /**认领状态(/P/Y)默认N*/
    @Column(name = "received_status")
    private String receivedStatus;

    /**计费公式*/
    @Column(name = "charge_expression")
    private String chargeExpression;

    @Column(name = "biz_replace_id")
    private String bizReplaceId;

    /**费用结束日期*/
    @Column(name = "freight_end_date")
    private String freightEndDate;

    /**可核销本位币金额*/
    @Column(name = "allocable_base_currency_value")
    private BigDecimal allocableBaseCurrencyValue;

    /**业务系统费用ID*/
    @Column(name = "biz_system_freight_id")
    private String bizSystemFreightId;

    /**箱型尺寸*/
    @Column(name = "cnt_type")
    private String cntType;

    /**请款单据ID*/
    @Column(name = "bc_payment_request_doc_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcPaymentRequestDocId;

    /**账单号*/
    @Column(name = "comp_no")
    private String compNo;

    /**核销汇率*/
    @Column(name = "allocation_exchange_rate")
    private BigDecimal allocationExchangeRate;

    /**业务对象代码*/
    @Column(name = "business_code")
    private String businessCode;

    /**实际开票本位币汇率*/
    @Column(name = "actual_rate_base")
    private BigDecimal actualRateBase;

    /**是否不开票 Y:是  N:否 默认N*/
    @Column(name = "is_not_invoice")
    private String isNotInvoice;

    /**确认单状态*/
    @Column(name = "confirm_status")
    private String confirmStatus;

    /**创建人时区*/
    @Column(name = "create_time_zone")
    private String createTimeZone;

    /**费用名称代码*/
    @Column(name = "freight_code")
    private String freightCode;

    /**发票流水号（字段作废） */
    @Column(name = "invoice_serial_no")
    private String invoiceSerialNo;

    /**本位币金额*/
    @Column(name = "base_currency_value")
    private BigDecimal baseCurrencyValue;

    /**实际付款对象编码*/
    @Column(name = "actual_settle_cust_code")
    private String actualSettleCustCode;

    @Column(name = "estimated_invoice_type")
    private String estimatedInvoiceType;

    /**发票代码*/
    @Column(name = "invoice_code")
    private String invoiceCode;

    /**创建人所属组织*/
    @Column(name = "create_office")
    private String createOffice;

    /**结算备注*/
    @Column(name = "fee_remark")
    private String feeRemark;

    /**税差金额*/
    @Column(name = "diff_taxes")
    private BigDecimal diffTaxes;

    /**不含税金额*/
    @Column(name = "estimated_amount")
    private BigDecimal estimatedAmount;

    /**对账状态（N：未对账；Y：已对账）默认N*/
    @Column(name = "comp_status")
    private String compStatus;

    /**入库单号*/
    @Column(name = "delivery_job_no")
    private String deliveryJobNo;

    /**申请支付币别*/
    @Column(name = "agreement_currency_code")
    private String agreementCurrencyCode;

    /**挂账会计期间*/
    @Column(name = "gl_arp_fiscal_period")
    private String glArpFiscalPeriod;

    /**公共订单主键*/
    @Column(name = "bc_public_order_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcPublicOrderId;

    /**核销主键*/
    @Column(name = "bc_allocation_event_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcAllocationEventId;

    /**内配标识  Y是 N不是  默认N*/
    @Column(name = "is_internal_frt")
    private String isInternalFrt;

    /**发票主键*/
    @Column(name = "bc_invoice_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcInvoiceId;

    /**独立结算订单ID*/
    @Column(name = "business_order_id")
    private String businessOrderId;

    /**请款单主键*/
    @Column(name = "bc_payment_request_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcPaymentRequestId;

    /**现场结费单主键*/
    @Column(name = "bc_charge_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcChargeId;

    /**计费业务类型编码*/
    @Column(name = "frt_biz_type_code")
    private String frtBizTypeCode;

    /**业务系统（数据来源OTW/报关系统）*/
    @Column(name = "biz_system_type")
    private String bizSystemType;

    /**核销状态；N未核销，P部分核销，Y已核销；默认N；*/
    @Column(name = "allocation_status")
    private String allocationStatus;

    /**审核时间*/
    @Column(name = "audit_date")
    private String auditDate;

    /**是否存在税差（N：否；Y：是）默认N*/
    @Column(name = "is_diff_taxes")
    private String isDiffTaxes;

    /**核销时间*/
    @Column(name = "allocation_date")
    private String allocationDate;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**可分配金额*/
    @Column(name = "assignable_amount")
    private BigDecimal assignableAmount;

    /**核销号*/
    @Column(name = "allocation_no")
    private String allocationNo;

    /**创建时间*/
    @Column(name = "create_time")
    private String createTime;

    /**协议号（BMS不关注）*/
    @Column(name = "agreement_no")
    private String agreementNo;

    /**审核人代码*/
    @Column(name = "audit_person_code")
    private String auditPersonCode;

    /** 审核状态，Y已审核，N未审核；默认N；*/
    @Column(name = "audit_status")
    private String auditStatus;

    /**实际付款对象*/
    @Column(name = "actual_settle_cust_name")
    private String actualSettleCustName;

    /**预计开票类型名称*/
    @Column(name = "estimated_invoice_type_name")
    private String estimatedInvoiceTypeName;

    /**撤票
Y 是 N 否  默认为N*/
    @Column(name = "is_replace")
    private String isReplace;

    /**业务对象名称*/
    @Column(name = "business_name")
    private String businessName;

    /**结算币到本位币的汇率*/
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    /**预估税金*/
    @Column(name = "estimated_taxes")
    private BigDecimal estimatedTaxes;

    /**预计付款日期*/
    @Column(name = "agreed_payment_date")
    private String agreedPaymentDate;

    /**实际开票本位币金额*/
    @Column(name = "actual_base_currency_value")
    private BigDecimal actualBaseCurrencyValue;

    /**可核销原币金额*/
    @Column(name = "allocable_prime_currency_value")
    private BigDecimal allocablePrimeCurrencyValue;

    /**发票号*/
    @Column(name = "invoice_no")
    private String invoiceNo;

    /**是否含附件*/
    @Column(name = "attachment_flag")
    private String attachmentFlag;

    /**计费业务类型*/
    @Column(name = "frt_biz_type_name")
    private String frtBizTypeName;

    @Column(name = "comp_no_batch")
    private String compNoBatch;

    /**子系统委托单的ID*/
    @Column(name = "biz_system_order_id")
    private String bizSystemOrderId;

    /**支票号*/
    @Column(name = "cheque_no")
    private String chequeNo;

    /**能否数据清理, Y:能 N:不能 */
    @Column(name = "is_data_cleaned")
    private String isDataCleaned;

    /**箱号 */
    @Column(name = "cnt_no")
    private String cntNo;

    /**支票ID*/
    @Column(name = "bc_cheque_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcChequeId;

    /** 是否生成挂账凭证。N表示否，Y表示是；默认N；*/
    @Column(name = "is_commit_gl_voucher")
    private String isCommitGlVoucher;

    /**挂账时间*/
    @Column(name = "gl_voucher_date")
    private String glVoucherDate;

    /**确认单号*/
    @Column(name = "confirm_no")
    private String confirmNo;

    /**挂账凭证ID*/
    @Column(name = "gl_voucher_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long glVoucherId;

    /**实际开票类型*/
    @Column(name = "actual_invoice_type")
    private String actualInvoiceType;

    /**本位币汇率*/
    @Column(name = "gl_rate_base")
    private BigDecimal glRateBase;

    /**箱ID*/
    @Column(name = "cnt_id")
    private String cntId;

    /**费用开始日期*/
    @Column(name = "freight_begin_date")
    private String freightBeginDate;

    /**税差挂账会计区间*/
    @Column(name = "diff_taxes_arp_fiscal_period")
    private String diffTaxesArpFiscalPeriod;

    /**费用来源：H手工输入，A协议价格自动计算，O外部导入，C费用更正，F财务费用，S拆分费用*/
    @Column(name = "freight_source")
    private String freightSource;

    /**--（字段作废）--*/
    @Column(name = "freight_type")
    private String freightType;

    /**审核人名称*/
    @Column(name = "audit_person_name")
    private String auditPersonName;

    /**到账状态；Y 已到账，N 未到账*/
    @Column(name = "is_charged")
    private String isCharged;

    /**费用名称*/
    @Column(name = "freight_name_cn")
    private String freightNameCn;

    /**是否已导出PEC001报文(东方支付) Y:是 N:否*/
    @Column(name = "is_edi_epay_out")
    private String isEdiEpayOut;

    /**实际开票税金*/
    @Column(name = "actual_taxes")
    private BigDecimal actualTaxes;

    /**是否允许开票（N：不开票，Y：开票）默认Y*/
    @Column(name = "is_invoice")
    private String isInvoice;

    /**已核销本位币金额*/
    @Column(name = "allocated_base_currency_value")
    private BigDecimal allocatedBaseCurrencyValue;

    /**到账日期*/
    @Column(name = "charged_date")
    private String chargedDate;

    /**协议汇率*/
    @Column(name = "agreement_rate")
    private BigDecimal agreementRate;

    /**账单主键*/
    @Column(name = "bc_ledger_comp_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long bcLedgerCompId;

    /**本位币币别*/
    @Column(name = "base_currency_code")
    private String baseCurrencyCode;

    /**分提单号*/
    @Column(name = "hbl_no")
    private String hblNo;

    /**实际开票税率*/
    @Column(name = "actual_tax_rate")
    private BigDecimal actualTaxRate;

    /**预估税率*/
    @Column(name = "estimated_tax_rate")
    private BigDecimal estimatedTaxRate;

    /**税差挂账凭证ID*/
    @Column(name = "diff_taxes_voucher_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long diffTaxesVoucherId;

    /**开票状态；N未开票，Y已开票；默认N；*/
    @Column(name = "invoice_status")
    private String invoiceStatus;

    /**业务类型(仓储出口、仓储进口、运输、报关)*/
    @Column(name = "business_type")
    private String businessType;

    /**已核销原币金额*/
    @Column(name = "allocated_prime_currency_value")
    private BigDecimal allocatedPrimeCurrencyValue;

}