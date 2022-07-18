package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/10/26 0026
 */
@Api("用户角色表映射")
@Data
public class UemUserRoleDto implements Serializable {
    /**用户表ID*/
    private String uemUserId;
    /**用户角色表ID*/
    private String uemUserRoleId;
    /**应用管理表ID*/
    private String sysApplicationId;
    /**角色管理表ID*/
    private String sysRoleId;
    /**是否禁用(0禁用,1启用)*/
    private Boolean isValid;
    /**启/禁用时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;
    /**应用名称*/
    private String applicationName;
    /**角色名称*/
    private String roleName;



}
