package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.SysDictCode;
import com.share.auth.center.model.entity.UemCustomerType;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemCustomerType extends BaseModelExpression<UemCustomerType, Long> {

    public static final BaseModelExpression<UemCustomerType, Long> uemCustomerType = new QUemCustomerType();
    public static final FieldExpression<String> companyTypeCode = uemCustomerType.fieldOf("companyTypeCode", String.class);
    public static final FieldExpression<Date> createTime = uemCustomerType.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCustomerType.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCustomerType.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = uemCustomerType.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCustomerType.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCustomerType.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemCustomerType.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> selectedItemCode = uemCustomerType.fieldOf("selectedItemCode", String.class);
    public static final FieldExpression<Long> uemCompanyId = uemCustomerType.fieldOf("uemCompanyId", Long.class);
    public static final FieldExpression<Long> uemCustomerTypeId = uemCustomerType.fieldOf("uemCustomerTypeId", Long.class);

    public static final BaseModelExpression<SysDictCode, Long> sysDictCode = new QSysDictCode(uemCustomerType, "sysDictCode");

    public QUemCustomerType() {
        super("UemCustomerType", UemCustomerType.class);
    }

    QUemCustomerType(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCustomerType", UemCustomerType.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCustomerTypeId;
    }
}
