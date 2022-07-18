package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserSelectTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserSelectTest extends BaseModelExpression<UserSelectTest, Long> {

    public static final BaseModelExpression<UserSelectTest, Long> userSelectTest = new QUserSelectTest();
    public static final FieldExpression<Long> id = userSelectTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userSelectTest.fieldOf("username", String.class);
    public static final FieldExpression<String> password = userSelectTest.fieldOf("password", String.class);


    public QUserSelectTest() {
        super("UserSelectTest", UserSelectTest.class);
    }

    QUserSelectTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserSelectTest", UserSelectTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
