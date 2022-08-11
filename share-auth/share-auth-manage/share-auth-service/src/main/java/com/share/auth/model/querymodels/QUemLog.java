package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemLog;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemLog extends BaseModelExpression<UemLog, Long> {

    public static final BaseModelExpression<UemLog, Long> uemLog = new QUemLog();
    public static final FieldExpression<Long> applicationId = uemLog.fieldOf("applicationId", Long.class);
    public static final FieldExpression<String> browser = uemLog.fieldOf("browser", String.class);
    public static final FieldExpression<String> city = uemLog.fieldOf("city", String.class);
    public static final FieldExpression<Long> companyId = uemLog.fieldOf("companyId", Long.class);
    public static final FieldExpression<Date> createTime = uemLog.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemLog.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemLog.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> equipment = uemLog.fieldOf("equipment", String.class);
    public static final FieldExpression<String> ipAddress = uemLog.fieldOf("ipAddress", String.class);
    public static final FieldExpression<Date> logDate = uemLog.fieldOf("logDate", Date.class);
    public static final FieldExpression<String> loginType = uemLog.fieldOf("loginType", String.class);
    public static final FieldExpression<Long> modifierId = uemLog.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemLog.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemLog.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemLog.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> result = uemLog.fieldOf("result", String.class);
    public static final FieldExpression<Long> uemLogId = uemLog.fieldOf("uemLogId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemLog.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<String> way = uemLog.fieldOf("way", String.class);


    public QUemLog() {
        super("UemLog", UemLog.class);
    }

    QUemLog(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemLog", UemLog.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemLogId;
    }
}
