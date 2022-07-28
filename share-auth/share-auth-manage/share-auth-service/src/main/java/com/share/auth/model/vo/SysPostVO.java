package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tanjp
 * @Date 2022/7/26 9:23
 */

@Data
public class SysPostVO extends BaseModel implements Serializable {
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long postId;

    /**创建者*/
    private String createBy;

    /**创建时间*/
    private Date createTime;

    /**岗位编码*/
    private String postCode;

    /**岗位名称*/
    private String postName;

    /**显示顺序*/
    private Integer postSort;

    /**备注*/
    private String remark;

    /**状态（0正常 1停用）*/
    private String status;

    /**更新者*/
    private String updateBy;

    /**更新时间*/
    private Date updateTime;
}
