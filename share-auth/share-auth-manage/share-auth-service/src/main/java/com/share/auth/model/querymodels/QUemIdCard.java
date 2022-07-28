package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemIdCard;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemIdCard extends BaseModelExpression<UemIdCard, Long> {

    public static final BaseModelExpression<UemIdCard, Long> uemIdCard = new QUemIdCard();
    public static final FieldExpression<Long> auditor = uemIdCard.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemIdCard.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemIdCard.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemIdCard.fieldOf("auditTime", Date.class);
    public static final FieldExpression<String> cardBackUrlId = uemIdCard.fieldOf("cardBackUrlId", String.class);
    public static final FieldExpression<String> cardPositiveUrlId = uemIdCard.fieldOf("cardPositiveUrlId", String.class);
    public static final FieldExpression<Date> createTime = uemIdCard.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemIdCard.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemIdCard.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> idCard = uemIdCard.fieldOf("idCard", String.class);
    public static final FieldExpression<Long> modifierId = uemIdCard.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemIdCard.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemIdCard.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> name = uemIdCard.fieldOf("name", String.class);
    public static final FieldExpression<Integer> recordVersion = uemIdCard.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Boolean> sex = uemIdCard.fieldOf("sex", Boolean.class);
    public static final FieldExpression<Long> uemIdCardId = uemIdCard.fieldOf("uemIdCardId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemIdCard.fieldOf("uemUserId", Long.class);


    public QUemIdCard() {
        super("UemIdCard", UemIdCard.class);
    }

    QUemIdCard(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemIdCard", UemIdCard.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemIdCardId;
    }
}
