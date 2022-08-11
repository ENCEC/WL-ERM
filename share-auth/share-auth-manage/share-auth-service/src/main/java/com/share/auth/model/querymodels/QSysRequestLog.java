package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysRequestLog;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysRequestLog extends BaseModelExpression<SysRequestLog, Long> {

    public static final BaseModelExpression<SysRequestLog, Long> sysRequestLog = new QSysRequestLog();
    public static final FieldExpression<Date> createTime = sysRequestLog.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysRequestLog.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysRequestLog.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> headerParam = sysRequestLog.fieldOf("headerParam", String.class);
    public static final FieldExpression<Boolean> isSuccess = sysRequestLog.fieldOf("isSuccess", Boolean.class);
    public static final FieldExpression<String> method = sysRequestLog.fieldOf("method", String.class);
    public static final FieldExpression<Long> modifierId = sysRequestLog.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysRequestLog.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysRequestLog.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> queryString = sysRequestLog.fieldOf("queryString", String.class);
    public static final FieldExpression<Integer> recordVersion = sysRequestLog.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> rectryCount = sysRequestLog.fieldOf("rectryCount", Integer.class);
    public static final FieldExpression<String> remoteAddress = sysRequestLog.fieldOf("remoteAddress", String.class);
    public static final FieldExpression<String> requestBody = sysRequestLog.fieldOf("requestBody", String.class);
    public static final FieldExpression<String> requestUrl = sysRequestLog.fieldOf("requestUrl", String.class);
    public static final FieldExpression<String> result = sysRequestLog.fieldOf("result", String.class);
    public static final FieldExpression<String> statusCode = sysRequestLog.fieldOf("statusCode", String.class);
    public static final FieldExpression<Long> sysRequestLogId = sysRequestLog.fieldOf("sysRequestLogId", Long.class);
    public static final FieldExpression<String> type = sysRequestLog.fieldOf("type", String.class);


    public QSysRequestLog() {
        super("SysRequestLog", SysRequestLog.class);
    }

    QSysRequestLog(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysRequestLog", SysRequestLog.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysRequestLogId;
    }
}
