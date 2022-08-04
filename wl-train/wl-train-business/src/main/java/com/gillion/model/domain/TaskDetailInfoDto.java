package com.gillion.model.domain;

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
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/7/29
 */
@Data
@ApiModel("任务子表DTO")
public class TaskDetailInfoDto implements Serializable {

    /**任务子表ID*/
    @ApiModelProperty("任务子表ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskDetailId;

    /**执行周期*/
    @ApiModelProperty("执行周期")
    private Integer actionPeriod;

    /**执行顺序*/
    @ApiModelProperty("执行顺序")
    private Integer actionSerialNum;

    /**执行时间*/
    @ApiModelProperty("执行时间")
    private Date actionTime;

    /**申请日期*/
    @ApiModelProperty("申请日期")
    private Date applyDate;

    /**审批日期*/
    @ApiModelProperty("审批日期")
    private Date approvalDate;

    /**审批人ID*/
    @ApiModelProperty("审批人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long approver;

    /**实际完成日期*/
    @ApiModelProperty("实际完成日期")
    private Date endDate;

    /**执行人ID*/
    @ApiModelProperty("执行人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long executor;

    /**面谈评语*/
    @ApiModelProperty("面谈评语")
    private String faceRemark;

    /**面谈时间*/
    @ApiModelProperty("面谈时间")
    private Date faceTime;

    /**责任人ID*/
    @ApiModelProperty("责任人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long leader;

    /**转正评语*/
    @ApiModelProperty("转正评语")
    private String offerRemark;

    /**转正类型*/
    @ApiModelProperty("转正类型")
    private String offerType;

    /**统筹人ID*/
    @ApiModelProperty("统筹人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long ordinator;

    /**计划完成日期*/
    @ApiModelProperty("计划完成日期")
    private Date planEndDate;

    /**计划开始日期*/
    @ApiModelProperty("计划开始日期")
    private Date planStartDate;

    /**完成进度*/
    @ApiModelProperty("完成进度")
    private String progress;

    /**结果评估*/
    @ApiModelProperty("结果评估")
    private String resultAccess;

    /**规范条目ID*/
    @ApiModelProperty("规范条目ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardEntryId;

    @ApiModelProperty("规范条目名称")
    private String standardEntryName;

    @ApiModelProperty("规范细则ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardDetailId;

    @ApiModelProperty("规范细则名称")
    private String standardDetailName;

    /**实际开始日期*/
    @ApiModelProperty("实际开始日期")
    private Date startDate;

    /**完成状态（0：待完成 1：执行中 2：已完成）*/
    @ApiModelProperty("完成状态（0：待完成 1：执行中 2：已完成）")
    private Integer status;

    /**任务ID*/
    @ApiModelProperty("任务ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskInfoId;

    /**任务名称*/
    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("页码")
    private Integer pageNo;
}
