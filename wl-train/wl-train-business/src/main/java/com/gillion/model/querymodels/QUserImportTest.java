package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserImportTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserImportTest extends BaseModelExpression<UserImportTest, Long> {

    public static final BaseModelExpression<UserImportTest, Long> userImportTest = new QUserImportTest();
    public static final FieldExpression<String> password = userImportTest.fieldOf("password", String.class);
    public static final FieldExpression<Long> id = userImportTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userImportTest.fieldOf("username", String.class);


    public QUserImportTest() {
        super("UserImportTest", UserImportTest.class);
    }

    QUserImportTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserImportTest", UserImportTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
