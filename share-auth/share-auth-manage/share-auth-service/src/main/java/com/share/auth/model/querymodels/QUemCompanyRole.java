package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemCompanyRole;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.SysRole;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QSysRole;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemCompanyRole extends BaseModelExpression<UemCompanyRole, Long> {

    public static final BaseModelExpression<UemCompanyRole, Long> uemCompanyRole = new QUemCompanyRole();
    public static final FieldExpression<Date> createTime = uemCompanyRole.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompanyRole.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompanyRole.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDefault = uemCompanyRole.fieldOf("isDefault", Boolean.class);
    public static final FieldExpression<Long> modifierId = uemCompanyRole.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompanyRole.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompanyRole.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemCompanyRole.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysApplicationId = uemCompanyRole.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<Long> sysRoleId = uemCompanyRole.fieldOf("sysRoleId", Long.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompanyRole.fieldOf("uemCompanyId", Long.class);
    public static final FieldExpression<Long> uemCompanyRoleId = uemCompanyRole.fieldOf("uemCompanyRoleId", Long.class);

    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication(uemCompanyRole, "sysApplication");
    public static final BaseModelExpression<SysRole, Long> sysRole = new QSysRole(uemCompanyRole, "sysRole");

    public QUemCompanyRole() {
        super("UemCompanyRole", UemCompanyRole.class);
    }

    QUemCompanyRole(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompanyRole", UemCompanyRole.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyRoleId;
    }
}
