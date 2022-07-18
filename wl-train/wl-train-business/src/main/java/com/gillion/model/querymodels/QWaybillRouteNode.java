package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.WaybillRouteNode;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QWaybillRouteNode extends BaseModelExpression<WaybillRouteNode, Long> {

    public static final BaseModelExpression<WaybillRouteNode, Long> waybillRouteNode = new QWaybillRouteNode();
    public static final FieldExpression<Long> waybillRouteNodeId = waybillRouteNode.fieldOf("waybillRouteNodeId", Long.class);
    public static final FieldExpression<Long> waybillId = waybillRouteNode.fieldOf("waybillId", Long.class);
    public static final FieldExpression<String> startingSiteName = waybillRouteNode.fieldOf("startingSiteName", String.class);
    public static final FieldExpression<String> arrivalSiteName = waybillRouteNode.fieldOf("arrivalSiteName", String.class);
    public static final FieldExpression<Integer> siteNum = waybillRouteNode.fieldOf("siteNum", Integer.class);


    public QWaybillRouteNode() {
        super("WaybillRouteNode", WaybillRouteNode.class);
    }

    QWaybillRouteNode(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "WaybillRouteNode", WaybillRouteNode.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return waybillRouteNodeId;
    }
}
