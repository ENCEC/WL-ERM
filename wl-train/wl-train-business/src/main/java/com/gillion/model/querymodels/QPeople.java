package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.People;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QPeople extends BaseModelExpression<People, Long> {

    public static final BaseModelExpression<People, Long> people = new QPeople();
    public static final FieldExpression<Long> testId = people.fieldOf("testId", Long.class);
    public static final FieldExpression<String> testName = people.fieldOf("testName", String.class);
    public static final FieldExpression<Integer> testAge = people.fieldOf("testAge", Integer.class);


    public QPeople() {
        super("People", People.class);
    }

    QPeople(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "People", People.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return testId;
    }
}
