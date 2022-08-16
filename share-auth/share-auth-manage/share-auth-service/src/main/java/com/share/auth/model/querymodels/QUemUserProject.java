package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemUserProject;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUserProject extends BaseModelExpression<UemUserProject, Long> {

    public static final BaseModelExpression<UemUserProject, Long> uemUserProject = new QUemUserProject();
    public static final FieldExpression<Date> createTime = uemUserProject.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUserProject.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUserProject.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = uemUserProject.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUserProject.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUserProject.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemUserProject.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemProjectId = uemUserProject.fieldOf("uemProjectId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUserProject.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<Long> uemUserProjectId = uemUserProject.fieldOf("uemUserProjectId", Long.class);


    public QUemUserProject() {
        super("UemUserProject", UemUserProject.class);
    }

    QUemUserProject(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUserProject", UemUserProject.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserProjectId;
    }
}
