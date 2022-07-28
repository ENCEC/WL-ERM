package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemCompanyManager;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemCompanyManager extends BaseModelExpression<UemCompanyManager, Long> {

    public static final BaseModelExpression<UemCompanyManager, Long> uemCompanyManager = new QUemCompanyManager();
    public static final FieldExpression<Long> auditor = uemCompanyManager.fieldOf("auditor", Long.class);
    public static final FieldExpression<String> auditRemark = uemCompanyManager.fieldOf("auditRemark", String.class);
    public static final FieldExpression<String> auditStatus = uemCompanyManager.fieldOf("auditStatus", String.class);
    public static final FieldExpression<Date> auditTime = uemCompanyManager.fieldOf("auditTime", Date.class);
    public static final FieldExpression<Date> createTime = uemCompanyManager.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompanyManager.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompanyManager.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> efileDate = uemCompanyManager.fieldOf("efileDate", Date.class);
    public static final FieldExpression<String> fileUrlId = uemCompanyManager.fieldOf("fileUrlId", String.class);
    public static final FieldExpression<Long> modifierId = uemCompanyManager.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompanyManager.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompanyManager.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemCompanyManager.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompanyManager.fieldOf("uemCompanyId", Long.class);
    public static final FieldExpression<Long> uemCompanyManagerId = uemCompanyManager.fieldOf("uemCompanyManagerId", Long.class);
    public static final FieldExpression<Long> uemUserId = uemCompanyManager.fieldOf("uemUserId", Long.class);


    public QUemCompanyManager() {
        super("UemCompanyManager", UemCompanyManager.class);
    }

    QUemCompanyManager(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompanyManager", UemCompanyManager.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyManagerId;
    }
}
