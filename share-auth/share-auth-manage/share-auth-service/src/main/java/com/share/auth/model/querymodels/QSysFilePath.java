package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysFilePath;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysFilePath extends BaseModelExpression<SysFilePath, Long> {

    public static final BaseModelExpression<SysFilePath, Long> sysFilePath = new QSysFilePath();
    public static final FieldExpression<String> account = sysFilePath.fieldOf("account", String.class);
    public static final FieldExpression<Date> createTime = sysFilePath.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = sysFilePath.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = sysFilePath.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> fileName = sysFilePath.fieldOf("fileName", String.class);
    public static final FieldExpression<String> fileType = sysFilePath.fieldOf("fileType", String.class);
    public static final FieldExpression<String> fileUrl = sysFilePath.fieldOf("fileUrl", String.class);
    public static final FieldExpression<Date> invalidTime = sysFilePath.fieldOf("invalidTime", Date.class);
    public static final FieldExpression<Boolean> isPsdCheck = sysFilePath.fieldOf("isPsdCheck", Boolean.class);
    public static final FieldExpression<Boolean> isValid = sysFilePath.fieldOf("isValid", Boolean.class);
    public static final FieldExpression<Long> modifierId = sysFilePath.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = sysFilePath.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysFilePath.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<String> operType = sysFilePath.fieldOf("operType", String.class);
    public static final FieldExpression<String> password = sysFilePath.fieldOf("password", String.class);
    public static final FieldExpression<Integer> recordVersion = sysFilePath.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<String> serverAddress = sysFilePath.fieldOf("serverAddress", String.class);
    public static final FieldExpression<Long> sysFilePathId = sysFilePath.fieldOf("sysFilePathId", Long.class);


    public QSysFilePath() {
        super("SysFilePath", SysFilePath.class);
    }

    QSysFilePath(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysFilePath", SysFilePath.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return sysFilePathId;
    }
}
