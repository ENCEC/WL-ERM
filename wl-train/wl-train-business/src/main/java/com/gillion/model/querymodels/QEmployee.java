package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Employee;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.Short;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QEmployee extends BaseModelExpression<Employee, Long> {

    public static final BaseModelExpression<Employee, Long> employee = new QEmployee();
    public static final FieldExpression<Long> id = employee.fieldOf("id", Long.class);
    public static final FieldExpression<String> name = employee.fieldOf("name", String.class);
    public static final FieldExpression<String> cname = employee.fieldOf("cname", String.class);
    public static final FieldExpression<String> password = employee.fieldOf("password", String.class);
    public static final FieldExpression<Short> age = employee.fieldOf("age", Short.class);
    public static final FieldExpression<Boolean> gender = employee.fieldOf("gender", Boolean.class);
    public static final FieldExpression<String> email = employee.fieldOf("email", String.class);
    public static final FieldExpression<String> mobile = employee.fieldOf("mobile", String.class);
    public static final FieldExpression<String> address = employee.fieldOf("address", String.class);
    public static final FieldExpression<Long> departmentId = employee.fieldOf("departmentId", Long.class);
    public static final FieldExpression<String> creator = employee.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = employee.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = employee.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> birthDay = employee.fieldOf("birthDay", Date.class);
    public static final FieldExpression<Date> modifyTime = employee.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> version = employee.fieldOf("version", Long.class);
    public static final FieldExpression<Boolean> destroyed = employee.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> principalGroupCode = employee.fieldOf("principalGroupCode", String.class);


    public QEmployee() {
        super("Employee", Employee.class);
    }

    QEmployee(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Employee", Employee.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
