package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@Data
public class SysTagDTO implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysTagId;

    /**标签名称*/
    private String tagName;

    /**标签描述*/
    private String tagDescription;

    /**是否禁用(0禁用,1启用)*/
    private Boolean status;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**创建时间*/
    private Date createTime;

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

    /**逻辑删除flag*/
    private Boolean isDeleted;

    /**
     * 分页条数
     **/
    @ApiModelProperty("分页条数")
    private Integer pageSize;
    /**
     * 当前页
     **/
    @ApiModelProperty("当前页")
    private Integer currentPage;

}