package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.SysRole;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import com.share.auth.center.model.entity.SysApplication;
import com.share.auth.center.model.entity.UemCompanyRole;
import com.share.auth.center.model.querymodels.QSysApplication;
import com.share.auth.center.model.querymodels.QUemCompanyRole;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysRole extends BaseModelExpression<SysRole, Long> {

    public static final BaseModelExpression<SysRole, Long> sysRole = new QSysRole();
    public static final FieldExpression<Date> createTime = sysRole.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysRole.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysRole.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = sysRole.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isDefault = sysRole.fieldOf("isDefault", Boolean.class);
    public static final FieldExpression<Boolean> isValid = sysRole.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysRole.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysRole.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysRole.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> priority = sysRole.fieldOf("priority", Integer.class);
    public static final FieldExpression<Integer> recordVersion = sysRole.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> remark = sysRole.fieldOf("remark", String.class);
    public static final FieldExpression<String> roleCode = sysRole.fieldOf("roleCode", String.class);
    public static final FieldExpression<String> roleName = sysRole.fieldOf("roleName", String.class);
    public static final FieldExpression<Long> rolePid = sysRole.fieldOf("rolePid", Long.class);
    public static final FieldExpression<Long> sysApplicationId = sysRole.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<Long> sysRoleId = sysRole.fieldOf("sysRoleId", Long.class);
    public static final FieldExpression<Long> topRoleId = sysRole.fieldOf("topRoleId", Long.class);

    public static final BaseModelExpression<UemCompanyRole, Long> uemCompanyRole = new QUemCompanyRole(sysRole, "uemCompanyRole");
    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication(sysRole, "sysApplication");

    public QSysRole() {
        super("SysRole", SysRole.class);
    }

    QSysRole(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysRole", SysRole.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysRoleId;
    }
}
