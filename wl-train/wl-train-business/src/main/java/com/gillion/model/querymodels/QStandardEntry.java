package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.StandardEntry;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QStandardEntry extends BaseModelExpression<StandardEntry, Long> {

    public static final BaseModelExpression<StandardEntry, Long> standardEntry = new QStandardEntry();
    public static final FieldExpression<Integer> actionPeriod = standardEntry.fieldOf("actionPeriod", Integer.class);
    public static final FieldExpression<String> actionRemark = standardEntry.fieldOf("actionRemark", String.class);
    public static final FieldExpression<Long> actionRoleId = standardEntry.fieldOf("actionRoleId", Long.class);
    public static final FieldExpression<Integer> actionSerialNum = standardEntry.fieldOf("actionSerialNum", Integer.class);
    public static final FieldExpression<Date> actionTime = standardEntry.fieldOf("actionTime", Date.class);
    public static final FieldExpression<String> applyPostId = standardEntry.fieldOf("applyPostId", String.class);
    public static final FieldExpression<Long> applyProfessorId = standardEntry.fieldOf("applyProfessorId", Long.class);
    public static final FieldExpression<Date> createTime = standardEntry.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = standardEntry.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = standardEntry.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> entryName = standardEntry.fieldOf("entryName", String.class);
    public static final FieldExpression<Boolean> isNeed = standardEntry.fieldOf("isNeed", Boolean.class);
    public static final FieldExpression<Long> modifierId = standardEntry.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = standardEntry.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = standardEntry.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> ordinatorId = standardEntry.fieldOf("ordinatorId", Long.class);
    public static final FieldExpression<Integer> recordVersion = standardEntry.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> standardEntryId = standardEntry.fieldOf("standardEntryId", Long.class);
    public static final FieldExpression<Boolean> status = standardEntry.fieldOf("status", Boolean.class);


    public QStandardEntry() {
        super("StandardEntry", StandardEntry.class);
    }

    QStandardEntry(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "StandardEntry", StandardEntry.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return standardEntryId;
    }
}
