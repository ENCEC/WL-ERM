package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tanjp
 * @Date 2022/7/26 9:23
 */
@ApiModel("SysPostDTO 岗位信息")
@Data
public class SysPostDTO extends BaseModel implements Serializable {
    @ApiModelProperty("id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long postId;

    /**创建者*/
    @ApiModelProperty("创建者")
    @Column(name = "create_by")
    private String createBy;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**岗位编码*/
    @ApiModelProperty("岗位编码")
    private String postCode;

    /**岗位名称*/
    @ApiModelProperty("岗位名称")
    private String postName;

    /**显示顺序*/
    @ApiModelProperty("显示顺序")
    private Integer postSort;

    /**备注*/
    @ApiModelProperty("备注")
    private String remark;

    /**状态（0正常 1停用）*/
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    /**更新者*/
    @ApiModelProperty("更新者")
    private String updateBy;

    /**更新时间*/
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /** 分页页数 */
    @ApiModelProperty("分页页数")
    private Integer currentPage;

    /** 分页显示条数 */
    @ApiModelProperty("分页显示条数")
    private Integer pageSize;
}
