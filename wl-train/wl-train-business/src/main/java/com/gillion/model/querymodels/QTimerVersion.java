package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.TimerVersion;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QTimerVersion extends BaseModelExpression<TimerVersion, Integer> {

    public static final BaseModelExpression<TimerVersion, Integer> timerVersion = new QTimerVersion();
    public static final FieldExpression<Integer> timerId = timerVersion.fieldOf("timerId", Integer.class);
    public static final FieldExpression<String> identifier = timerVersion.fieldOf("identifier", String.class);
    public static final FieldExpression<Long> version = timerVersion.fieldOf("version", Long.class);
    public static final FieldExpression<Date> modifyTime = timerVersion.fieldOf("modifyTime", Date.class);


    public QTimerVersion() {
        super("TimerVersion", TimerVersion.class);
    }

    QTimerVersion(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "TimerVersion", TimerVersion.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return timerId;
    }
}
