package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.Children;

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
public class QChildren extends BaseModelExpression<Children, Long> {

    public static final BaseModelExpression<Children, Long> children = new QChildren();
    public static final FieldExpression<Long> id = children.fieldOf("id", Long.class);
    public static final FieldExpression<String> childName = children.fieldOf("childName", String.class);
    public static final FieldExpression<Boolean> sex = children.fieldOf("sex", Boolean.class);
    public static final FieldExpression<Long> giftId = children.fieldOf("giftId", Long.class);
    public static final FieldExpression<String> giftCode = children.fieldOf("giftCode", String.class);
    public static final FieldExpression<Integer> version = children.fieldOf("version", Integer.class);
    public static final FieldExpression<Boolean> destroyed = children.fieldOf("destroyed", Boolean.class);
    public static final FieldExpression<String> creator = children.fieldOf("creator", String.class);
    public static final FieldExpression<Date> createTime = children.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> modifier = children.fieldOf("modifier", String.class);
    public static final FieldExpression<Date> modifyTime = children.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> userAttribute = children.fieldOf("userAttribute", String.class);
    public static final FieldExpression<String> mobile = children.fieldOf("mobile", String.class);
    public static final FieldExpression<String> parentsMobile = children.fieldOf("parentsMobile", String.class);
    public static final FieldExpression<String> phone = children.fieldOf("phone", String.class);
    public static final FieldExpression<String> mailbox = children.fieldOf("mailbox", String.class);
    public static final FieldExpression<Integer> age = children.fieldOf("age", Integer.class);
    public static final FieldExpression<Integer> schoolAge = children.fieldOf("schoolAge", Integer.class);
    public static final FieldExpression<String> englishName = children.fieldOf("englishName", String.class);
    public static final FieldExpression<Date> enrollmentDate = children.fieldOf("enrollmentDate", Date.class);
    public static final FieldExpression<Date> graduationDate = children.fieldOf("graduationDate", Date.class);


    public QChildren() {
        super("Children", Children.class);
    }

    QChildren(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "Children", Children.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
