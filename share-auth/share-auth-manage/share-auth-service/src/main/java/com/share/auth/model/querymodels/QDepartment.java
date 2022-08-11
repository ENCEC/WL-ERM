package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.Department;

import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QDepartment extends BaseModelExpression<Department, Long> {

    public static final BaseModelExpression<Department, Long> department = new QDepartment();
    public static final FieldExpression<Long> departmentId = department.fieldOf("departmentId", Long.class);
    public static final FieldExpression<String> departmentManager = department.fieldOf("departmentManager", String.class);
    public static final FieldExpression<String> departmentName = department.fieldOf("departmentName", String.class);


    public QDepartment() {
        super("Department", Department.class);
    }

    QDepartment(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Department", Department.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return departmentId;
    }
}
