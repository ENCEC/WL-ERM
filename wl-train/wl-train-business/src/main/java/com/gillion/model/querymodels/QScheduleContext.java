package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ScheduleContext;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QScheduleContext extends BaseModelExpression<ScheduleContext, Long> {

    public static final BaseModelExpression<ScheduleContext, Long> scheduleContext = new QScheduleContext();
    public static final FieldExpression<Date> createTime = scheduleContext.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> id = scheduleContext.fieldOf("id", Long.class);
    public static final FieldExpression<Date> lastExecuteTime = scheduleContext.fieldOf("lastExecuteTime", Date.class);
    public static final FieldExpression<Date> nextExecuteTime = scheduleContext.fieldOf("nextExecuteTime", Date.class);
    public static final FieldExpression<String> scheduleCode = scheduleContext.fieldOf("scheduleCode", String.class);
    public static final FieldExpression<Boolean> scheduleStatus = scheduleContext.fieldOf("scheduleStatus", Boolean.class);
    public static final FieldExpression<Date> updateTime = scheduleContext.fieldOf("updateTime", Date.class);
    public static final FieldExpression<String> workerServers = scheduleContext.fieldOf("workerServers", String.class);


    public QScheduleContext() {
        super("ScheduleContext", ScheduleContext.class);
    }

    QScheduleContext(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ScheduleContext", ScheduleContext.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
