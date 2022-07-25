package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemDept;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemDept extends BaseModelExpression<UemDept, Long> {

    public static final BaseModelExpression<UemDept, Long> uemDept = new QUemDept();
    public static final FieldExpression<String> companyNameCn = uemDept.fieldOf("companyNameCn", String.class);
    public static final FieldExpression<Date> createTime = uemDept.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemDept.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemDept.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> deptCode = uemDept.fieldOf("deptCode", String.class);
    public static final FieldExpression<String> deptName = uemDept.fieldOf("deptName", String.class);
    public static final FieldExpression<String> managerAccount = uemDept.fieldOf("managerAccount", String.class);
    public static final FieldExpression<String> managerName = uemDept.fieldOf("managerName", String.class);
    public static final FieldExpression<Long> managerUemUserId = uemDept.fieldOf("managerUemUserId", Long.class);
    public static final FieldExpression<Long> modifierId = uemDept.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemDept.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemDept.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> parentDeptCode = uemDept.fieldOf("parentDeptCode", String.class);
    public static final FieldExpression<Long> parentDeptId = uemDept.fieldOf("parentDeptId", Long.class);
    public static final FieldExpression<String> parentDeptName = uemDept.fieldOf("parentDeptName", String.class);
    public static final FieldExpression<Integer> recordVersion = uemDept.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemCompanyId = uemDept.fieldOf("uemCompanyId", Long.class);
    public static final FieldExpression<Long> uemDeptId = uemDept.fieldOf("uemDeptId", Long.class);


    public QUemDept() {
        super("UemDept", UemDept.class);
    }

    QUemDept(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemDept", UemDept.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemDeptId;
    }
}
