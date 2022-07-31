package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.TaskInfo;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QTaskInfo extends BaseModelExpression<TaskInfo, Long> {

    public static final BaseModelExpression<TaskInfo, Long> taskInfo = new QTaskInfo();
    public static final FieldExpression<Date> createTime = taskInfo.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = taskInfo.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = taskInfo.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> endDate = taskInfo.fieldOf("endDate", Date.class);
    public static final FieldExpression<Long> modifierId = taskInfo.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = taskInfo.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = taskInfo.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> parentTaskId = taskInfo.fieldOf("parentTaskId", Long.class);
    public static final FieldExpression<Date> planEndDate = taskInfo.fieldOf("planEndDate", Date.class);
    public static final FieldExpression<Date> planStartDate = taskInfo.fieldOf("planStartDate", Date.class);
    public static final FieldExpression<Date> publishDate = taskInfo.fieldOf("publishDate", Date.class);
    public static final FieldExpression<Integer> recordVersion = taskInfo.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Date> startDate = taskInfo.fieldOf("startDate", Date.class);
    public static final FieldExpression<Integer> status = taskInfo.fieldOf("status", Integer.class);
    public static final FieldExpression<Long> taskInfoId = taskInfo.fieldOf("taskInfoId", Long.class);
    public static final FieldExpression<String> taskTitle = taskInfo.fieldOf("taskTitle", String.class);
    public static final FieldExpression<String> taskType = taskInfo.fieldOf("taskType", String.class);


    public QTaskInfo() {
        super("TaskInfo", TaskInfo.class);
    }

    QTaskInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "TaskInfo", TaskInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return taskInfoId;
    }
}
