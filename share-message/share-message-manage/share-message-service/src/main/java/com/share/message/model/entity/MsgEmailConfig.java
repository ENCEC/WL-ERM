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
@Table(name = "msg_email_config")
public class MsgEmailConfig extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "msg_email_config_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long msgEmailConfigId;

    /**使用方业务系统ID，引用账号体系的ID*/
    @Column(name = "business_system_id")
    private String businessSystemId;

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

    /**邮箱地址*/
    @Column(name = "email_address")
    private String emailAddress;

    /**邮箱配置编码*/
    @Column(name = "email_config_code")
    private String emailConfigCode;

    /**邮箱配置名称*/
    @Column(name = "email_config_name")
    private String emailConfigName;

    /**邮箱密码，MD5加密*/
    @Column(name = "email_password")
    private String emailPassword;

    /**邮箱类型，需要建数据字典*/
    @Column(name = "email_type")
    private String emailType;

    /**邮箱用户名*/
    @Column(name = "email_user")
    private String emailUser;

    /**邮件服务器地址*/
    @Column(name = "host")
    private String host;

    /**是否删除（0-未删除，1-已删除），默认为0*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**是否启用（0-禁用，1-启用），默认1*/
    @Column(name = "is_valid")
    private Boolean isValid;

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

    /**邮件服务器端口*/
    @Column(name = "port")
    private Integer port;

    /**邮箱协议，如smtp*/
    @Column(name = "protocol")
    private String protocol;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

}