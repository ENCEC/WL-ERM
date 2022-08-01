package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ScheduleAssignInfo;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QScheduleAssignInfo extends BaseModelExpression<ScheduleAssignInfo, Long> {

    public static final BaseModelExpression<ScheduleAssignInfo, Long> scheduleAssignInfo = new QScheduleAssignInfo();
    public static final FieldExpression<Date> createTime = scheduleAssignInfo.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> id = scheduleAssignInfo.fieldOf("id", Long.class);
    public static final FieldExpression<String> scheduleCode = scheduleAssignInfo.fieldOf("scheduleCode", String.class);
    public static final FieldExpression<String> supervisor = scheduleAssignInfo.fieldOf("supervisor", String.class);
    public static final FieldExpression<Date> updateTime = scheduleAssignInfo.fieldOf("updateTime", Date.class);


    public QScheduleAssignInfo() {
        super("ScheduleAssignInfo", ScheduleAssignInfo.class);
    }

    QScheduleAssignInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ScheduleAssignInfo", ScheduleAssignInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
