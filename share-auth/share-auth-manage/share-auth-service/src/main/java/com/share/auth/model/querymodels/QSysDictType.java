package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysDictType;

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
public class QSysDictType extends BaseModelExpression<SysDictType, Long> {

    public static final BaseModelExpression<SysDictType, Long> sysDictType = new QSysDictType();
    public static final FieldExpression<Date> createTime = sysDictType.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysDictType.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysDictType.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> dictTypeCode = sysDictType.fieldOf("dictTypeCode", String.class);
    public static final FieldExpression<String> dictTypeName = sysDictType.fieldOf("dictTypeName", String.class);
    public static final FieldExpression<Date> invalidTime = sysDictType.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isValid = sysDictType.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysDictType.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysDictType.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysDictType.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysDictType.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = sysDictType.fieldOf("remark", String.class);
    public static final FieldExpression<Long> sysDictTypeId = sysDictType.fieldOf("sysDictTypeId", Long.class);


    public QSysDictType() {
        super("SysDictType", SysDictType.class);
    }

    QSysDictType(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysDictType", SysDictType.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysDictTypeId;
    }
}
