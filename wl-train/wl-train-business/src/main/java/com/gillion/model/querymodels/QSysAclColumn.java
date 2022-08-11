package com.gillion.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.gillion.model.entity.SysAclColumn;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysAclColumn extends BaseModelExpression<SysAclColumn, Integer> {

    public static final BaseModelExpression<SysAclColumn, Integer> sysAclColumn = new QSysAclColumn();
    public static final FieldExpression<Integer> aclColumnId = sysAclColumn.fieldOf("aclColumnId", Integer.class);
    public static final FieldExpression<Integer> aclTableId = sysAclColumn.fieldOf("aclTableId", Integer.class);
    public static final FieldExpression<String> columnName = sysAclColumn.fieldOf("columnName", String.class);
    public static final FieldExpression<Integer> createrId = sysAclColumn.fieldOf("createrId", Integer.class);
    public static final FieldExpression<Date> createTime = sysAclColumn.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> remark = sysAclColumn.fieldOf("remark", String.class);
    public static final FieldExpression<Integer> updaterId = sysAclColumn.fieldOf("updaterId", Integer.class);
    public static final FieldExpression<Date> updateTime = sysAclColumn.fieldOf("updateTime", Date.class);


    public QSysAclColumn() {
        super("SysAclColumn", SysAclColumn.class);
    }

    QSysAclColumn(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysAclColumn", SysAclColumn.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return aclColumnId;
    }
}
