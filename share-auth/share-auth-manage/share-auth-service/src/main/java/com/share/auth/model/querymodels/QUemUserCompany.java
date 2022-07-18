package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.entity.UemUserCompany;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUserCompany extends BaseModelExpression<UemUserCompany, Long> {

    public static final BaseModelExpression<UemUserCompany, Long> uemUserCompany = new QUemUserCompany();
    public static final FieldExpression<String> auditStatus = uemUserCompany.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> createTime = uemUserCompany.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUserCompany.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUserCompany.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> entryTime = uemUserCompany.fieldOf("entryTime", Date.class);
    public static final FieldExpression<Long> modifierId = uemUserCompany.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUserCompany.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUserCompany.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Date> quitTime = uemUserCompany.fieldOf("quitTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemUserCompany.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemCompanyId = uemUserCompany.fieldOf("uemCompanyId", Long.class);
    public static final FieldExpression<Long> uemUserCompanyId = uemUserCompany.fieldOf("uemUserCompanyId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUserCompany.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<Boolean> userRole = uemUserCompany.fieldOf("userRole", Boolean.class);

    public static final BaseModelExpression<UemUser, Long> uemUser = new QUemUser(uemUserCompany, "uemUser");

    public QUemUserCompany() {
        super("UemUserCompany", UemUserCompany.class);
    }

    QUemUserCompany(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUserCompany", UemUserCompany.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserCompanyId;
    }
}
