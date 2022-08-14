package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;

import java.util.Date;


/**
 * @author daoServiceGenerator
 * @version 1.0.0.0
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QTaskDetailInfo extends BaseModelExpression<TaskDetailInfo, Long> {

    public static final BaseModelExpression<TaskDetailInfo, Long> taskDetailInfo = new QTaskDetailInfo();
    public static final FieldExpression<Integer> actionPeriod = taskDetailInfo.fieldOf("actionPeriod", Integer.class);
    public static final FieldExpression<Integer> actionSerialNum = taskDetailInfo.fieldOf("actionSerialNum", Integer.class);
    public static final FieldExpression<Integer> actionTime = taskDetailInfo.fieldOf("actionTime", Integer.class);
    public static final FieldExpression<Date> applyDate = taskDetailInfo.fieldOf("applyDate", Date.class);
    public static final FieldExpression<Date> approvalDate = taskDetailInfo.fieldOf("approvalDate", Date.class);
    public static final FieldExpression<Long> approver = taskDetailInfo.fieldOf("approver", Long.class);
    public static final FieldExpression<String> approverName = taskDetailInfo.fieldOf("approverName", String.class);
    public static final FieldExpression<String> approvalRemark = taskDetailInfo.fieldOf("approvalRemark", String.class);
    public static final FieldExpression<Long> auditId = taskDetailInfo.fieldOf("auditId", Long.class);
    public static final FieldExpression<String> auditName = taskDetailInfo.fieldOf("auditName", String.class);
    public static final FieldExpression<Date> auditDate = taskDetailInfo.fieldOf("auditDate", Date.class);
    public static final FieldExpression<String> auditResult = taskDetailInfo.fieldOf("auditResult", String.class);
    public static final FieldExpression<String> auditRemark = taskDetailInfo.fieldOf("auditRemark", String.class);
    public static final FieldExpression<Date> createTime = taskDetailInfo.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = taskDetailInfo.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = taskDetailInfo.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> endDate = taskDetailInfo.fieldOf("endDate", Date.class);
    public static final FieldExpression<String> faceRemark = taskDetailInfo.fieldOf("faceRemark", String.class);
    public static final FieldExpression<Date> faceTime = taskDetailInfo.fieldOf("faceTime", Date.class);
    public static final FieldExpression<String> faceScore = taskDetailInfo.fieldOf("faceScore", String.class);
    public static final FieldExpression<String> faceResult = taskDetailInfo.fieldOf("faceResult", String.class);
    public static final FieldExpression<Long> leader = taskDetailInfo.fieldOf("leader", Long.class);
    public static final FieldExpression<String> leaderName = taskDetailInfo.fieldOf("leaderName", String.class);
    public static final FieldExpression<Long> modifierId = taskDetailInfo.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = taskDetailInfo.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = taskDetailInfo.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> offerRemark = taskDetailInfo.fieldOf("offerRemark", String.class);
    public static final FieldExpression<String> offerType = taskDetailInfo.fieldOf("offerType", String.class);
    public static final FieldExpression<String> ordinator = taskDetailInfo.fieldOf("ordinator", String.class);
    public static final FieldExpression<String> ordinatorName = taskDetailInfo.fieldOf("ordinatorName", String.class);
    public static final FieldExpression<Date> planEndDate = taskDetailInfo.fieldOf("planEndDate", Date.class);
    public static final FieldExpression<Date> planStartDate = taskDetailInfo.fieldOf("planStartDate", Date.class);
    public static final FieldExpression<String> progress = taskDetailInfo.fieldOf("progress", String.class);
    public static final FieldExpression<Integer> recordVersion = taskDetailInfo.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> resultAccess = taskDetailInfo.fieldOf("resultAccess", String.class);
    public static final FieldExpression<Long> standardDetailId = taskDetailInfo.fieldOf("standardDetailId", Long.class);
    public static final FieldExpression<String> standardDetailName = taskDetailInfo.fieldOf("standardDetailName", String.class);
    public static final FieldExpression<Long> standardEntryId = taskDetailInfo.fieldOf("standardEntryId", Long.class);
    public static final FieldExpression<String> standardEntryName = taskDetailInfo.fieldOf("standardEntryName", String.class);
    public static final FieldExpression<Date> startDate = taskDetailInfo.fieldOf("startDate", Date.class);
    public static final FieldExpression<Integer> status = taskDetailInfo.fieldOf("status", Integer.class);
    public static final FieldExpression<Long> taskDetailId = taskDetailInfo.fieldOf("taskDetailId", Long.class);
    public static final FieldExpression<Long> taskInfoId = taskDetailInfo.fieldOf("taskInfoId", Long.class);
    public static final FieldExpression<String> taskName = taskDetailInfo.fieldOf("taskName", String.class);
    public static final FieldExpression<Long> interviewerId = taskDetailInfo.fieldOf("interviewerId", Long.class);
    public static final FieldExpression<String> interviewerName = taskDetailInfo.fieldOf("interviewerName", String.class);

    public QTaskDetailInfo() {
        super("TaskDetailInfo", TaskDetailInfo.class);
    }

    QTaskDetailInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "TaskDetailInfo", TaskDetailInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return taskDetailId;
    }
}
