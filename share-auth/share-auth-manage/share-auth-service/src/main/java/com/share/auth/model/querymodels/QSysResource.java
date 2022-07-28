package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysResource;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysResource extends BaseModelExpression<SysResource, Long> {

    public static final BaseModelExpression<SysResource, Long> sysResource = new QSysResource();
    public static final FieldExpression<String> component = sysResource.fieldOf("component", String.class);
    public static final FieldExpression<String> componentName = sysResource.fieldOf("componentName", String.class);
    public static final FieldExpression<Date> createTime = sysResource.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysResource.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysResource.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = sysResource.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isDeleted = sysResource.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isValid = sysResource.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysResource.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysResource.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysResource.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = sysResource.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> resourceLogo = sysResource.fieldOf("resourceLogo", String.class);
    public static final FieldExpression<Long> resourcePid = sysResource.fieldOf("resourcePid", Long.class);
    public static final FieldExpression<String> resourceRemark = sysResource.fieldOf("resourceRemark", String.class);
    public static final FieldExpression<Integer> resourceSort = sysResource.fieldOf("resourceSort", Integer.class);
    public static final FieldExpression<String> resourceTitle = sysResource.fieldOf("resourceTitle", String.class);
    public static final FieldExpression<Integer> resourceType = sysResource.fieldOf("resourceType", Integer.class);
    public static final FieldExpression<String> resourceUrl = sysResource.fieldOf("resourceUrl", String.class);
    public static final FieldExpression<Long> sysApplicationId = sysResource.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<Long> sysResourceId = sysResource.fieldOf("sysResourceId", Long.class);


    public QSysResource() {
        super("SysResource", SysResource.class);
    }

    QSysResource(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysResource", SysResource.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysResourceId;
    }
}
