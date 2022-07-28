package com.share.auth.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysTechnicalTitleAndPostVO
 * @Description 岗位职称的封装类
 * @Author weiq
 * @Date 2022/7/25 15:41
 * @Version 1.0
 **/
@Data
public class SysTechnicalTitleAndPostVO implements Serializable {
    /**岗位职称ID*/

    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long technicalTitleId;

    /**创建者*/

    private String createBy;

    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**岗位ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long postId;

    /**备注*/
    private String remark;

    /**工龄*/
    private String seniority;

    /**状态（0正常 1停用）*/
    private String status;

    /**职称名称*/
    private String technicalName;

    /**更新者*/
    private String updateBy;

    /**更新时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**岗位名称*/
    private String postName;

   /**分页页数*/
    private Integer currentPage;

   /**分页条数*/
    private Integer pageSize;
}
