package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.I18nMessages;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QI18nMessages extends BaseModelExpression<I18nMessages, Long> {

    public static final BaseModelExpression<I18nMessages, Long> i18nMessages = new QI18nMessages();
    public static final FieldExpression<Long> id = i18nMessages.fieldOf("id", Long.class);
    public static final FieldExpression<String> key = i18nMessages.fieldOf("key", String.class);
    public static final FieldExpression<Integer> localeId = i18nMessages.fieldOf("localeId", Integer.class);
    public static final FieldExpression<String> message = i18nMessages.fieldOf("message", String.class);


    public QI18nMessages() {
        super("I18nMessages", I18nMessages.class);
    }

    QI18nMessages(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "I18nMessages", I18nMessages.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
