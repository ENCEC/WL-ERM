package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业权限VO
 * @author chenhy
 * @date 20210715
 */
@Data
public class UemCompanyRoleVO implements Serializable {

    private static final long serialVersionUID = 1;

    /**主键*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyRoleId;

    /**是否选择角色*/
    private Boolean isSelected;

    /**是否默认(0否，1是)*/
    private Boolean isDefault;

    /**应用系统id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /**应用名称*/
    private String applicationName;

    /**角色id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysRoleId;

    /**角色名称*/
    private String roleName;

    /**企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

}
