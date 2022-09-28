package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysTag;

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
public class QSysTag extends BaseModelExpression<SysTag, Long> {

    public static final BaseModelExpression<SysTag, Long> sysTag = new QSysTag();
    public static final FieldExpression<Long> sysTagId = sysTag.fieldOf("sysTagId", Long.class);
    public static final FieldExpression<String> tagName = sysTag.fieldOf("tagName", String.class);
    public static final FieldExpression<String> tagDescription = sysTag.fieldOf("tagDescription", String.class);
    public static final FieldExpression<Boolean> status = sysTag.fieldOf("status", Boolean.class);
    public static final FieldExpression<Long> creatorId = sysTag.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysTag.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> createTime = sysTag.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> modifierId = sysTag.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysTag.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysTag.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysTag.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Boolean> isDeleted = sysTag.fieldOf("isDeleted", Boolean.class);


    public QSysTag() {
        super("SysTag", SysTag.class);
    }

    QSysTag(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysTag", SysTag.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysTagId;
    }
}
