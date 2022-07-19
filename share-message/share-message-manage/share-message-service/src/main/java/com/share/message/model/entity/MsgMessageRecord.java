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
@Table(name = "msg_message_record")
public class MsgMessageRecord extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "msg_message_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long msgMessageId;

    /**发送方系统id*/
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

    /**是否删除（0-未删除，1-已删除），默认为0*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**是否成功（0-失败，1-成功）*/
    @Column(name = "is_success")
    private Boolean isSuccess;

    /**消息内容*/
    @Column(name = "message_content")
    private String messageContent;

    /**消息等级*/
    @Column(name = "message_level")
    private String messageLevel;

    /**消息标题*/
    @Column(name = "message_title")
    private String messageTitle;

    /**消息类型（1-平台通知）*/
    @Column(name = "message_type")
    private String messageType;

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

    /**失败原因*/
    @Column(name = "reason")
    private String reason;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**接收方系统id（接收方系统id多个时用逗号隔开）*/
    @Column(name = "target_system_id")
    private String targetSystemId;

}