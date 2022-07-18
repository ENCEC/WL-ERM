package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserAddTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserAddTest extends BaseModelExpression<UserAddTest, Long> {

    public static final BaseModelExpression<UserAddTest, Long> userAddTest = new QUserAddTest();
    public static final FieldExpression<Long> id = userAddTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userAddTest.fieldOf("username", String.class);
    public static final FieldExpression<String> password = userAddTest.fieldOf("password", String.class);


    public QUserAddTest() {
        super("UserAddTest", UserAddTest.class);
    }

    QUserAddTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserAddTest", UserAddTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
