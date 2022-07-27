package com.share.auth.model.vo;


import lombok.Data;


import java.io.Serializable;
import java.util.Date;

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
    private Long technicalTitleId;

    /**创建者*/

    private String createBy;

    /**创建时间*/
    private Date createTime;

    /**岗位ID*/
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
    private Date updateTime;

    /**岗位名称*/
    private String postName;

   /**分页页数*/
    private Integer currentPage;

   /**分页条数*/
    private Integer pageSize;
}
