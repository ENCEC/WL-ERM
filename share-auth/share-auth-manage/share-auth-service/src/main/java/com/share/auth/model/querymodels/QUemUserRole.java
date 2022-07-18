package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.SysRole;
import com.share.auth.model.entity.UemUserRole;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUserRole extends BaseModelExpression<UemUserRole, Long> {

    public static final BaseModelExpression<UemUserRole, Long> uemUserRole = new QUemUserRole();
    public static final FieldExpression<Date> createTime = uemUserRole.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUserRole.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUserRole.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = uemUserRole.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isValid = uemUserRole.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = uemUserRole.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUserRole.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUserRole.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemUserRole.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysApplicationId = uemUserRole.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<Long> sysRoleId = uemUserRole.fieldOf("sysRoleId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUserRole.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<Long> uemUserRoleId = uemUserRole.fieldOf("uemUserRoleId", Long.class);

    public static final BaseModelExpression<SysRole, Long> sysRole = new QSysRole(uemUserRole, "sysRole");
    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication(uemUserRole, "sysApplication");

    public QUemUserRole() {
        super("UemUserRole", UemUserRole.class);
    }

    QUemUserRole(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUserRole", UemUserRole.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserRoleId;
    }
}
