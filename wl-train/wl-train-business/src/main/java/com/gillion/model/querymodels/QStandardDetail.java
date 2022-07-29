package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.StandardDetail;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QStandardDetail extends BaseModelExpression<StandardDetail, Long> {

    public static final BaseModelExpression<StandardDetail, Long> standardDetail = new QStandardDetail();
    public static final FieldExpression<Integer> actionSerialNum = standardDetail.fieldOf("actionSerialNum", Integer.class);
    public static final FieldExpression<Date> createTime = standardDetail.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = standardDetail.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = standardDetail.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> detailName = standardDetail.fieldOf("detailName", String.class);
    public static final FieldExpression<String> itemType = standardDetail.fieldOf("itemType", String.class);
    public static final FieldExpression<Long> modifierId = standardDetail.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = standardDetail.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = standardDetail.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = standardDetail.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> standardDetailId = standardDetail.fieldOf("standardDetailId", Long.class);
    public static final FieldExpression<Long> standardEntryId = standardDetail.fieldOf("standardEntryId", Long.class);
    public static final FieldExpression<Boolean> status = standardDetail.fieldOf("status", Boolean.class);


    public QStandardDetail() {
        super("StandardDetail", StandardDetail.class);
    }

    QStandardDetail(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "StandardDetail", StandardDetail.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return standardDetailId;
    }
}
