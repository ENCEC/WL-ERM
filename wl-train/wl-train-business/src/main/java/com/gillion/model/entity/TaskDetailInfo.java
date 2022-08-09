package com.gillion.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "task_detail_info")
public class TaskDetailInfo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 任务子表ID
     */
    @Id
    @Column(name = "task_detail_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long taskDetailId;

    /**
     * 执行周期
     */
    @Column(name = "action_period")
    private Integer actionPeriod;

    /**
     * 执行顺序
     */
    @Column(name = "action_serial_num")
    private Integer actionSerialNum;

    /**
     * 执行时间
     */
    @Column(name = "action_time")
    private Integer actionTime;

    /**
     * 申请日期
     */
    @Column(name = "apply_date")
    private Date applyDate;

    /**
     * 审批日期
     */
    @Column(name = "approval_date")
    private Date approvalDate;

    /**
     * 审批人ID
     */
    @Column(name = "approver")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long approver;

    /**
     * 审批人姓名
     */
    @Column(name = "approver_name")
    private String approverName;

    /**
     * 审批人意见
     */
    @Column(name = "approval_remark")
    private String approvalRemark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**
     * 创建人名称
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 实际完成日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 面谈评语
     */
    @Column(name = "face_remark")
    private String faceRemark;

    /**
     * 面谈时间
     */
    @Column(name = "face_time")
    private Date faceTime;

    /**
     * 面谈成绩
     */
    @Column(name = "face_score")
    private String faceScore;


    /**
     * 责任人ID
     */
    @Column(name = "leader")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long leader;

    /**
     * 责任人姓名
     */
    @Column(name = "leader_name")
    private String leaderName;

    /**
     * 修改人id
     */
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**
     * 修改人名称
     */
    @Column(name = "modifier_name")
    private String modifierName;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 转正评语
     */
    @Column(name = "offer_remark")
    private String offerRemark;

    /**
     * 转正类型
     */
    @Column(name = "offer_type")
    private String offerType;

    /**
     * 统筹人ID列表
     */
    @Column(name = "ordinator")
    private String ordinator;

    /**
     * 统筹人姓名列表
     */
    @Column(name = "ordinator_name")
    private String ordinatorName;

    /**
     * 计划完成日期
     */
    @Column(name = "plan_end_date")
    private Date planEndDate;

    /**
     * 计划开始日期
     */
    @Column(name = "plan_start_date")
    private Date planStartDate;

    /**
     * 完成进度
     */
    @Column(name = "progress")
    private String progress;

    /**
     * 版本号
     */
    @Column(name = "record_version")
    private Integer recordVersion;

    /**
     * 结果评估
     */
    @Column(name = "result_access")
    private String resultAccess;

    /**
     * 规范条目细则ID
     */
    @Column(name = "standard_detail_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardDetailId;

    @Column(name = "standard_detail_name")
    private String standardDetailName;

    /**
     * 规范条目ID
     */
    @Column(name = "standard_entry_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardEntryId;

    @Column(name = "standard_entry_name")
    private String standardEntryName;

    /**
     * 实际开始日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 完成状态（0：待完成 1：执行中 2：已完成 3：退回重做）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 任务ID
     */
    @Column(name = "task_info_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long taskInfoId;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

}