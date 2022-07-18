package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.SysNoRule;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysNoRule extends BaseModelExpression<SysNoRule, Long> {

    public static final BaseModelExpression<SysNoRule, Long> sysNoRule = new QSysNoRule();
    public static final FieldExpression<Date> createTime = sysNoRule.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysNoRule.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysNoRule.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = sysNoRule.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isValid = sysNoRule.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysNoRule.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysNoRule.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysNoRule.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysNoRule.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> ruleFormat = sysNoRule.fieldOf("ruleFormat", String.class);
    public static final FieldExpression<String> ruleName = sysNoRule.fieldOf("ruleName", String.class);
    public static final FieldExpression<String> ruleNumberLength = sysNoRule.fieldOf("ruleNumberLength", String.class);
    public static final FieldExpression<String> rulePrifix = sysNoRule.fieldOf("rulePrifix", String.class);
    public static final FieldExpression<Long> sysNoRuleId = sysNoRule.fieldOf("sysNoRuleId", Long.class);


    public QSysNoRule() {
        super("SysNoRule", SysNoRule.class);
    }

    QSysNoRule(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysNoRule", SysNoRule.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysNoRuleId;
    }
}
