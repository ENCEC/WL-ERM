package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.RuleNumberConfig;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QRuleNumberConfig extends BaseModelExpression<RuleNumberConfig, Long> {

    public static final BaseModelExpression<RuleNumberConfig, Long> ruleNumberConfig = new QRuleNumberConfig();
    public static final FieldExpression<String> ruleCode = ruleNumberConfig.fieldOf("ruleCode", String.class);
    public static final FieldExpression<String> groupName = ruleNumberConfig.fieldOf("groupName", String.class);
    public static final FieldExpression<String> expression = ruleNumberConfig.fieldOf("expression", String.class);
    public static final FieldExpression<Integer> controlType = ruleNumberConfig.fieldOf("controlType", Integer.class);
    public static final FieldExpression<Date> createTime = ruleNumberConfig.fieldOf("createTime", Date.class);
    public static final FieldExpression<Integer> maxValue = ruleNumberConfig.fieldOf("maxValue", Integer.class);
    public static final FieldExpression<Integer> resetType = ruleNumberConfig.fieldOf("resetType", Integer.class);
    public static final FieldExpression<String> ruleName = ruleNumberConfig.fieldOf("ruleName", String.class);
    public static final FieldExpression<Date> updateTime = ruleNumberConfig.fieldOf("updateTime", Date.class);
    public static final FieldExpression<Integer> step = ruleNumberConfig.fieldOf("step", Integer.class);
    public static final FieldExpression<Long> id = ruleNumberConfig.fieldOf("id", Long.class);
    public static final FieldExpression<Integer> initValue = ruleNumberConfig.fieldOf("initValue", Integer.class);


    public QRuleNumberConfig() {
        super("RuleNumberConfig", RuleNumberConfig.class);
    }

    QRuleNumberConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "RuleNumberConfig", RuleNumberConfig.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
