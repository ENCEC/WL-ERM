package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Student;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QStudent extends BaseModelExpression<Student, Long> {

    public static final BaseModelExpression<Student, Long> student = new QStudent();
    public static final FieldExpression<Long> id = student.fieldOf("id", Long.class);
    public static final FieldExpression<String> name = student.fieldOf("name", String.class);
    public static final FieldExpression<String> password = student.fieldOf("password", String.class);
    public static final FieldExpression<String> confirmPassword = student.fieldOf("confirmPassword", String.class);
    public static final FieldExpression<Integer> sex = student.fieldOf("sex", Integer.class);
    public static final FieldExpression<String> email = student.fieldOf("email", String.class);
    public static final FieldExpression<String> mobile = student.fieldOf("mobile", String.class);
    public static final FieldExpression<BigDecimal> money = student.fieldOf("money", BigDecimal.class);
    public static final FieldExpression<Integer> score = student.fieldOf("score", Integer.class);
    public static final FieldExpression<Integer> age = student.fieldOf("age", Integer.class);
    public static final FieldExpression<Date> enrollment = student.fieldOf("enrollment", Date.class);
    public static final FieldExpression<Date> birthday = student.fieldOf("birthday", Date.class);
    public static final FieldExpression<Long> teacherId = student.fieldOf("teacherId", Long.class);
    public static final FieldExpression<Long> courseId = student.fieldOf("courseId", Long.class);
    public static final FieldExpression<String> creator = student.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = student.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = student.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = student.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> version = student.fieldOf("version", Integer.class);
    public static final FieldExpression<String> address = student.fieldOf("address", String.class);
    public static final FieldExpression<Boolean> destroyed = student.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> userAttribute = student.fieldOf("userAttribute", String.class);


    public QStudent() {
        super("Student", Student.class);
    }

    QStudent(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Student", Student.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
