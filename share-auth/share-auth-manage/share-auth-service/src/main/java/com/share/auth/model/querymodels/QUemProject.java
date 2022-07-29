package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemProject;
import com.share.auth.model.entity.UemUser;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemProject extends BaseModelExpression<UemProject, Long> {

    public static final BaseModelExpression<UemProject, Long> uemProject = new QUemProject();
    public static final FieldExpression<Date> actualEndTime = uemProject.fieldOf("actualEndTime", Date.class);
    public static final FieldExpression<Date> actualStartTime = uemProject.fieldOf("actualStartTime", Date.class);
    public static final FieldExpression<Long> chiefId = uemProject.fieldOf("chiefId", Long.class);
    public static final FieldExpression<String> chiefName = uemProject.fieldOf("chiefName", String.class);
    public static final FieldExpression<Date> createTime = uemProject.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemProject.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemProject.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> customer = uemProject.fieldOf("customer", String.class);
    public static final FieldExpression<Long> demandId = uemProject.fieldOf("demandId", Long.class);
    public static final FieldExpression<String> demandName = uemProject.fieldOf("demandName", String.class);
    public static final FieldExpression<Long> devDirectorId = uemProject.fieldOf("devDirectorId", Long.class);
    public static final FieldExpression<String> devDirectorName = uemProject.fieldOf("devDirectorName", String.class);
    public static final FieldExpression<Long> dutyId = uemProject.fieldOf("dutyId", Long.class);
    public static final FieldExpression<String> dutyName = uemProject.fieldOf("dutyName", String.class);
    public static final FieldExpression<Integer> fcy = uemProject.fieldOf("fcy", Integer.class);
    public static final FieldExpression<String> genDemandUsers = uemProject.fieldOf("genDemandUsers", String.class);
    public static final FieldExpression<String> genDevUsers = uemProject.fieldOf("genDevUsers", String.class);
    public static final FieldExpression<Long> modifierId = uemProject.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemProject.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemProject.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Date> planEndTime = uemProject.fieldOf("planEndTime", Date.class);
    public static final FieldExpression<Date> planStartTime = uemProject.fieldOf("planStartTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemProject.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> status = uemProject.fieldOf("status", Integer.class);
    public static final FieldExpression<Integer> totalNum = uemProject.fieldOf("totalNum", Integer.class);
    public static final FieldExpression<Long> uemDeptId = uemProject.fieldOf("uemDeptId", Long.class);
    public static final FieldExpression<Long> uemProjectId = uemProject.fieldOf("uemProjectId", Long.class);

    public static final BaseModelExpression<UemUser, Long> uemUser = new QUemUser(uemProject, "uemUser");

    public QUemProject() {
        super("UemProject", UemProject.class);
    }

    QUemProject(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemProject", UemProject.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemProjectId;
    }
}
