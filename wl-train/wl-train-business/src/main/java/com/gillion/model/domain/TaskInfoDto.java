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
import java.util.List;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/7/29
 */
@Data
@ApiModel("任务信息")
public class TaskInfoDto implements Serializable {

    /**任务ID*/
    @ApiModelProperty("任务ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskInfoId;

    /**任务分配人*/
    @ApiModelProperty("任务分配人")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dispatchers;

    /**任务分配人姓名*/
    @ApiModelProperty("任务分配人姓名")
    private Long dispatchersName;

    /**实际完成日期*/
    @ApiModelProperty("实际完成日期")
    private Date endDate;

    /**执行人*/
    @ApiModelProperty("执行人")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long executor;

    /**执行人姓名*/
    @ApiModelProperty("执行人姓名")
    private Long executorName;

    /**父级任务ID*/
    @ApiModelProperty("父级任务ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long parentTaskId;

    /**计划完成日期*/
    @ApiModelProperty("计划完成日期")
    private Date planEndDate;

    /**计划开始日期*/
    @ApiModelProperty("计划开始日期")
    private Date planStartDate;

    /**发布日期*/
    @ApiModelProperty("发布日期")
    private Date publishDate;

    /**版本号*/
    @ApiModelProperty("版本号")
    private Integer recordVersion;

    /**实际开始日期*/
    @ApiModelProperty("实际开始日期")
    private Date startDate;

    /**任务状态（0：待完成 1：执行中 2：已完成）*/
    @ApiModelProperty("任务状态（0：待完成 1：执行中 2：已完成）")
    private Integer status;

    /**任务标题*/
    @ApiModelProperty("任务标题")
    private String taskTitle;

    /**任务类型*/
    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("任务细则")
    private List<TaskDetailInfoDto> taskDetailInfoDtoList;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("页码")
    private Integer pageNo;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**创建人id*/
    @ApiModelProperty("创建人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**修改人id*/
    @ApiModelProperty("修改人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @ApiModelProperty("修改人名称")
    private String modifierName;

    /**修改时间*/
    @ApiModelProperty("修改时间")
    private Date modifyTime;

}
