package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysRoleAcl;

import java.lang.Byte;
import java.lang.Integer;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysRoleAcl extends BaseModelExpression<SysRoleAcl, Integer> {

    public static final BaseModelExpression<SysRoleAcl, Integer> sysRoleAcl = new QSysRoleAcl();
    public static final FieldExpression<Integer> aclTableId = sysRoleAcl.fieldOf("aclTableId", Integer.class);
    public static final FieldExpression<String> condition = sysRoleAcl.fieldOf("condition", String.class);
    public static final FieldExpression<String> conditionSql = sysRoleAcl.fieldOf("conditionSql", String.class);
    public static final FieldExpression<Byte> crudType = sysRoleAcl.fieldOf("crudType", Byte.class);
    public static final FieldExpression<Integer> roleAclId = sysRoleAcl.fieldOf("roleAclId", Integer.class);
    public static final FieldExpression<String> roleId = sysRoleAcl.fieldOf("roleId", String.class);


    public QSysRoleAcl() {
        super("SysRoleAcl", SysRoleAcl.class);
    }

    QSysRoleAcl(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysRoleAcl", SysRoleAcl.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return roleAclId;
    }
}