package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Gift;

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
public class QGift extends BaseModelExpression<Gift, Long> {

    public static final BaseModelExpression<Gift, Long> gift = new QGift();
    public static final FieldExpression<Long> id = gift.fieldOf("id", Long.class);
    public static final FieldExpression<String> giftName = gift.fieldOf("giftName", String.class);
    public static final FieldExpression<String> giftCode = gift.fieldOf("giftCode", String.class);
    public static final FieldExpression<Integer> productionYear = gift.fieldOf("productionYear", Integer.class);
    public static final FieldExpression<Long> shopId = gift.fieldOf("shopId", Long.class);
    public static final FieldExpression<Integer> version = gift.fieldOf("version", Integer.class);
    public static final FieldExpression<Boolean> destroyed = gift.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> creator = gift.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = gift.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = gift.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = gift.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> userAttribute = gift.fieldOf("userAttribute", String.class);


    public QGift() {
        super("Gift", Gift.class);
    }

    QGift(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Gift", Gift.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
