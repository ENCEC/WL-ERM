package com.share.message.model.entity;

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
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "msg_message_template")
public class MsgMessageTemplate extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "msg_message_template_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long msgMessageTemplateId;

    /**使用方业务系统ID，引用账号体系的ID*/
    @Column(name = "business_system_id")
    private String businessSystemId;

    /**消息正文模板内容*/
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

    /**消息模板编号*/
    @Column(name = "msg_template_code")
    private String msgTemplateCode;

    /**消息模板名称*/
    @Column(name = "msg_template_name")
    private String msgTemplateName;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

}