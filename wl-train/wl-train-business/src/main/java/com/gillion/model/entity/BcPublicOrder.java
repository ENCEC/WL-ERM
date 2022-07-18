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
@Table(name = "bc_public_order")
public class BcPublicOrder extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**物理主键*/
    @Id
    @Column(name = "bc_public_order_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long bcPublicOrderId;

    /**总订单ID*/
    @Column(name = "public_go_order_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long publicGoOrderId;

    /**项目ID*/
    @Column(name = "public_project_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long publicProjectId;

    /**业务系统*/
    @Column(name = "public_biz_system_type")
    private String publicBizSystemType;

    /**业务类型（数据字典）*/
    @Column(name = "public_source_type")
    private String publicSourceType;

    /**公共组织机构代码*/
    @Column(name = "public_org_id")
    private String publicOrgId;

    /**业务类型  BMS要求*/
    @Column(name = "public_business_type")
    private String publicBusinessType;

    /**业务子类*/
    @Column(name = "public_sub_business_type")
    private String publicSubBusinessType;

    /**业务单ID 例如存入的 MF_ORDER_ID*/
    @Column(name = "public_business_order_id")
    private String publicBusinessOrderId;

    /**公共  业务起始日期*/
    @Column(name = "public_business_date")
    private String publicBusinessDate;

    /**公共  业务完成日期 BMS要求的公货物交付时间  */
    @Column(name = "public_business_finish_date")
    private String publicBusinessFinishDate;

    /**业务开始日期（字段作废）*/
    @Column(name = "public_business_beg_date")
    private String publicBusinessBegDate;

    /**业务结束日期（字段作废）*/
    @Column(name = "public_business_end_date")
    private String publicBusinessEndDate;

    /**公共  客户业务编号*/
    @Column(name = "public_cust_business_no")
    private String publicCustBusinessNo;

    /**公共  提单号 BMS要求*/
    @Column(name = "public_mbl_no")
    private String publicMblNo;

    /**公共  货代提单号*/
    @Column(name = "public_hbl_no")
    private String publicHblNo;

    /**公共  外运业务号 BMS 要求*/
    @Column(name = "public_job_no")
    private String publicJobNo;

    /**公共  船名代码*/
    @Column(name = "public_vessel_code")
    private String publicVesselCode;

    /**公共  船舶名称(英文)*/
    @Column(name = "public_vessel_name")
    private String publicVesselName;

    /**公共  航次/航班*/
    @Column(name = "public_voyage")
    private String publicVoyage;

    /**结算公司*/
    @Column(name = "public_settle_office")
    private String publicSettleOffice;

    /**结算公司名称*/
    @Column(name = "public_settle_office_name")
    private String publicSettleOfficeName;

    /**公共  揽货方式 BMS要求*/
    @Column(name = "public_canvassion_mode")
    private String publicCanvassionMode;

    /**揽货部门*/
    @Column(name = "public_canvassion_department")
    private String publicCanvassionDepartment;

    /**公共  装货港代码*/
    @Column(name = "public_pol_code")
    private String publicPolCode;

    /**公共  装货港*/
    @Column(name = "public_pol")
    private String publicPol;

    /**公共  卸货港代码*/
    @Column(name = "public_pod_code")
    private String publicPodCode;

    /**公共  卸货港*/
    @Column(name = "public_pod")
    private String publicPod;

    /**公共  目的地代码*/
    @Column(name = "public_port_of_dest_code")
    private String publicPortOfDestCode;

    /**公共  目的地*/
    @Column(name = "public_port_of_destination")
    private String publicPortOfDestination;

    /**委托人代码*/
    @Column(name = "public_consignor_code")
    private String publicConsignorCode;

    /**公共  委托人*/
    @Column(name = "public_consignor_name")
    private String publicConsignorName;

    /**公共  收货人ID*/
    @Column(name = "public_consignee_code")
    private String publicConsigneeCode;

    /**公共  收货人*/
    @Column(name = "public_consignee_name")
    private String publicConsigneeName;

    /**公共  发货人ID*/
    @Column(name = "public_shipper_code")
    private String publicShipperCode;

    /**公共  发货人*/
    @Column(name = "public_shipper_name")
    private String publicShipperName;

    /**通知人代码*/
    @Column(name = "public_notify_code")
    private String publicNotifyCode;

    /**公共  通知人*/
    @Column(name = "public_notify_name")
    private String publicNotifyName;

    /**公共  海外代理代码   BMS要求*/
    @Column(name = "public_dest_agent_code")
    private String publicDestAgentCode;

    /**公共  海外代理名称*/
    @Column(name = "public_dest_agent_name")
    private String publicDestAgentName;

    /**公共  承运公司ID（海运：船公司；空运：航空公司）
支线船公司、干线船公司   BMS要求*/
    @Column(name = "public_carrier_code")
    private String publicCarrierCode;

    /**公共  承运公司*/
    @Column(name = "public_carrier_name")
    private String publicCarrierName;

    /**订舱代理代码  BMS要求*/
    @Column(name = "public_booking_agency_code")
    private String publicBookingAgencyCode;

    /**订舱代理名称*/
    @Column(name = "public_booking_agency_name")
    private String publicBookingAgencyName;

    /**订舱协议号  BMS要求*/
    @Column(name = "public_booking_protocol_no")
    private String publicBookingProtocolNo;

    /**公共  开航日期/航期  BMS要求*/
    @Column(name = "public_etd")
    private String publicEtd;

    /**公共到港日*/
    @Column(name = "public_eta")
    private String publicEta;

    /**公共  业务员代码*/
    @Column(name = "public_sales_code")
    private String publicSalesCode;

    /**业务员名称*/
    @Column(name = "public_sales_name")
    private String publicSalesName;

    /**公共  客服代码*/
    @Column(name = "public_cs_code")
    private String publicCsCode;

    /**公共  客服*/
    @Column(name = "public_cs_name")
    private String publicCsName;

    /**公共  操作代码*/
    @Column(name = "public_op_code")
    private String publicOpCode;

    /**公共  操作*/
    @Column(name = "public_op_name")
    private String publicOpName;

    /**付款方式   BMS要求*/
    @Column(name = "public_payment_mode")
    private String publicPaymentMode;

    /**公共 运输条款  BMS要求*/
    @Column(name = "public_transport_term")
    private String publicTransportTerm;

    /**公共  包装件数*/
    @Column(name = "public_no_of_package")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long publicNoOfPackage;

    /**公共  包装类型*/
    @Column(name = "public_package_type")
    private String publicPackageType;

    /**公共  货物毛重*/
    @Column(name = "public_gross_weight")
    private BigDecimal publicGrossWeight;

    /**公共  重量单位*/
    @Column(name = "public_gross_weight_unit")
    private String publicGrossWeightUnit;

    /**计费重*/
    @Column(name = "public_charging_weight")
    private BigDecimal publicChargingWeight;

    /**计费重单位*/
    @Column(name = "public_charging_weight_unit")
    private String publicChargingWeightUnit;

    /**公共  货物体积*/
    @Column(name = "public_cube")
    private BigDecimal publicCube;

    /**公共  体积单位*/
    @Column(name = "public_cube_unit")
    private String publicCubeUnit;

    /**公共  货物名称(英文)*/
    @Column(name = "public_cargo_description_en")
    private String publicCargoDescriptionEn;

    /**箱型箱量汇总格式样例：1X20GP+2X40HQ*/
    @Column(name = "public_ctn_num")
    private String publicCtnNum;

    /**根据柜号拼接起来，用分号;分割*/
    @Column(name = "public_ctn_no")
    private String publicCtnNo;

    /**订单下集装箱表中重箱TEU量汇总*/
    @Column(name = "public_ctn_teu")
    private String publicCtnTeu;

    /**订单备注*/
    @Column(name = "public_order_remark")
    private String publicOrderRemark;

    /**公共  是否HBL*/
    @Column(name = "public_is_hbl")
    private String publicIsHbl;

    /**公共 是否报关*/
    @Column(name = "public_is_customs_clearance")
    private String publicIsCustomsClearance;

    /**公共 是否报检*/
    @Column(name = "public_is_inspection")
    private String publicIsInspection;

    /**PUBLIC_IS_TRUCK*/
    @Column(name = "public_is_truck")
    private String publicIsTruck;

    /**公共 是否仓储*/
    @Column(name = "public_is_warehouse")
    private String publicIsWarehouse;

    /**公共 是否保险*/
    @Column(name = "public_is_insurance")
    private String publicIsInsurance;

    /**是否报批 Y:是 N：否  默认为N*/
    @Column(name = "public_is_approval")
    private String publicIsApproval;

    /**上报人*/
    @Column(name = "public_approval_person_code")
    private String publicApprovalPersonCode;

    /**上报人*/
    @Column(name = "public_approval_person_name")
    private String publicApprovalPersonName;

    /**上报时间*/
    @Column(name = "public_approval_date")
    private String publicApprovalDate;

    /**是否独立结算订单（N：否（普通订单）；Y：是）*/
    @Column(name = "is_independent")
    private String isIndependent;

    /**是否NVOCC  BMS要求
通过订单是否出HBL判断
Y：是  N ：否  默认为N*/
    @Column(name = "is_nvocc")
    private String isNvocc;

    /**业务流向：I：进口；O：出口；N：国内（国内指内贸）
BMS要求
通过业务类型判断*/
    @Column(name = "flow_direction")
    private String flowDirection;

    /**海运航线代码*/
    @Column(name = "mf_line_code")
    private String mfLineCode;

    @Column(name = "mf_line")
    private String mfLine;

    /** 报批备注*/
    @Column(name = "public_approval_remark")
    private String publicApprovalRemark;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**创建人所属组织*/
    @Column(name = "create_office")
    private String createOffice;

    /**创建时间*/
    @Column(name = "create_time")
    private String createTime;

    /**创建人时区*/
    @Column(name = "create_time_zone")
    private String createTimeZone;

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

    /**公共  货物名称(中文)*/
    @Column(name = "public_cargo_description_cn")
    private String publicCargoDescriptionCn;

    /**公共  包装类型名称*/
    @Column(name = "public_package_type_name")
    private String publicPackageTypeName;

    /**货主*/
    @Column(name = "bpp_code")
    private String bppCode;

    /**货主名称*/
    @Column(name = "bpp_name")
    private String bppName;

    /**小单号*/
    @Column(name = "small_single_no")
    private String smallSingleNo;

    @Column(name = "parent_order_id")
    private String parentOrderId;

    @Column(name = "parent_order_no")
    private String parentOrderNo;

    @Column(name = "parent_sub_order_id")
    private String parentSubOrderId;

    @Column(name = "parent_sub_order_no")
    private String parentSubOrderNo;

    /**内配标识  Y是 N不是  默认N*/
    @Column(name = "is_internal_frt")
    private String isInternalFrt;

    /**业务类型名称*/
    @Column(name = "public_business_type_name")
    private String publicBusinessTypeName;

    /**操作类型(业务子类)*/
    @Column(name = "public_operate_type")
    private String publicOperateType;

    /**操作类型名称(业务子类)*/
    @Column(name = "public_operate_type_name")
    private String publicOperateTypeName;

    /** 审核状态，Y已审核，N未审核；默认N；*/
    @Column(name = "audit_status")
    private String auditStatus;

    /** 审核时间*/
    @Column(name = "audit_date")
    private String auditDate;

    /** 审核人代码*/
    @Column(name = "audit_person_code")
    private String auditPersonCode;

    /** 审核人名称*/
    @Column(name = "audit_person_name")
    private String auditPersonName;

    /**上报会计区间*/
    @Column(name = "public_approval_fiscal_period")
    private String publicApprovalFiscalPeriod;

    /**揽货部门CODE*/
    @Column(name = "public_canvassion_dept_code")
    private String publicCanvassionDeptCode;

    /**预计开票日*/
    @Column(name = "public_estimated_issue_date")
    private String publicEstimatedIssueDate;

    /**预计对账日*/
    @Column(name = "public_estimated_comp_date")
    private String publicEstimatedCompDate;

    /**中集统一订单号*/
    @Column(name = "cimc_no")
    private String cimcNo;

    /**中集统一订单ID*/
    @Column(name = "cimc_id")
    private String cimcId;

    /**结算备注*/
    @Column(name = "fee_remark")
    private String feeRemark;

    /**收款利息*/
    @Column(name = "receive_interest")
    private BigDecimal receiveInterest;

    /**付款利息(负数显示)*/
    @Column(name = "payment_interest")
    private BigDecimal paymentInterest;

    /**总利息 （收-付）*/
    @Column(name = "gross_interest")
    private BigDecimal grossInterest;

    /**委托联系人*/
    @Column(name = "public_contact_name")
    private String publicContactName;

    /**订舱部门代码*/
    @Column(name = "public_cooperator_office_code")
    private String publicCooperatorOfficeCode;

    /**订舱部门*/
    @Column(name = "public_cooperator_office_name")
    private String publicCooperatorOfficeName;

    /**代理部门代码*/
    @Column(name = "public_oversea_office_code")
    private String publicOverseaOfficeCode;

    /**代理部门*/
    @Column(name = "public_oversea_office_name")
    private String publicOverseaOfficeName;

    /**外托货代代码*/
    @Column(name = "public_fw_partner_code")
    private String publicFwPartnerCode;

    /**外托货代*/
    @Column(name = "public_fw_partner_name")
    private String publicFwPartnerName;

    /**海关编号*/
    @Column(name = "public_custom_no")
    private String publicCustomNo;

    /**航班号*/
    @Column(name = "flight_no")
    private String flightNo;

    /**运费条款*/
    @Column(name = "pay_type_code")
    private String payTypeCode;

    /**装船作业区*/
    @Column(name = "load_area")
    private String loadArea;

    /**卸船作业区*/
    @Column(name = "disc_area")
    private String discArea;

    /**靠泊时间*/
    @Column(name = "berthing_time")
    private String berthingTime;

    /**实际开航日*/
    @Column(name = "public_atd")
    private String publicAtd;

    /**实际到港日*/
    @Column(name = "public_ata")
    private String publicAta;

    /**财务锁状态YN*/
    @Column(name = "ac_lock_status")
    private String acLockStatus;

    /**财务锁时间*/
    @Column(name = "ac_lock_date")
    private String acLockDate;

    /**财务锁操作人代码*/
    @Column(name = "ac_lock_person_code")
    private String acLockPersonCode;

    /**财务锁操作人*/
    @Column(name = "ac_lock_person_name")
    private String acLockPersonName;

    /**凭证锁状态YN*/
    @Column(name = "voucher_lock_status")
    private String voucherLockStatus;

    /**凭证锁时间*/
    @Column(name = "voucher_lock_date")
    private String voucherLockDate;

    /**凭证锁操作人代码*/
    @Column(name = "voucher_lock_person_code")
    private String voucherLockPersonCode;

    /**凭证锁操作人*/
    @Column(name = "voucher_lock_person_name")
    private String voucherLockPersonName;

    /**运费条款*/
    @Column(name = "pay_term")
    private String payTerm;

    /**公共  船舶名称(中文)*/
    @Column(name = "public_vessel_name_cn")
    private String publicVesselNameCn;

}