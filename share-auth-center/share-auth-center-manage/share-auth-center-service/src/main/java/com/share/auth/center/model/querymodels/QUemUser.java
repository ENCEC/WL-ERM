package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.UemUser;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import com.share.auth.center.model.entity.SysApplication;
import com.share.auth.center.model.entity.UemCompany;
import com.share.auth.center.model.entity.UemLog;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUser extends BaseModelExpression<UemUser, Long> {

    public static final BaseModelExpression<UemUser, Long> uemUser = new QUemUser();
    public static final FieldExpression<String> account = uemUser.fieldOf("account", String.class);
    public static final FieldExpression<Long> auditor = uemUser.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemUser.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemUser.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemUser.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Long> blindCompanny = uemUser.fieldOf("blindCompanny", Long.class);
    public static final FieldExpression<Date> blindCompannyTime = uemUser.fieldOf("blindCompannyTime", Date.class);
    public static final FieldExpression<String> cardBackUrlId = uemUser.fieldOf("cardBackUrlId", String.class);
    public static final FieldExpression<String> cardPositiveUrlId = uemUser.fieldOf("cardPositiveUrlId", String.class);
    public static final FieldExpression<Date> createTime = uemUser.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUser.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUser.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> email = uemUser.fieldOf("email", String.class);
    public static final FieldExpression<String> idCard = uemUser.fieldOf("idCard", String.class);
    public static final FieldExpression<Date> invalidTime = uemUser.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isAgreemeent = uemUser.fieldOf("isAgreemeent", Boolean.class);
    public static final FieldExpression<Boolean> isDisplayed = uemUser.fieldOf("isDisplayed", Boolean.class);
    public static final FieldExpression<Boolean> isLocked = uemUser.fieldOf("isLocked", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemUser.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> mobile = uemUser.fieldOf("mobile", String.class);
    public static final FieldExpression<Long> modifierId = uemUser.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUser.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUser.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> name = uemUser.fieldOf("name", String.class);
    public static final FieldExpression<String> orgCode = uemUser.fieldOf("orgCode", String.class);
    public static final FieldExpression<Long> oriApplication = uemUser.fieldOf("oriApplication", Long.class);
    public static final FieldExpression<String> password = uemUser.fieldOf("password", String.class);
    public static final FieldExpression<String> qqId = uemUser.fieldOf("qqId", String.class);
    public static final FieldExpression<Integer> recordVersion = uemUser.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> score = uemUser.fieldOf("score", Integer.class);
    public static final FieldExpression<String> seqNo = uemUser.fieldOf("seqNo", String.class);
    public static final FieldExpression<Boolean> sex = uemUser.fieldOf("sex", Boolean.class);
    public static final FieldExpression<String> source = uemUser.fieldOf("source", String.class);
    public static final FieldExpression<String> staffDuty = uemUser.fieldOf("staffDuty", String.class);
    public static final FieldExpression<String> staffDutyCode = uemUser.fieldOf("staffDutyCode", String.class);
    public static final FieldExpression<String> staffLevel = uemUser.fieldOf("staffLevel", String.class);
    public static final FieldExpression<String> telephone = uemUser.fieldOf("telephone", String.class);
    public static final FieldExpression<Long> uemIdCardId = uemUser.fieldOf("uemIdCardId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUser.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<String> userType = uemUser.fieldOf("userType", String.class);
    public static final FieldExpression<String> wxId = uemUser.fieldOf("wxId", String.class);

    public static final BaseModelExpression<SysApplication, Long> sysApplication = new QSysApplication(uemUser, "sysApplication");
    public static final BaseModelExpression<UemLog, Long> uemLog = new QUemLog(uemUser, "uemLog");
    public static final BaseModelExpression<UemCompany, Long> uemCompany = new QUemCompany(uemUser, "uemCompany");

    public QUemUser() {
        super("UemUser", UemUser.class);
    }

    QUemUser(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUser", UemUser.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserId;
    }
}
