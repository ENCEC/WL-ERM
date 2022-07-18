package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysDictCode;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemCompanyCargoType;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemCompanyCargoType extends BaseModelExpression<UemCompanyCargoType, Long> {

    public static final BaseModelExpression<UemCompanyCargoType, Long> uemCompanyCargoType = new QUemCompanyCargoType();
    public static final FieldExpression<String> cargoType = uemCompanyCargoType.fieldOf("cargoType", String.class);
    public static final FieldExpression<String> cargoTypeCode = uemCompanyCargoType.fieldOf("cargoTypeCode", String.class);
    public static final FieldExpression<Date> createTime = uemCompanyCargoType.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemCompanyCargoType.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemCompanyCargoType.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> modifierId = uemCompanyCargoType.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemCompanyCargoType.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemCompanyCargoType.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemCompanyCargoType.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> uemCompanyCargoTypeId = uemCompanyCargoType.fieldOf("uemCompanyCargoTypeId", Long.class);
    public static final FieldExpression<Long> uemCompanyId = uemCompanyCargoType.fieldOf("uemCompanyId", Long.class);

    public static final BaseModelExpression<SysDictCode, Long> sysDictCode = new QSysDictCode(uemCompanyCargoType, "sysDictCode");
    public static final BaseModelExpression<UemCompany, Long> uemCompany = new QUemCompany(uemCompanyCargoType, "uemCompany");

    public QUemCompanyCargoType() {
        super("UemCompanyCargoType", UemCompanyCargoType.class);
    }

    QUemCompanyCargoType(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemCompanyCargoType", UemCompanyCargoType.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemCompanyCargoTypeId;
    }
}
