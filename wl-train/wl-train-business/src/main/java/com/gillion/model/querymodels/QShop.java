package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Shop;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QShop extends BaseModelExpression<Shop, Long> {

    public static final BaseModelExpression<Shop, Long> shop = new QShop();
    public static final FieldExpression<String> address = shop.fieldOf("address", String.class);
    public static final FieldExpression<Date> buildTime = shop.fieldOf("buildTime", Date.class);
    public static final FieldExpression<String> giftShop = shop.fieldOf("giftShop", String.class);
    public static final FieldExpression<Long> shopId = shop.fieldOf("shopId", Long.class);
    public static final FieldExpression<Integer> version = shop.fieldOf("version", Integer.class);


    public QShop() {
        super("Shop", Shop.class);
    }

    QShop(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Shop", Shop.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return shopId;
    }
}
