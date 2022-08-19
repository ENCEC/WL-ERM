package com.gillion.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/7/29
 */
@Data
@ApiModel("任务子表DTO")
public class TaskDetailInfoDto implements Serializable {

    /**
     * 任务子表ID
     */
    @ApiModelProperty("任务子表ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskDetailId;

    @ApiModelProperty("审批人id-来自user表")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /**
     * 主键id数组
     */
    @ApiModelProperty("主键数组")
    private List<String> uemUserIds;


    /**
     * 执行周期
     */
    @ApiModelProperty("执行周期")
    private Integer actionPeriod;

    /**
     * 执行顺序
     */
    @ApiModelProperty("执行顺序")
    private Integer actionSerialNum;

    /**
     * 执行时间
     */
    @ApiModelProperty("执行时间")
    private Integer actionTime;

    /**
     * 申请日期
     */
    @ApiModelProperty("申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;

    /**
     * 审批日期
     */
    @ApiModelProperty("审批日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date approvalDate;

    /**
     * 审批人ID
     */
    @ApiModelProperty("审批人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long approver;

    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String approverName;

    /**
     * 审核人ID
     */
    @ApiModelProperty("审核人id")
    private Long auditId;

    /**
     * 审核人姓名
     */
    @ApiModelProperty("审核人姓名")
    private String auditName;

    /**
     * 审核日期
     */
    @ApiModelProperty("审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditDate;
    /**
     * 审核结果
     */
    @ApiModelProperty("审核结果")
    private String auditResult;
    /**
     * 审核意见
     */
    @ApiModelProperty("审核意见")
    private String auditRemark;

    /**
     * 实际完成日期
     */
    @ApiModelProperty("实际完成日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 执行人ID
     */
    @ApiModelProperty("执行人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long executor;

    /**
     * 面谈人ID
     */
    @ApiModelProperty("面谈人ID")
    private Long interviewerId;

    private String interviewerName;

    /**
     * 面谈评语
     */
    @ApiModelProperty("面谈评语")
    private String faceRemark;

    /**
     * 面谈时间
     */
    @ApiModelProperty("面谈时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date faceTime;

    /**
     * 答辩成绩
     */
    @ApiModelProperty("答辩成绩")
    private String faceScore;

    /**
     * 面谈结果
     */
    @ApiModelProperty("面谈结果")
    private String faceResult;

    /**
     * 审批意见
     */
    @ApiModelProperty("审批意见")
    private String approvalRemark;

    /**
     * 责任人ID
     */
    @ApiModelProperty("责任人ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long leader;
    /**
     * 分配人ID
     */
    @ApiModelProperty("分配人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dispatchers;

    /**
     * 责任人姓名
     */
    @ApiModelProperty("责任人姓名")
    private String leaderName;

    /**转正评语*/
    /**
     * 分配人姓名
     */
    @ApiModelProperty("分配人id")
    private String dispatchersName;

    /**
     * 转正评语
     */
    @ApiModelProperty("转正评语")
    private String offerRemark;

    /**
     * 转正类型
     */
    @ApiModelProperty("转正类型")
    private String offerType;

    /**
     * 转正时间
     */
    @ApiModelProperty("转正时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date offerDate;
    /**
     * 统筹人ID
     */
    @ApiModelProperty("统筹人ID")
    private String ordinator;

    /**
     * 统筹人姓名
     */
    @ApiModelProperty("统筹人姓名")
    private String ordinatorName;

    /**
     * 计划完成日期
     */
    @ApiModelProperty("计划完成日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planEndDate;

    /**
     * 计划开始日期
     */
    @ApiModelProperty("计划开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planStartDate;

    /**
     * 完成进度
     */
    @ApiModelProperty("完成进度")
    private String progress;

    /**
     * 结果评估
     */
    @ApiModelProperty("结果评估")
    private String resultAccess;

    /**
     * 规范条目ID
     */
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

    /**
     * 实际开始日期
     */
    @ApiModelProperty("实际开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 完成状态（0：待完成 1：执行中 2：已完成）
     */
    @ApiModelProperty("完成状态（0：待完成 1：执行中 2：已完成）")
    private Integer status;

    /**
     * 任务ID
     */
    @ApiModelProperty("任务ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskInfoId;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;
}
