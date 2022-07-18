package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserUpdateTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserUpdateTest extends BaseModelExpression<UserUpdateTest, Long> {

    public static final BaseModelExpression<UserUpdateTest, Long> userUpdateTest = new QUserUpdateTest();
    public static final FieldExpression<Long> id = userUpdateTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userUpdateTest.fieldOf("username", String.class);
    public static final FieldExpression<String> password = userUpdateTest.fieldOf("password", String.class);


    public QUserUpdateTest() {
        super("UserUpdateTest", UserUpdateTest.class);
    }

    QUserUpdateTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserUpdateTest", UserUpdateTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
