package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.DictProductType;

import java.lang.Boolean;
import java.lang.String;
import java.math.BigDecimal;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QDictProductType extends BaseModelExpression<DictProductType, String> {

    public static final BaseModelExpression<DictProductType, String> dictProductType = new QDictProductType();
    public static final FieldExpression<String> productCode = dictProductType.fieldOf("productCode", String.class);
    public static final FieldExpression<String> productName = dictProductType.fieldOf("productName", String.class);
    public static final FieldExpression<Boolean> productType = dictProductType.fieldOf("productType", Boolean.class);
    public static final FieldExpression<BigDecimal> productPrice = dictProductType.fieldOf("productPrice", BigDecimal.class);


    public QDictProductType() {
        super("DictProductType", DictProductType.class);
    }

    QDictProductType(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "DictProductType", DictProductType.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return productCode;
    }
}
