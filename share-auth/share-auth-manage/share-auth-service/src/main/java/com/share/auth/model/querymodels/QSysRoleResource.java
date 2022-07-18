package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysResource;
import com.share.auth.model.entity.SysRoleResource;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysRoleResource extends BaseModelExpression<SysRoleResource, Long> {

    public static final BaseModelExpression<SysRoleResource, Long> sysRoleResource = new QSysRoleResource();
    public static final FieldExpression<Date> createTime = sysRoleResource.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysRoleResource.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysRoleResource.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = sysRoleResource.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysRoleResource.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysRoleResource.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysRoleResource.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysResourceId = sysRoleResource.fieldOf("sysResourceId", Long.class);
    public static final FieldExpression<Long> sysRoleId = sysRoleResource.fieldOf("sysRoleId", Long.class);
    public static final FieldExpression<Long> sysRoleResourceId = sysRoleResource.fieldOf("sysRoleResourceId", Long.class);

    public static final BaseModelExpression<SysResource, Long> sysResource = new QSysResource(sysRoleResource, "sysResource");

    public QSysRoleResource() {
        super("SysRoleResource", SysRoleResource.class);
    }

    QSysRoleResource(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysRoleResource", SysRoleResource.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysRoleResourceId;
    }
}
