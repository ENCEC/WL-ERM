package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.ExportConfig;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QExportConfig extends BaseModelExpression<ExportConfig, String> {

    public static final BaseModelExpression<ExportConfig, String> exportConfig = new QExportConfig();
    public static final FieldExpression<String> id = exportConfig.fieldOf("id", String.class);
    public static final FieldExpression<Boolean> printMode = exportConfig.fieldOf("printMode", Boolean.class);
    public static final FieldExpression<byte[]> template = exportConfig.fieldOf("template", byte[].class);
    public static final FieldExpression<Boolean> async = exportConfig.fieldOf("async", Boolean.class);
    public static final FieldExpression<String> dictionaryUrl = exportConfig.fieldOf("dictionaryUrl", String.class);
    public static final FieldExpression<String> fileName = exportConfig.fieldOf("fileName", String.class);
    public static final FieldExpression<Integer> pageSize = exportConfig.fieldOf("pageSize", Integer.class);
    public static final FieldExpression<String> className = exportConfig.fieldOf("className", String.class);
    public static final FieldExpression<String> sourceUrl = exportConfig.fieldOf("sourceUrl", String.class);
    public static final FieldExpression<String> statisticsUrl = exportConfig.fieldOf("statisticsUrl", String.class);
    public static final FieldExpression<String> columnSetting = exportConfig.fieldOf("columnSetting", String.class);


    public QExportConfig() {
        super("ExportConfig", ExportConfig.class);
    }

    QExportConfig(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "ExportConfig", ExportConfig.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return id;
    }
}
