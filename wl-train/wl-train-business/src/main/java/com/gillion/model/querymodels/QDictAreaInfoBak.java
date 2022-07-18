package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.DictAreaInfoBak;

import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QDictAreaInfoBak extends BaseModelExpression<DictAreaInfoBak, String> {

    public static final BaseModelExpression<DictAreaInfoBak, String> dictAreaInfoBak = new QDictAreaInfoBak();
    public static final FieldExpression<String> areaCode = dictAreaInfoBak.fieldOf("areaCode", String.class);
    public static final FieldExpression<String> areaShortName = dictAreaInfoBak.fieldOf("areaShortName", String.class);
    public static final FieldExpression<String> areaFullName = dictAreaInfoBak.fieldOf("areaFullName", String.class);


    public QDictAreaInfoBak() {
        super("DictAreaInfoBak", DictAreaInfoBak.class);
    }

    QDictAreaInfoBak(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "DictAreaInfoBak", DictAreaInfoBak.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return areaCode;
    }
}
