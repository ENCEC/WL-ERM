package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.DictAreaInfo;

import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QDictAreaInfo extends BaseModelExpression<DictAreaInfo, String> {

    public static final BaseModelExpression<DictAreaInfo, String> dictAreaInfo = new QDictAreaInfo();
    public static final FieldExpression<String> areaCode = dictAreaInfo.fieldOf("areaCode", String.class);
    public static final FieldExpression<String> areaShortName = dictAreaInfo.fieldOf("areaShortName", String.class);
    public static final FieldExpression<String> areaFullName = dictAreaInfo.fieldOf("areaFullName", String.class);


    public QDictAreaInfo() {
        super("DictAreaInfo", DictAreaInfo.class);
    }

    QDictAreaInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "DictAreaInfo", DictAreaInfo.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return areaCode;
    }
}
