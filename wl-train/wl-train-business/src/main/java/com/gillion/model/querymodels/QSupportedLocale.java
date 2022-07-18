package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.SupportedLocale;

import java.lang.Integer;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSupportedLocale extends BaseModelExpression<SupportedLocale, Integer> {

    public static final BaseModelExpression<SupportedLocale, Integer> supportedLocale = new QSupportedLocale();
    public static final FieldExpression<Integer> id = supportedLocale.fieldOf("id", Integer.class);
    public static final FieldExpression<String> language = supportedLocale.fieldOf("language", String.class);
    public static final FieldExpression<String> country = supportedLocale.fieldOf("country", String.class);
    public static final FieldExpression<String> variant = supportedLocale.fieldOf("variant", String.class);
    public static final FieldExpression<String> display = supportedLocale.fieldOf("display", String.class);
    public static final FieldExpression<String> state = supportedLocale.fieldOf("state", String.class);


    public QSupportedLocale() {
        super("SupportedLocale", SupportedLocale.class);
    }

    QSupportedLocale(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SupportedLocale", SupportedLocale.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return id;
    }
}
