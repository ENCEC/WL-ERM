package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.SysApplication;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysApplication extends BaseModelExpression<SysApplication, Long> {

    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication();
    public static final FieldExpression<String> applicationAbbreviName = sysApplication.fieldOf("applicationAbbreviName", String.class);
    public static final FieldExpression<String> applicationCode = sysApplication.fieldOf("applicationCode", String.class);
    public static final FieldExpression<String> applicationName = sysApplication.fieldOf("applicationName", String.class);
    public static final FieldExpression<String> applicationRemark = sysApplication.fieldOf("applicationRemark", String.class);
    public static final FieldExpression<String> applicationSecret = sysApplication.fieldOf("applicationSecret", String.class);
    public static final FieldExpression<String> applicationUrl = sysApplication.fieldOf("applicationUrl", String.class);
    public static final FieldExpression<String> contact = sysApplication.fieldOf("contact", String.class);
    public static final FieldExpression<String> contactTel = sysApplication.fieldOf("contactTel", String.class);
    public static final FieldExpression<Date> createTime = sysApplication.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysApplication.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysApplication.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = sysApplication.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isValid = sysApplication.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysApplication.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysApplication.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysApplication.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysApplication.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> relatedEnterprise = sysApplication.fieldOf("relatedEnterprise", String.class);
    public static final FieldExpression<Long> sysApplicationId = sysApplication.fieldOf("sysApplicationId", Long.class);

    public QSysApplication() {
        super("SysApplication", SysApplication.class);
    }

    QSysApplication(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysApplication", SysApplication.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysApplicationId;
    }
}
