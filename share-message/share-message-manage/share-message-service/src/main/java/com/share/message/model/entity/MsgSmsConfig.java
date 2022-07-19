package com.share.message.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "msg_sms_config")
public class MsgSmsConfig extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "msg_sms_config_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long msgSmsConfigId;

    /**app_key*/
    @Column(name = "app_key")
    private String appKey;

    /**app_secret*/
    @Column(name = "app_secret")
    private String appSecret;

    /**使用方业务系统ID，引用账号体系的ID*/
    @Column(name = "business_system_id")
    private String businessSystemId;

    /**渠道名称*/
    @Column(name = "channel_name")
    private String channelName;

    /**创建公司ID*/
    @Column(name = "create_company_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long createCompanyId;

    /**创建公司*/
    @Column(name = "create_company_name")
    private String createCompanyName;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人*/
    @Column(name = "creator_name")
    private String creatorName;

    /**是否删除（0-未删除，1-已删除），默认为0*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**服务商账号*/
    @Column(name = "isp_account")
    private String ispAccount;

    /**服务商编号*/
    @Column(name = "isp_no")
    private String ispNo;

    /**服务商密码，MD5加密*/
    @Column(name = "isp_password")
    private String ispPassword;

    /**是否启用（0-禁用，1-启用），默认1*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**关键字*/
    @Column(name = "keyword")
    private String keyword;

    /**修改人ID*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改公司ID*/
    @Column(name = "modify_company_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifyCompanyId;

    /**修改公司*/
    @Column(name = "modify_company_name")
    private String modifyCompanyName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**优先级*/
    @Column(name = "priority")
    private Integer priority;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**短信接口类型，0-有服务商，1-无服务商*/
    @Column(name = "sms_service_type")
    private String smsServiceType;

    /**短信接口地址*/
    @Column(name = "sms_url")
    private String smsUrl;

}