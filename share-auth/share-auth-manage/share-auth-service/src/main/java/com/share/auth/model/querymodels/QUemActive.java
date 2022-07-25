package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemActive;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemActive extends BaseModelExpression<UemActive, Long> {

    public static final BaseModelExpression<UemActive, Long> uemActive = new QUemActive();
    public static final FieldExpression<String> activeContent = uemActive.fieldOf("activeContent", String.class);
    public static final FieldExpression<Date> activeTime = uemActive.fieldOf("activeTime", Date.class);
    public static final FieldExpression<String> activeType = uemActive.fieldOf("activeType", String.class);
    public static final FieldExpression<Date> createTime = uemActive.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemActive.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemActive.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> failureTime = uemActive.fieldOf("failureTime", Date.class);
    public static final FieldExpression<Boolean> isValid = uemActive.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = uemActive.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemActive.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemActive.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> receiverAccount = uemActive.fieldOf("receiverAccount", String.class);
    public static final FieldExpression<String> receiverType = uemActive.fieldOf("receiverType", String.class);
    public static final FieldExpression<Integer> recordVersion = uemActive.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemActiveId = uemActive.fieldOf("uemActiveId", Long.class);


    public QUemActive() {
        super("UemActive", UemActive.class);
    }

    QUemActive(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemActive", UemActive.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemActiveId;
    }
}
