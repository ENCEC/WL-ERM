package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/10/26 0026
 */
@Data
@ApiModel("UemUserRoleDto 用户角色关联表映射")
public class UemUserRoleDto implements Serializable {

    /**用户表ID*/
    @ApiModelProperty("用户ID")
    private String uemUserId;

    /**用户角色表ID*/
    @ApiModelProperty("用户角色表ID")
    private String uemUserRoleId;

    /**应用管理表ID*/
    @ApiModelProperty("系统应用ID")
    private String sysApplicationId;

    /**角色管理表ID*/
    @ApiModelProperty("角色ID")
    private String sysRoleId;

    /**是否禁用(0禁用,1启用)*/
    @ApiModelProperty("是否禁用(0禁用,1启用)")
    private Boolean isValid;

    /**启/禁用时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("启/禁用时间")
    private Date invalidTime;

    /**应用名称*/
    @ApiModelProperty("应用名称")
    private String applicationName;

    /**应用名称*/
    @ApiModelProperty("应用名称")
    private String roleName;
}
