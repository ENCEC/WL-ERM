package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.AppNodes;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QAppNodes extends BaseModelExpression<AppNodes, Long> {

    public static final BaseModelExpression<AppNodes, Long> appNodes = new QAppNodes();
    public static final FieldExpression<String> appName = appNodes.fieldOf("appName", String.class);
    public static final FieldExpression<Long> id = appNodes.fieldOf("id", Long.class);
    public static final FieldExpression<String> nodeName = appNodes.fieldOf("nodeName", String.class);
    public static final FieldExpression<Integer> nodeNum = appNodes.fieldOf("nodeNum", Integer.class);


    public QAppNodes() {
        super("AppNodes", AppNodes.class);
    }

    QAppNodes(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "AppNodes", AppNodes.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return id;
    }
}
