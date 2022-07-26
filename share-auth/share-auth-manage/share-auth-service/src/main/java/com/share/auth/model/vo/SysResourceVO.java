package com.share.auth.model.vo;

/**
 * @version 1.0
 * @descriptionSysResource返回实体
 * @Author: zhuyp
 * @Date: 2021/12/7 17:23
 */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class SysResourceVO  implements Serializable{
    private static final long serialVersionUID=1;


    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysResourceId;

    private String component;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**启/禁用时间*/
    private Date invalidTime;

    /**是否禁用(0禁用,1启用)*/
    private Boolean isValid;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**版本号*/
    private Integer recordVersion;

    /**菜单图标*/
    private String resourceLogo;

    /**父级菜单ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long resourcePid;

    /**父级页面ID（资源类型为按钮时不为空）*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long pageResourcePid;

    /**菜单说明*/
    private String resourceRemark;

    /**菜单序号*/
    private Integer resourceSort;

    /**菜单标题*/
    private String resourceTitle;

    /**资源类型（1：模块/页面，2按钮，3接口）*/
    private Integer resourceType;

    /**资源地址*/
    private String resourceUrl;

    /**关联应用id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;
    /**
     * 组件名称
     */
    private String componentName;

}