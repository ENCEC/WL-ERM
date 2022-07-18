package com.share.auth.model.entity;

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
@Table(name = "sys_role")
public class SysRole extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "sys_role_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysRoleId;

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

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否默认角色(0否,1是)*/
    @Column(name = "is_default")
    private Boolean isDefault;

    /**是否禁用(0禁用,1启用)*/
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

    /**优先级*/
    @Column(name = "priority")
    private Integer priority;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**角色描述*/
    @Column(name = "remark")
    private String remark;

    /**角色编码*/
    @Column(name = "role_code")
    private String roleCode;

    /**角色名称*/
    @Column(name = "role_name")
    private String roleName;

    /**父级角色*/
    @Column(name = "role_pid")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long rolePid;

    /**关联应用id*/
    @Column(name = "sys_application_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /**最顶层角色id*/
    @Column(name = "top_role_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long topRoleId;

}