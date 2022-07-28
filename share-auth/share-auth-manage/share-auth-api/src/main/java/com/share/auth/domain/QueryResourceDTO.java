package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxf
 * @date 2020-10-26 16:49
 */
@Data
@ApiModel("QueryResourceDTO 树状系统资源列表")
public class QueryResourceDTO implements Serializable{
    private static final long serialVersionUID = 1;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @ApiModelProperty("id")
    private Long sysResourceId;

    /**菜单图标*/
    @ApiModelProperty("菜单图标")
    private String resourceLogo;

    /**菜单标题*/
    @ApiModelProperty("菜单标题")
    private String resourceTitle;

    /**资源类型*/
    @ApiModelProperty("资源类型")
    private Integer resourceType;

    /**资源地址*/
    @ApiModelProperty("资源地址")
    private String resourceUrl;

    /**菜单序号*/
    @ApiModelProperty("菜单序号")
    private Integer resourceSort;

    /** 父级菜单id*/
    @ApiModelProperty("父级菜单id")
    private Long resourcePid;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("组件名称")
    private String componentName;

    @ApiModelProperty("是否有子资源")
    private Boolean haveChildrenResource;

    /** 下级菜单集合*/
    @ApiModelProperty("下级菜单集合")
    private List<QueryResourceDTO> childrenResourceList;

    /**资源备注*/
    @ApiModelProperty("资源备注")
    private String resourceRemark;

    @ApiModelProperty("应用ID")
    private Long sysApplicationId;
}
