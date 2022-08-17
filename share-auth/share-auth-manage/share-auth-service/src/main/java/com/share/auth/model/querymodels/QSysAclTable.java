package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysAclTable;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysAclTable extends BaseModelExpression<SysAclTable, Integer> {

    public static final BaseModelExpression<SysAclTable, Integer> sysAclTable = new QSysAclTable();
    public static final FieldExpression<Integer> aclMode = sysAclTable.fieldOf("aclMode", Integer.class);
    public static final FieldExpression<Integer> aclTableId = sysAclTable.fieldOf("aclTableId", Integer.class);
    public static final FieldExpression<Integer> createrId = sysAclTable.fieldOf("createrId", Integer.class);
    public static final FieldExpression<Date> createTime = sysAclTable.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> remarks = sysAclTable.fieldOf("remarks", String.class);
    public static final FieldExpression<String> tableName = sysAclTable.fieldOf("tableName", String.class);
    public static final FieldExpression<Integer> updaterId = sysAclTable.fieldOf("updaterId", Integer.class);
    public static final FieldExpression<Date> updateTime = sysAclTable.fieldOf("updateTime", Date.class);


    public QSysAclTable() {
        super("SysAclTable", SysAclTable.class);
    }

    QSysAclTable(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysAclTable", SysAclTable.class, alias);
    }

    @Override
    public OperatorExpression<Integer> primaryKey() {
        return aclTableId;
    }
}
