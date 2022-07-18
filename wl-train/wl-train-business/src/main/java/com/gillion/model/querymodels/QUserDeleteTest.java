package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserDeleteTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserDeleteTest extends BaseModelExpression<UserDeleteTest, Long> {

    public static final BaseModelExpression<UserDeleteTest, Long> userDeleteTest = new QUserDeleteTest();
    public static final FieldExpression<Long> id = userDeleteTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userDeleteTest.fieldOf("username", String.class);
    public static final FieldExpression<String> password = userDeleteTest.fieldOf("password", String.class);


    public QUserDeleteTest() {
        super("UserDeleteTest", UserDeleteTest.class);
    }

    QUserDeleteTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserDeleteTest", UserDeleteTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
