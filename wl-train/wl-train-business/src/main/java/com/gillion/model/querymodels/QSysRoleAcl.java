package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.SysRoleAcl;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysRoleAcl extends BaseModelExpression<SysRoleAcl, Integer> {

    public static final BaseModelExpression<SysRoleAcl, Integer> sysRoleAcl = new QSysRoleAcl();
    public static final FieldExpression<Integer> aclTableId = sysRoleAcl.fieldOf("aclTableId", Integer.class);
    public static final FieldExpression<String> conditions = sysRoleAcl.fieldOf("conditions", String.class);
    public static final FieldExpression<String> conditionsSql = sysRoleAcl.fieldOf("conditionsSql", String.class);
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
