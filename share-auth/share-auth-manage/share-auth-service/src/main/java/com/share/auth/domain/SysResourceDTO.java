package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @descriptionSysResource入参实体
 * @Author: zhuyp
 * @Date: 2021/12/7 17:25
 */
@Data
@ApiModel("系统资源")
public class SysResourceDTO implements Serializable {
    private static final long serialVersionUID=1;

    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("ID")
    private Long sysResourceId;

    @ApiModelProperty("组件路径")
    private String component;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("创建人id")
    private Long creatorId;

    /**创建人名称*/
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**启/禁用时间*/
    @ApiModelProperty("启/禁用时间")
    private Date invalidTime;

    /**是否禁用(0禁用,1启用)*/
    @ApiModelProperty("是否禁用(0禁用,1启用)")
    private Boolean isValid;

    /**修改人id*/
    @ApiModelProperty("修改人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @ApiModelProperty("修改人名称")
    private String modifierName;

    /**修改时间*/
    @ApiModelProperty("修改时间")
    private Date modifyTime;

    /**版本号*/
    @ApiModelProperty("版本号")
    private Integer recordVersion;

    /**菜单图标*/
    @ApiModelProperty("菜单图标")
    private String resourceLogo;

    /**父级菜单ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("父级菜单ID")
    private Long resourcePid;

    /**父级页面ID（资源类型为按钮时不为空）*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("父级页面ID（资源类型为按钮时不为空）")
    private Long pageResourcePid;

    /**菜单说明*/
    @ApiModelProperty("菜单说明")
    private String resourceRemark;

    /**菜单序号*/
    @ApiModelProperty("菜单序号")
    private Integer resourceSort;

    /**菜单标题*/
    @ApiModelProperty("菜单标题")
    private String resourceTitle;

    private String parentResourceTitle;

    /**资源类型（1：模块/页面，2按钮，3接口）*/
    @ApiModelProperty("资源类型（1：模块/页面，2按钮，3接口）")
    private Integer resourceType;

    /**资源地址*/
    @ApiModelProperty("资源地址")
    private String resourceUrl;

    /**关联应用id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("关联应用id")
    private Long sysApplicationId;
    /**分页条数**/
    @ApiModelProperty("分页条数")
    private int pageSize;
    /**当前页**/
    @ApiModelProperty("当前页")
    private int currentPage;
    /**
     * 组件名称
     */
    @ApiModelProperty("组件名称")
    private String componentName;
}
