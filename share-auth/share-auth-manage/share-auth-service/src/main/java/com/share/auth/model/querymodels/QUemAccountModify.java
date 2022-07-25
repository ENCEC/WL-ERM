package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemAccountModify;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemAccountModify extends BaseModelExpression<UemAccountModify, Long> {

    public static final BaseModelExpression<UemAccountModify, Long> uemAccountModify = new QUemAccountModify();
    public static final FieldExpression<String> aftData = uemAccountModify.fieldOf("aftData", String.class);
    public static final FieldExpression<String> befData = uemAccountModify.fieldOf("befData", String.class);
    public static final FieldExpression<Date> blindTime = uemAccountModify.fieldOf("blindTime", Date.class);
    public static final FieldExpression<Date> createTime = uemAccountModify.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemAccountModify.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemAccountModify.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = uemAccountModify.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemAccountModify.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemAccountModify.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> operType = uemAccountModify.fieldOf("operType", String.class);
    public static final FieldExpression<Integer> recordVersion = uemAccountModify.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> ssabUnionId = uemAccountModify.fieldOf("ssabUnionId", String.class);
    public static final FieldExpression<Long> ssacId = uemAccountModify.fieldOf("ssacId", Long.class);
    public static final FieldExpression<String> thirdAccountType = uemAccountModify.fieldOf("thirdAccountType", String.class);
    public static final FieldExpression<Long> uemAccountModifyId = uemAccountModify.fieldOf("uemAccountModifyId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemAccountModify.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<Date> unblindTime = uemAccountModify.fieldOf("unblindTime", Date.class);


    public QUemAccountModify() {
        super("UemAccountModify", UemAccountModify.class);
    }

    QUemAccountModify(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemAccountModify", UemAccountModify.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemAccountModifyId;
    }
}
