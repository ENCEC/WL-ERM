package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ScheduleLog;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QScheduleLog extends BaseModelExpression<ScheduleLog, Long> {

    public static final BaseModelExpression<ScheduleLog, Long> scheduleLog = new QScheduleLog();
    public static final FieldExpression<Integer> consumingTime = scheduleLog.fieldOf("consumingTime", Integer.class);
    public static final FieldExpression<Date> createTime = scheduleLog.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> description = scheduleLog.fieldOf("description", String.class);
    public static final FieldExpression<Date> endTime = scheduleLog.fieldOf("endTime", Date.class);
    public static final FieldExpression<Long> id = scheduleLog.fieldOf("id", Long.class);
    public static final FieldExpression<Long> scheduleId = scheduleLog.fieldOf("scheduleId", Long.class);
    public static final FieldExpression<Integer> shard = scheduleLog.fieldOf("shard", Integer.class);
    public static final FieldExpression<Date> startTime = scheduleLog.fieldOf("startTime", Date.class);
    public static final FieldExpression<Boolean> status = scheduleLog.fieldOf("status", Boolean.class);
    public static final FieldExpression<String> supervisor = scheduleLog.fieldOf("supervisor", String.class);
    public static final FieldExpression<String> triggerId = scheduleLog.fieldOf("triggerId", String.class);
    public static final FieldExpression<Date> updateTime = scheduleLog.fieldOf("updateTime", Date.class);
    public static final FieldExpression<String> workerNode = scheduleLog.fieldOf("workerNode", String.class);


    public QScheduleLog() {
        super("ScheduleLog", ScheduleLog.class);
    }

    QScheduleLog(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ScheduleLog", ScheduleLog.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
