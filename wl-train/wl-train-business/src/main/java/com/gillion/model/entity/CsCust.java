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


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cs_cust")
public class CsCust extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**物理主键
*/
    @Id
    @Column(name = "cs_cust_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long csCustId;

    /**(字段作废)*/
    @Column(name = "biz_cs_cust_id")
    private String bizCsCustId;

    /**(字段作废)*/
    @Column(name = "biz_system_type")
    private String bizSystemType;

    /**客商编码（需校验唯一性）*/
    @Column(name = "cust_code")
    private String custCode;

    /**中文简称
*/
    @Column(name = "cust_alias")
    private String custAlias;

    /**英文全称*/
    @Column(name = "cust_name_en")
    private String custNameEn;

    /**中文全称
*/
    @Column(name = "cust_name_cn")
    private String custNameCn;

    /**国家
*/
    @Column(name = "country_code")
    private String countryCode;

    /**国家名称（中文）*/
    @Column(name = "country_name_cn")
    private String countryNameCn;

    /**省份/州（编码）*/
    @Column(name = "state_code")
    private String stateCode;

    /**省份/州（名称）*/
    @Column(name = "state_name")
    private String stateName;

    /**城市
*/
    @Column(name = "city_code")
    private String cityCode;

    /**城市名*/
    @Column(name = "city_name")
    private String cityName;

    /**邮政编码
*/
    @Column(name = "zip")
    private String zip;

    /**中文地址
*/
    @Column(name = "addr_cn")
    private String addrCn;

    /**英文地址*/
    @Column(name = "addr_en")
    private String addrEn;

    /**有效标志*/
    @Column(name = "active")
    private String active;

    /**Y/N*/
    @Column(name = "is_deleted")
    private String isDeleted;

    /**一次性客商
Y:是
N:否*/
    @Column(name = "is_one_time_customers")
    private String isOneTimeCustomers;

    /**失效人*/
    @Column(name = "invalid_name")
    private String invalidName;

    /**失效时间*/
    @Column(name = "invalid_time")
    private String invalidTime;

    /**规模（单个公司、员工型、一次性、全国、区域）*/
    @Column(name = "scale")
    private String scale;

    /**所有制信息（国有企业、民营企业、三资企业、非营业组织机构、自然人）*/
    @Column(name = "ownership")
    private String ownership;

    /**销售渠道*/
    @Column(name = "sales_channels")
    private String salesChannels;

    /**是否结算对象
Y:是
N:否*/
    @Column(name = "is_settlement")
    private String isSettlement;

    /**结算对象*/
    @Column(name = "settle_cust_code")
    private String settleCustCode;

    /**结算对象名称*/
    @Column(name = "settle_cust_name")
    private String settleCustName;

    /**是否客户*/
    @Column(name = "cs_flag")
    private String csFlag;

    /**是否供应商*/
    @Column(name = "supplier_flag")
    private String supplierFlag;

    /**注册资本*/
    @Column(name = "registered_capitqal")
    private String registeredCapitqal;

    /**注册时间*/
    @Column(name = "registered_time")
    private String registeredTime;

    /**统一信用代码（对应主数据的企业注册码）*/
    @Column(name = "unified_credit_no")
    private String unifiedCreditNo;

    /**组织机构代码（对应主数据组织机构证代码）*/
    @Column(name = "client_no")
    private String clientNo;

    /**纳税人识别号（对应主数据税务登记证代码）*/
    @Column(name = "taxpayer_identificatio_no")
    private String taxpayerIdentificatioNo;

    /**工商注册号（对应主数据营业执照注册码）
administration for industry and commerce 工商局
*/
    @Column(name = "aic_registered_no")
    private String aicRegisteredNo;

    /**商检注册代码*/
    @Column(name = "iqb_registered_no")
    private String iqbRegisteredNo;

    /**海关注册代码*/
    @Column(name = "customes_registered_no")
    private String customesRegisteredNo;

    /**法人代表*/
    @Column(name = "fictitious_person")
    private String fictitiousPerson;

    @Column(name = "tel")
    private String tel;

    /**手机*/
    @Column(name = "mobile_no")
    private String mobileNo;

    /**邮箱*/
    @Column(name = "email")
    private String email;

    /**传真*/
    @Column(name = "fax")
    private String fax;

    /**客户状态： 1、临时客户 2、正式客户*/
    @Column(name = "cust_status")
    private String custStatus;

    /**客户主数据代码 CDH(common data HUB)*/
    @Column(name = "cdh_code")
    private String cdhCode;

    /**供应商主数据代码*/
    @Column(name = "v_cdh_code")
    private String vCdhCode;

    /**客户类型(（内部组织I、外部组织O）*/
    @Column(name = "cust_type")
    private String custType;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**创建人所属组织*/
    @Column(name = "create_office")
    private String createOffice;

    /**创建时间*/
    @Column(name = "create_time")
    private String createTime;

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

    @Column(name = "last_modify_time_zone")
    private String lastModifyTimeZone;

    /**版本号*/
    @Column(name = "record_version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long recordVersion;

    @Column(name = "principal_group_code")
    private String principalGroupCode;

    /**行业类别*/
    @Column(name = "lndustry_type")
    private String lndustryType;

    /**行业子类（取自数据字典）（暂不使用）*/
    @Column(name = "sub_lndustry_type")
    private String subLndustryType;

    /**默认付款条件（暂不使用）*/
    @Column(name = "default_payment_term")
    private String defaultPaymentTerm;

    /**默认收款条件（暂不使用）*/
    @Column(name = "default_collection_term")
    private String defaultCollectionTerm;

    /**备注*/
    @Column(name = "remarks")
    private String remarks;

    /**课税性质（一般纳税人、小规模纳税人）*/
    @Column(name = "tax_nature")
    private String taxNature;

    /**股份关系*/
    @Column(name = "stake_relation")
    private String stakeRelation;

    /**类别*/
    @Column(name = "type")
    private String type;

    /**股份比例*/
    @Column(name = "stake_proportion")
    private String stakeProportion;

    /**恢复日期*/
    @Column(name = "recovery_date")
    private String recoveryDate;

    /**失效日期*/
    @Column(name = "expiry_date")
    private String expiryDate;

    /**开票限额*/
    @Column(name = "invoice_maximum")
    private String invoiceMaximum;

    /**CDH限额*/
    @Column(name = "cdh_maximum")
    private String cdhMaximum;

    /**注册时间*/
    @Column(name = "registered_date")
    private String registeredDate;

    /**导入备注*/
    @Column(name = "import_remark")
    private String importRemark;

    /**内部组织代码*/
    @Column(name = "cust_office_code")
    private String custOfficeCode;

    /**助记码（记录船货代助记码）*/
    @Column(name = "memory_code")
    private String memoryCode;

    /**内部组织名称*/
    @Column(name = "cust_office_name")
    private String custOfficeName;

    /**MDG客商编码*/
    @Column(name = "mdg_cust_code")
    private String mdgCustCode;

}