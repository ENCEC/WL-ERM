package com.share.auth.center.model.entity;

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
@Table(name = "uem_active")
public class UemActive extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "uem_active_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemActiveId;

    /**发送内容*/
    @Column(name = "active_content")
    private String activeContent;

    /**发送时间*/
    @Column(name = "active_time")
    private Date activeTime;

    /**激活类型（0注册，1找回密码，2账号安全，3新增用户）*/
    @Column(name = "active_type")
    private String activeType;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**失效时间*/
    @Column(name = "failure_time")
    private Date failureTime;

    /**是否有效(0失效，1有效)*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**修改人id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**接收方账号（手机号或者邮箱号）*/
    @Column(name = "receiver_account")
    private String receiverAccount;

    /**接收方类型（0手机号，1邮箱号）*/
    @Column(name = "receiver_type")
    private String receiverType;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

}