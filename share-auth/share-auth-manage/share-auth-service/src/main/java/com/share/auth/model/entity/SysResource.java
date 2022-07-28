package com.share.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "sys_resource")
public class SysResource extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * id
     */
    @Id
    @Column(name = "sys_resource_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysResourceId;

    @Column(name = "component")
    private String component;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**
     * 创建人名称
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 启/禁用时间
     */
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**
     * 是否禁用(0禁用,1启用)
     */
    @Column(name = "is_valid")
    private Boolean isValid;

    /**
     * 修改人id
     */
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**
     * 修改人名称
     */
    @Column(name = "modifier_name")
    private String modifierName;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 版本号
     */
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**
     * 菜单图标
     */
    @Column(name = "resource_logo")
    private String resourceLogo;

    /**
     * 父级菜单ID
     */
    @Column(name = "resource_pid")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long resourcePid;

    /**
     * 菜单说明
     */
    @Column(name = "resource_remark")
    private String resourceRemark;

    /**
     * 菜单序号
     */
    @Column(name = "resource_sort")
    private Integer resourceSort;

    /**
     * 菜单标题
     */
    @Column(name = "resource_title")
    private String resourceTitle;

 /*   @Column(name = "parent_name")
    private String parentName;
*/

    /**
     * 资源类型（1：模块/页面，2按钮，3接口）
     */
    @Column(name = "resource_type")
    private Integer resourceType;

    /**
     * 资源地址
     */
    @Column(name = "resource_url")
    private String resourceUrl;

    /**
     * 关联应用id
     */
    @Column(name = "sys_application_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /**
     * 组件名称
     */
    @Column(name = "component_name")
    private String componentName;

    /**
     * 逻辑删除标识（0-未删除，1-已删除），默认0
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}