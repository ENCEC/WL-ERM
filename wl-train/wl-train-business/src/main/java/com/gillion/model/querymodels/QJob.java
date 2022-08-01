package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Job;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QJob extends BaseModelExpression<Job, Long> {

    public static final BaseModelExpression<Job, Long> job = new QJob();
    public static final FieldExpression<Date> createTime = job.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> cron = job.fieldOf("cron", String.class);
    public static final FieldExpression<String> description = job.fieldOf("description", String.class);
    public static final FieldExpression<Boolean> enabled = job.fieldOf("enabled", Boolean.class);
    public static final FieldExpression<Long> id = job.fieldOf("id", Long.class);
    public static final FieldExpression<String> scheduleCode = job.fieldOf("scheduleCode", String.class);
    public static final FieldExpression<Boolean> scheduleType = job.fieldOf("scheduleType", Boolean.class);
    public static final FieldExpression<Integer> shardCount = job.fieldOf("shardCount", Integer.class);
    public static final FieldExpression<Boolean> threadType = job.fieldOf("threadType", Boolean.class);
    public static final FieldExpression<Integer> timeout = job.fieldOf("timeout", Integer.class);
    public static final FieldExpression<Date> updateTime = job.fieldOf("updateTime", Date.class);


    public QJob() {
        super("Job", Job.class);
    }

    QJob(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Job", Job.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
