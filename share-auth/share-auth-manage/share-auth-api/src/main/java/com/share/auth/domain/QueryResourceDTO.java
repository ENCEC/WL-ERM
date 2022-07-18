package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxf
 * @date 2020-10-26 16:49
 */
@Data
public class QueryResourceDTO implements Serializable{
    private static final long serialVersionUID = 1;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysResourceId;

    /**菜单图标*/
    private String resourceLogo;

    /**菜单标题*/
    private String resourceTitle;

    /**资源类型*/
    private Integer resourceType;

    /**资源地址*/
    private String resourceUrl;


    /**菜单序号*/
    private Integer resourceSort;

    /** 父级菜单id*/
    private Long resourcePid;

    private String component;

    private String componentName;

    private Boolean haveChildrenResource;

    /** 下级菜单集合*/
    private List<QueryResourceDTO> childrenResourceList;

    /**资源备注*/
    private String resourceRemark;

    private Long sysApplicationId;
}
