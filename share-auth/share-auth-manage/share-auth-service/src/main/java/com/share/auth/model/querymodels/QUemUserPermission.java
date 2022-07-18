package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemUserPermission;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.QSysApplication;
import com.share.auth.model.querymodels.QSysPlatformUser;
import com.share.auth.model.querymodels.QUemUser;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUserPermission extends BaseModelExpression<UemUserPermission, Long> {

    public static final BaseModelExpression<UemUserPermission, Long> uemUserPermission = new QUemUserPermission();
    public static final FieldExpression<String> applyReason = uemUserPermission.fieldOf("applyReason", String.class);
    public static final FieldExpression<Long> auditor = uemUserPermission.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemUserPermission.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemUserPermission.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemUserPermission.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Date> createTime = uemUserPermission.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUserPermission.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUserPermission.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = uemUserPermission.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUserPermission.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUserPermission.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemUserPermission.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysApplicationId = uemUserPermission.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUserPermission.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<Long> uemUserPermissionId = uemUserPermission.fieldOf("uemUserPermissionId", Long.class);

    public static final BaseModelExpression<SysPlatformUser, Long> sysPlatformUser = new QSysPlatformUser(uemUserPermission, "sysPlatformUser");
    public static final BaseModelExpression<UemUser, Long> uemUser = new QUemUser(uemUserPermission, "uemUser");
    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication(uemUserPermission, "sysApplication");
    public static final BaseModelExpression<UemUser, Long> userAuditor = new QUemUser(uemUserPermission, "userAuditor");

    public QUemUserPermission() {
        super("UemUserPermission", UemUserPermission.class);
    }

    QUemUserPermission(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUserPermission", UemUserPermission.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserPermissionId;
    }
}
