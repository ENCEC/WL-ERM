package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.WorkerInfo;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QWorkerInfo extends BaseModelExpression<WorkerInfo, Long> {

    public static final BaseModelExpression<WorkerInfo, Long> workerInfo = new QWorkerInfo();
    public static final FieldExpression<Date> createTime = workerInfo.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> id = workerInfo.fieldOf("id", Long.class);
    public static final FieldExpression<Date> updateTime = workerInfo.fieldOf("updateTime", Date.class);
    public static final FieldExpression<String> workerNode = workerInfo.fieldOf("workerNode", String.class);


    public QWorkerInfo() {
        super("WorkerInfo", WorkerInfo.class);
    }

    QWorkerInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "WorkerInfo", WorkerInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
