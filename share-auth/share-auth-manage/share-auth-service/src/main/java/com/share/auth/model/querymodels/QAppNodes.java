package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.AppNodes;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QAppNodes extends BaseModelExpression<AppNodes, Integer> {

    public static final BaseModelExpression<AppNodes, Integer> appNodes = new QAppNodes();
    public static final FieldExpression<String> appName = appNodes.fieldOf("appName", String.class);
    public static final FieldExpression<Integer> id = appNodes.fieldOf("id", Integer.class);
    public static final FieldExpression<String> nodeName = appNodes.fieldOf("nodeName", String.class);
    public static final FieldExpression<Integer> nodeNum = appNodes.fieldOf("nodeNum", Integer.class);


    public QAppNodes() {
        super("AppNodes", AppNodes.class);
    }

    QAppNodes(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "AppNodes", AppNodes.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return id;
    }
}
