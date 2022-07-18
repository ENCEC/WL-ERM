package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Course;

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
public class QCourse extends BaseModelExpression<Course, Long> {

    public static final BaseModelExpression<Course, Long> course = new QCourse();
    public static final FieldExpression<Boolean> destroyed = course.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> creator = course.fieldOf("creator", String.class);
    public static final FieldExpression<String> courseName = course.fieldOf("courseName", String.class);
    public static final FieldExpression<Date> modifyTime = course.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Date> createTime = course.fieldOf("createTime", Date.class);
    public static final FieldExpression<Integer> courseNum = course.fieldOf("courseNum", Integer.class);
    public static final FieldExpression<String> userAttribute = course.fieldOf("userAttribute", String.class);
    public static final FieldExpression<String> modifier = course.fieldOf("modifier", String.class);
    public static final FieldExpression<Long> id = course.fieldOf("id", Long.class);
    public static final FieldExpression<Integer> version = course.fieldOf("version", Integer.class);
    public static final FieldExpression<String> courseContent = course.fieldOf("courseContent", String.class);


    public QCourse() {
        super("Course", Course.class);
    }

    QCourse(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Course", Course.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
