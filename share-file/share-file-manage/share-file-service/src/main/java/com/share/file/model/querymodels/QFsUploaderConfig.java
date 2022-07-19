package com.share.file.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.file.model.entity.FsUploaderConfig;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QFsUploaderConfig extends BaseModelExpression<FsUploaderConfig, Long> {

    public static final BaseModelExpression<FsUploaderConfig, Long> fsUploaderConfig = new QFsUploaderConfig();
    public static final FieldExpression<String> businessSystemId = fsUploaderConfig.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Integer> countLimit = fsUploaderConfig.fieldOf("countLimit", Integer.class);
    public static final FieldExpression<Long> createCompanyId = fsUploaderConfig.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = fsUploaderConfig.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = fsUploaderConfig.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = fsUploaderConfig.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = fsUploaderConfig.fieldOf("creatorName", String.class);
    public static final FieldExpression<Long> fsUploaderConfigId = fsUploaderConfig.fieldOf("fsUploaderConfigId", Long.class);
    public static final FieldExpression<Boolean> isDeleted = fsUploaderConfig.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Long> modifierId = fsUploaderConfig.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = fsUploaderConfig.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = fsUploaderConfig.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = fsUploaderConfig.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = fsUploaderConfig.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = fsUploaderConfig.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<BigDecimal> sizeLimit = fsUploaderConfig.fieldOf("sizeLimit", BigDecimal.class);
    public static final FieldExpression<String> typeLimit = fsUploaderConfig.fieldOf("typeLimit", String.class);


    public QFsUploaderConfig() {
        super("FsUploaderConfig", FsUploaderConfig.class);
    }

    QFsUploaderConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "FsUploaderConfig", FsUploaderConfig.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return fsUploaderConfigId;
    }
}
