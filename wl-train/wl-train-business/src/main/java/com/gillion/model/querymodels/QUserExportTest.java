package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.UserExportTest;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUserExportTest extends BaseModelExpression<UserExportTest, Long> {

    public static final BaseModelExpression<UserExportTest, Long> userExportTest = new QUserExportTest();
    public static final FieldExpression<Long> id = userExportTest.fieldOf("id", Long.class);
    public static final FieldExpression<String> username = userExportTest.fieldOf("username", String.class);
    public static final FieldExpression<String> password = userExportTest.fieldOf("password", String.class);


    public QUserExportTest() {
        super("UserExportTest", UserExportTest.class);
    }

    QUserExportTest(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UserExportTest", UserExportTest.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
