package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemUserUat;

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
public class QUemUserUat extends BaseModelExpression<UemUserUat, Long> {

    public static final BaseModelExpression<UemUserUat, Long> uemUserUat = new QUemUserUat();
    public static final FieldExpression<String> account = uemUserUat.fieldOf("account", String.class);
    public static final FieldExpression<Long> auditor = uemUserUat.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemUserUat.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemUserUat.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemUserUat.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Long> blindCompanny = uemUserUat.fieldOf("blindCompanny", Long.class);
    public static final FieldExpression<Date> blindCompannyTime = uemUserUat.fieldOf("blindCompannyTime", Date.class);
    public static final FieldExpression<String> cardBackUrlId = uemUserUat.fieldOf("cardBackUrlId", String.class);
    public static final FieldExpression<String> cardPositiveUrlId = uemUserUat.fieldOf("cardPositiveUrlId", String.class);
    public static final FieldExpression<Date> createTime = uemUserUat.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUserUat.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUserUat.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> email = uemUserUat.fieldOf("email", String.class);
    public static final FieldExpression<String> gvmId = uemUserUat.fieldOf("gvmId", String.class);
    public static final FieldExpression<String> idCard = uemUserUat.fieldOf("idCard", String.class);
    public static final FieldExpression<Date> invalidTime = uemUserUat.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isAgreemeent = uemUserUat.fieldOf("isAgreemeent", Boolean.class);
    public static final FieldExpression<Boolean> isDisplayed = uemUserUat.fieldOf("isDisplayed", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemUserUat.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<String> mobile = uemUserUat.fieldOf("mobile", String.class);
    public static final FieldExpression<Long> modifierId = uemUserUat.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUserUat.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUserUat.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> name = uemUserUat.fieldOf("name", String.class);
    public static final FieldExpression<String> orgCode = uemUserUat.fieldOf("orgCode", String.class);
    public static final FieldExpression<Long> oriApplication = uemUserUat.fieldOf("oriApplication", Long.class);
    public static final FieldExpression<String> password = uemUserUat.fieldOf("password", String.class);
    public static final FieldExpression<String> qqId = uemUserUat.fieldOf("qqId", String.class);
    public static final FieldExpression<Integer> recordVersion = uemUserUat.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Integer> score = uemUserUat.fieldOf("score", Integer.class);
    public static final FieldExpression<String> seqNo = uemUserUat.fieldOf("seqNo", String.class);
    public static final FieldExpression<Boolean> sex = uemUserUat.fieldOf("sex", Boolean.class);
    public static final FieldExpression<String> source = uemUserUat.fieldOf("source", String.class);
    public static final FieldExpression<String> staffCode = uemUserUat.fieldOf("staffCode", String.class);
    public static final FieldExpression<String> staffDuty = uemUserUat.fieldOf("staffDuty", String.class);
    public static final FieldExpression<String> staffDutyCode = uemUserUat.fieldOf("staffDutyCode", String.class);
    public static final FieldExpression<String> staffLevel = uemUserUat.fieldOf("staffLevel", String.class);
    public static final FieldExpression<String> telephone = uemUserUat.fieldOf("telephone", String.class);
    public static final FieldExpression<Long> uemIdCardId = uemUserUat.fieldOf("uemIdCardId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUserUat.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<String> userType = uemUserUat.fieldOf("userType", String.class);
    public static final FieldExpression<String> wxId = uemUserUat.fieldOf("wxId", String.class);


    public QUemUserUat() {
        super("UemUserUat", UemUserUat.class);
    }

    QUemUserUat(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemUserUat", UemUserUat.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemUserId;
    }
}
