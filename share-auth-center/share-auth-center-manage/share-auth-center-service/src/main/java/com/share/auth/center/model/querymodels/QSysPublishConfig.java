package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.SysApplication;
import com.share.auth.center.model.entity.SysPublishConfig;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysPublishConfig extends BaseModelExpression<SysPublishConfig, Long> {

    public static final BaseModelExpression<SysPublishConfig, Long> sysPublishConfig = new QSysPublishConfig();
    public static final FieldExpression<String> applicationCode = sysPublishConfig.fieldOf("applicationCode", String.class);
    public static final FieldExpression<Long> createCompanyId = sysPublishConfig.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = sysPublishConfig.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = sysPublishConfig.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysPublishConfig.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysPublishConfig.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = sysPublishConfig.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isOpen = sysPublishConfig.fieldOf("isOpen", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysPublishConfig.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysPublishConfig.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = sysPublishConfig.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = sysPublishConfig.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = sysPublishConfig.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysPublishConfig.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysPublishConfigId = sysPublishConfig.fieldOf("sysPublishConfigId", Long.class);
    public static final FieldExpression<String> times = sysPublishConfig.fieldOf("times", String.class);

    public static final BaseModelExpression<SysApplication, Long> SYSPublishConfig = new QSysApplication(sysPublishConfig, "SYSPublishConfig");

    public QSysPublishConfig() {
        super("SysPublishConfig", SysPublishConfig.class);
    }

    QSysPublishConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysPublishConfig", SysPublishConfig.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysPublishConfigId;
    }
}
