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
@Table(name = "msg_sms_template")
public class MsgSmsTemplate extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "msg_sms_template_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long msgSmsTemplateId;

    /**使用方业务系统ID，引用账号体系的ID*/
    @Column(name = "business_system_id")
    private String businessSystemId;

    /**短信正文模板内容*/
    @Column(name = "content")
    private String content;

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

    /**模板描述*/
    @Column(name = "description")
    private String description;

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

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**短信模板编号*/
    @Column(name = "sms_template_code")
    private String smsTemplateCode;

    /**短信模板名称*/
    @Column(name = "sms_template_name")
    private String smsTemplateName;

    /**短信类型（0-国内），默认0*/
    @Column(name = "sms_type")
    private String smsType;

}