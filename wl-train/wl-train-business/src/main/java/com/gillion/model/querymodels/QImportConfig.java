package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ImportConfig;

import java.lang.Boolean;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QImportConfig extends BaseModelExpression<ImportConfig, String> {

    public static final BaseModelExpression<ImportConfig, String> importConfig = new QImportConfig();
    public static final FieldExpression<String> id = importConfig.fieldOf("id", String.class);
    public static final FieldExpression<String> className = importConfig.fieldOf("className", String.class);
    public static final FieldExpression<byte[]> template = importConfig.fieldOf("template", byte[].class);
    public static final FieldExpression<String> dictionaryUrl = importConfig.fieldOf("dictionaryUrl", String.class);
    public static final FieldExpression<String> dealService = importConfig.fieldOf("dealService", String.class);
    public static final FieldExpression<String> methodName = importConfig.fieldOf("methodName", String.class);
    public static final FieldExpression<Boolean> validateHead = importConfig.fieldOf("validateHead", Boolean.class);
    public static final FieldExpression<String> validateMethod = importConfig.fieldOf("validateMethod", String.class);
    public static final FieldExpression<Boolean> async = importConfig.fieldOf("async", Boolean.class);
    public static final FieldExpression<Boolean> allowParameter = importConfig.fieldOf("allowParameter", Boolean.class);
    public static final FieldExpression<String> primaryColumns = importConfig.fieldOf("primaryColumns", String.class);
    public static final FieldExpression<String> defineColumn = importConfig.fieldOf("defineColumn", String.class);
    public static final FieldExpression<String> childrenTable = importConfig.fieldOf("childrenTable", String.class);


    public QImportConfig() {
        super("ImportConfig", ImportConfig.class);
    }

    QImportConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ImportConfig", ImportConfig.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return id;
    }
}
