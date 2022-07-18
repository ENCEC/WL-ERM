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
@Table(name = "gl_voucher")
public class GlVoucher extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**主键ID*/
    @Id
    @Column(name = "gl_voucher_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long glVoucherId;

    /**凭证备注信息*/
    @Column(name = "voucher_remarks")
    private String voucherRemarks;

    /**凭证类别*/
    @Column(name = "voucher_category")
    private String voucherCategory;

    /**在凭证类别下的具体凭证类型*/
    @Column(name = "voucher_type")
    private String voucherType;

    /**凭证号码*/
    @Column(name = "voucher_no")
    private String voucherNo;

    /** SAP凭证号*/
    @Column(name = "external_voucher_no")
    private String externalVoucherNo;

    /**序列号*/
    @Column(name = "seq_no")
    private String seqNo;

    /**附件数量*/
    @Column(name = "attachment")
    private BigDecimal attachment;

    /**传输来源*/
    @Column(name = "transaction_source")
    private String transactionSource;

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

    /**云服务分组代码*/
    @Column(name = "principal_group_code")
    private String principalGroupCode;

    /**最后修改人所属组织*/
    @Column(name = "last_modify_office")
    private String lastModifyOffice;

    /**最后修改时间*/
    @Column(name = "last_modify_time")
    private String lastModifyTime;

    /**最后修改人时区*/
    @Column(name = "last_modify_time_zone")
    private String lastModifyTimeZone;

    /**版本号*/
    @Column(name = "record_version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long recordVersion;

    /**业务系统的相关业务单证号码 */
    @Column(name = "transaction_no")
    private String transactionNo;

    /**凭证日期 */
    @Column(name = "voucher_date")
    private String voucherDate;

    /**凭证状态*/
    @Column(name = "voucher_state")
    private String voucherState;

    /**凭证导出状态*/
    @Column(name = "voucher_export_state")
    private String voucherExportState;

    /**凭证导出时间*/
    @Column(name = "voucher_export_time")
    private String voucherExportTime;

    /**凭证转出人*/
    @Column(name = "voucher_export_person")
    private String voucherExportPerson;

    /**凭证审核用户*/
    @Column(name = "validate_user")
    private String validateUser;

    /**公司*/
    @Column(name = "company_code")
    private String companyCode;

    /**凭证参考号码*/
    @Column(name = "reference_code")
    private String referenceCode;

    /**会计期间*/
    @Column(name = "fiscal_period")
    private String fiscalPeriod;

    /**凭证转出人*/
    @Column(name = "voucher_export_person_name")
    private String voucherExportPersonName;

    /**凭证审核用户*/
    @Column(name = "validate_user_name")
    private String validateUserName;

    /**利润中心*/
    @Column(name = "profit_center")
    private String profitCenter;

    /**部门代码*/
    @Column(name = "department_code")
    private String departmentCode;

    /**公司名称*/
    @Column(name = "company_name")
    private String companyName;

    /**是否税差凭证(N:否;Y:是)默认N*/
    @Column(name = "is_diff_taxes")
    private String isDiffTaxes;

    @Column(name = "coa_name")
    private String coaName;

}