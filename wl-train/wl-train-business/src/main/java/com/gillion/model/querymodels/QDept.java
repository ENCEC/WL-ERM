package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Dept;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QDept extends BaseModelExpression<Dept, Long> {

    public static final BaseModelExpression<Dept, Long> dept = new QDept();
    public static final FieldExpression<String> deptName = dept.fieldOf("deptName", String.class);
    public static final FieldExpression<String> test = dept.fieldOf("test", String.class);
    public static final FieldExpression<Long> deptId = dept.fieldOf("deptId", Long.class);
    public static final FieldExpression<String> principalGroupCode = dept.fieldOf("principalGroupCode", String.class);
    public static final FieldExpression<String> t2 = dept.fieldOf("t2", String.class);


    public QDept() {
        super("Dept", Dept.class);
    }

    QDept(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Dept", Dept.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return deptId;
    }
}
