package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysPlatformUser;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysPlatformUser extends BaseModelExpression<SysPlatformUser, Long> {

    public static final BaseModelExpression<SysPlatformUser, Long> sysPlatformUser = new QSysPlatformUser();
    public static final FieldExpression<String> account = sysPlatformUser.fieldOf("account", String.class);
    public static final FieldExpression<Date> createTime = sysPlatformUser.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysPlatformUser.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysPlatformUser.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> invalidTime = sysPlatformUser.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isLocked = sysPlatformUser.fieldOf("isLocked", Boolean.class);
    public static final FieldExpression<Boolean> isValid = sysPlatformUser.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> mail = sysPlatformUser.fieldOf("mail", String.class);
    public static final FieldExpression<Long> modifierId = sysPlatformUser.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysPlatformUser.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysPlatformUser.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> name = sysPlatformUser.fieldOf("name", String.class);
    public static final FieldExpression<String> password = sysPlatformUser.fieldOf("password", String.class);
    public static final FieldExpression<Integer> recordVersion = sysPlatformUser.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> sysPlatformUserId = sysPlatformUser.fieldOf("sysPlatformUserId", Long.class);
    public static final FieldExpression<String> tel = sysPlatformUser.fieldOf("tel", String.class);


    public QSysPlatformUser() {
        super("SysPlatformUser", SysPlatformUser.class);
    }

    QSysPlatformUser(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysPlatformUser", SysPlatformUser.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysPlatformUserId;
    }
}
