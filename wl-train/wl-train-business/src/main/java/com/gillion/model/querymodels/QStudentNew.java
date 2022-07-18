package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.StudentNew;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QStudentNew extends BaseModelExpression<StudentNew, Long> {

    public static final BaseModelExpression<StudentNew, Long> studentNew = new QStudentNew();
    public static final FieldExpression<Long> id = studentNew.fieldOf("id", Long.class);
    public static final FieldExpression<String> name = studentNew.fieldOf("name", String.class);
    public static final FieldExpression<String> password = studentNew.fieldOf("password", String.class);
    public static final FieldExpression<String> confirmPassword = studentNew.fieldOf("confirmPassword", String.class);
    public static final FieldExpression<String> sex = studentNew.fieldOf("sex", String.class);
    public static final FieldExpression<String> email = studentNew.fieldOf("email", String.class);
    public static final FieldExpression<String> mobile = studentNew.fieldOf("mobile", String.class);
    public static final FieldExpression<Long> money = studentNew.fieldOf("money", Long.class);
    public static final FieldExpression<Integer> score = studentNew.fieldOf("score", Integer.class);
    public static final FieldExpression<Integer> age = studentNew.fieldOf("age", Integer.class);
    public static final FieldExpression<Date> enrollment = studentNew.fieldOf("enrollment", Date.class);
    public static final FieldExpression<Date> birthday = studentNew.fieldOf("birthday", Date.class);
    public static final FieldExpression<Long> teacherId = studentNew.fieldOf("teacherId", Long.class);
    public static final FieldExpression<Long> courseId = studentNew.fieldOf("courseId", Long.class);
    public static final FieldExpression<String> creator = studentNew.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = studentNew.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = studentNew.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = studentNew.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> version = studentNew.fieldOf("version", Integer.class);
    public static final FieldExpression<String> address = studentNew.fieldOf("address", String.class);


    public QStudentNew() {
        super("StudentNew", StudentNew.class);
    }

    QStudentNew(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "StudentNew", StudentNew.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
