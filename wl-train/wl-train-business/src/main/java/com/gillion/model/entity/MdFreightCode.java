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
@Table(name = "md_freight_code")
public class MdFreightCode extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**物理主键*/
    @Id
    @Column(name = "md_freight_code_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long mdFreightCodeId;

    /**业务类型ID*/
    @Column(name = "biz_system_id")
    private String bizSystemId;

    /**系统类型：数据字典
*/
    @Column(name = "biz_system_type")
    private String bizSystemType;

    /**局部费用编码*/
    @Column(name = "freight_code")
    private String freightCode;

    /**费用助记码*/
    @Column(name = "memory_code")
    private String memoryCode;

    /**局部费用英文名称*/
    @Column(name = "freight_name_en")
    private String freightNameEn;

    /**局部费用中文名称*/
    @Column(name = "freight_name_cn")
    private String freightNameCn;

    /**全局费目代码*/
    @Column(name = "freight_global_code")
    private String freightGlobalCode;

    /**全局费用名称*/
    @Column(name = "feight_global_name")
    private String feightGlobalName;

    /**费用显示简称*/
    @Column(name = "short_name")
    private String shortName;

    /**费用类别*/
    @Column(name = "freight_type")
    private String freightType;

    /**业务类型*/
    @Column(name = "business_type")
    private String businessType;

    /**开票归集名称*/
    @Column(name = "invoice_name")
    private String invoiceName;

    /**报表归集名称*/
    @Column(name = "report_name")
    private String reportName;

    /**默认币别*/
    @Column(name = "currency")
    private String currency;

    /**Y 有效 N 失效*/
    @Column(name = "active")
    private String active;

    /**Y/N*/
    @Column(name = "is_deleted")
    private String isDeleted;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**结算公司代码*/
    @Column(name = "settle_office")
    private String settleOffice;

    /**结算公司名称*/
    @Column(name = "settle_office_name")
    private String settleOfficeName;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**创建人组织*/
    @Column(name = "create_office")
    private String createOffice;

    /**创建时间*/
    @Column(name = "create_time")
    private String createTime;

    /**创建时区*/
    @Column(name = "create_time_zone")
    private String createTimeZone;

    /**最后修改人*/
    @Column(name = "last_modifyor")
    private String lastModifyor;

    /**最后修改人组织*/
    @Column(name = "last_modify_office")
    private String lastModifyOffice;

    /**最后修改时间*/
    @Column(name = "last_modify_time")
    private String lastModifyTime;

    /**最后修改时区*/
    @Column(name = "last_modify_time_zone")
    private String lastModifyTimeZone;

    @Column(name = "principal_group_code")
    private String principalGroupCode;

    /**版本号*/
    @Column(name = "record_version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long recordVersion;

    /**商品编码*/
    @Column(name = "invoice_freight_code")
    private String invoiceFreightCode;

}