package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Teacher;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QTeacher extends BaseModelExpression<Teacher, Long> {

    public static final BaseModelExpression<Teacher, Long> teacher = new QTeacher();
    public static final FieldExpression<Long> id = teacher.fieldOf("id", Long.class);
    public static final FieldExpression<String> teacherName = teacher.fieldOf("teacherName", String.class);
    public static final FieldExpression<Integer> teacherAge = teacher.fieldOf("teacherAge", Integer.class);
    public static final FieldExpression<Long> studentId = teacher.fieldOf("studentId", Long.class);
    public static final FieldExpression<String> creator = teacher.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = teacher.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = teacher.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = teacher.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> version = teacher.fieldOf("version", Integer.class);
    public static final FieldExpression<Boolean> destroyed = teacher.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> userAttribute = teacher.fieldOf("userAttribute", String.class);


    public QTeacher() {
        super("Teacher", Teacher.class);
    }

    QTeacher(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Teacher", Teacher.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
