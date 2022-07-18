package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ChangeLog;

import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QChangeLog extends BaseModelExpression<ChangeLog, Long> {

    public static final BaseModelExpression<ChangeLog, Long> changeLog = new QChangeLog();
    public static final FieldExpression<Long> id = changeLog.fieldOf("id", Long.class);
    public static final FieldExpression<Long> dataSourceId = changeLog.fieldOf("dataSourceId", Long.class);
    public static final FieldExpression<String> modelName = changeLog.fieldOf("modelName", String.class);
    public static final FieldExpression<String> sqlStmt = changeLog.fieldOf("sqlStmt", String.class);
    public static final FieldExpression<String> changeData = changeLog.fieldOf("changeData", String.class);
    public static final FieldExpression<String> operator = changeLog.fieldOf("operator", String.class);
    public static final FieldExpression<Date> operateTime = changeLog.fieldOf("operateTime", Date.class);


    public QChangeLog() {
        super("ChangeLog", ChangeLog.class);
    }

    QChangeLog(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ChangeLog", ChangeLog.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
