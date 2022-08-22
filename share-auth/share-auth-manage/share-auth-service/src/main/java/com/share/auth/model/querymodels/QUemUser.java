package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemUser;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemUser extends BaseModelExpression<UemUser, Long> {

    public static final BaseModelExpression<UemUser, Long> uemUser = new QUemUser();
    public static final FieldExpression<String> account = uemUser.fieldOf("account", String.class);
    public static final FieldExpression<String> address = uemUser.fieldOf("address", String.class);
    public static final FieldExpression<Long> auditor = uemUser.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemUser.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemUser.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemUser.fieldOf("auditTime", Date.class);
    public static final FieldExpression<String> birthday = uemUser.fieldOf("birthday", String.class);
    public static final FieldExpression<Long> blindCompanny = uemUser.fieldOf("blindCompanny", Long.class);
    public static final FieldExpression<String> cardBackUrlId = uemUser.fieldOf("cardBackUrlId", String.class);
    public static final FieldExpression<String> cardPositiveUrlId = uemUser.fieldOf("cardPositiveUrlId", String.class);
    public static final FieldExpression<Date> createTime = uemUser.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemUser.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemUser.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> defenseScore = uemUser.fieldOf("defenseScore", Long.class);
    public static final FieldExpression<String> deptCode = uemUser.fieldOf("deptCode", String.class);
    public static final FieldExpression<String> deptName = uemUser.fieldOf("deptName", String.class);
    public static final FieldExpression<Date> dismissDate = uemUser.fieldOf("dismissDate", Date.class);
    public static final FieldExpression<String> dismissReason = uemUser.fieldOf("dismissReason", String.class);
    public static final FieldExpression<Long> education = uemUser.fieldOf("education", Long.class);
    public static final FieldExpression<String> email = uemUser.fieldOf("email", String.class);
    public static final FieldExpression<Date> entryDate = uemUser.fieldOf("entryDate", Date.class);
    public static final FieldExpression<Date> graduateDate = uemUser.fieldOf("graduateDate", Date.class);
    public static final FieldExpression<String> graduateSchool = uemUser.fieldOf("graduateSchool", String.class);
    public static final FieldExpression<String> idCard = uemUser.fieldOf("idCard", String.class);
    public static final FieldExpression<String> interviewComments = uemUser.fieldOf("interviewComments", String.class);
    public static final FieldExpression<Date> invalidTime = uemUser.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isAgreemeent = uemUser.fieldOf("isAgreemeent", Boolean.class);
    public static final FieldExpression<Boolean> isDeleted = uemUser.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Boolean> isDisplayed = uemUser.fieldOf("isDisplayed", Boolean.class);
    public static final FieldExpression<Boolean> isLocked = uemUser.fieldOf("isLocked", Boolean.class);
    public static final FieldExpression<Boolean> isValid = uemUser.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> jobStatus = uemUser.fieldOf("jobStatus", Long.class);
    public static final FieldExpression<Date> leaveDate = uemUser.fieldOf("leaveDate", Date.class);
    public static final FieldExpression<String> leaveReason = uemUser.fieldOf("leaveReason", String.class);
    public static final FieldExpression<Long> maritalStatus = uemUser.fieldOf("maritalStatus", Long.class);
    public static final FieldExpression<String> mobile = uemUser.fieldOf("mobile", String.class);
    public static final FieldExpression<Long> modifierId = uemUser.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemUser.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemUser.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> name = uemUser.fieldOf("name", String.class);
    public static final FieldExpression<Date> offerDate = uemUser.fieldOf("offerDate", Date.class);
    public static final FieldExpression<String> offerReason = uemUser.fieldOf("offerReason", String.class);
    public static final FieldExpression<Long> oriApplication = uemUser.fieldOf("oriApplication", Long.class);
    public static final FieldExpression<String> password = uemUser.fieldOf("password", String.class);
    public static final FieldExpression<String> politicalStatus = uemUser.fieldOf("politicalStatus", String.class);
    public static final FieldExpression<String> positiveComments = uemUser.fieldOf("positiveComments", String.class);
    public static final FieldExpression<Long> positiveType = uemUser.fieldOf("positiveType", Long.class);
    public static final FieldExpression<Long> projectId = uemUser.fieldOf("projectId", Long.class);
    public static final FieldExpression<String> projectName = uemUser.fieldOf("projectName", String.class);
    public static final FieldExpression<String> qqId = uemUser.fieldOf("qqId", String.class);
    public static final FieldExpression<Integer> recordVersion = uemUser.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> resume = uemUser.fieldOf("resume", String.class);
    public static final FieldExpression<Integer> score = uemUser.fieldOf("score", Integer.class);
    public static final FieldExpression<BigDecimal> seniority = uemUser.fieldOf("seniority", BigDecimal.class);
    public static final FieldExpression<String> seqNo = uemUser.fieldOf("seqNo", String.class);
    public static final FieldExpression<Boolean> sex = uemUser.fieldOf("sex", Boolean.class);
    public static final FieldExpression<String> source = uemUser.fieldOf("source", String.class);
    public static final FieldExpression<String> sourceAddress = uemUser.fieldOf("sourceAddress", String.class);
    public static final FieldExpression<String> speciality = uemUser.fieldOf("speciality", String.class);
    public static final FieldExpression<String> staffApplication = uemUser.fieldOf("staffApplication", String.class);
    public static final FieldExpression<String> dismissApplication = uemUser.fieldOf("dismissApplication", String.class);
    public static final FieldExpression<String> staffDuty = uemUser.fieldOf("staffDuty", String.class);
    public static final FieldExpression<String> staffDutyCode = uemUser.fieldOf("staffDutyCode", String.class);
    public static final FieldExpression<Long> staffDutyId = uemUser.fieldOf("staffDutyId", Long.class);
    public static final FieldExpression<String> staffLevel = uemUser.fieldOf("staffLevel", String.class);
    public static final FieldExpression<String> technicalName = uemUser.fieldOf("technicalName", String.class);
    public static final FieldExpression<Long> technicalTitleId = uemUser.fieldOf("technicalTitleId", Long.class);
    public static final FieldExpression<String> telephone = uemUser.fieldOf("telephone", String.class);
    public static final FieldExpression<Long> uemDeptId = uemUser.fieldOf("uemDeptId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemUser.fieldOf("uemUserId", Long.class);
    public static final FieldExpression<String> userType = uemUser.fieldOf("userType", String.class);
    public static final FieldExpression<String> wxId = uemUser.fieldOf("wxId", String.class);


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
