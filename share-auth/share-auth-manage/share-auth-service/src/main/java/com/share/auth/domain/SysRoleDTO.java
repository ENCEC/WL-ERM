package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenxf
 * @date 2020-11-16 14:48
 */
@Data
@ApiModel("系统角色")
public class SysRoleDTO implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("ID")
    private Long sysRoleId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 创建人id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("创建人id")
    private Long creatorId;

    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**
     * 启/禁用时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("启/禁用时间")
    private Date invalidTime;

    /**
     * 是否禁用
     */
    @ApiModelProperty("是否禁用")
    private Boolean isValid;

    /**
     * 修改人id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("修改人id")
    private Long modifierId;

    /**
     * 修改人名称
     */
    @ApiModelProperty("修改人名称")
    private String modifierName;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("修改时间")
    private Date modifyTime;

    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private Integer recordVersion;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String remark;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    private String resourceTitle;

    private String sysResourceId;

    private String sysRoleResourceId;

    /**
     * 优先级
     */
    @ApiModelProperty("优先级")
    private Integer priority;

    /**
     * 父级角色
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("父级角色")
    private Long rolePid;

    /**
     * 关联应用id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("关联应用id")
    private Long sysApplicationId;

    @ApiModelProperty("")
    private Boolean haveChildRole;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;


    /**
     * 分页条数
     **/
    @ApiModelProperty("分页条数")
    private int pageSize;
    /**
     * 当前页
     **/
    @ApiModelProperty("当前页")
    private int currentPage;
    /**
     * 是否默认角色(0否,1是)
     */
    @ApiModelProperty("是否默认角色(0否,1是)")
    private Boolean isDefault;

    @ApiModelProperty("")
    private List<SysRoleDTO> childrenSysRoleList;

    @ApiModelProperty("")
    //修改之前为long类型
    private List<String> sysResourceIdList;

    //多对多关联编辑时主键List
    private List<String> sysRoleResourceIdList;
}
