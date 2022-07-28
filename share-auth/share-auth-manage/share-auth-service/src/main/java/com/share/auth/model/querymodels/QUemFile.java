package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.UemFile;

import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QUemFile extends BaseModelExpression<UemFile, Long> {

    public static final BaseModelExpression<UemFile, Long> uemFile = new QUemFile();
    public static final FieldExpression<Date> createTime = uemFile.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = uemFile.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = uemFile.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> fileName = uemFile.fieldOf("fileName", String.class);
    public static final FieldExpression<String> filePath = uemFile.fieldOf("filePath", String.class);
    public static final FieldExpression<Byte> fileType = uemFile.fieldOf("fileType", Byte.class);
    public static final FieldExpression<Long> modifierId = uemFile.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = uemFile.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = uemFile.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = uemFile.fieldOf("recordVersion", Integer.class);
    public static final FieldExpression<Long> targetId = uemFile.fieldOf("targetId", Long.class);
    public static final FieldExpression<Long> uemFileId = uemFile.fieldOf("uemFileId", Long.class);


    public QUemFile() {
        super("UemFile", UemFile.class);
    }

    QUemFile(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "UemFile", UemFile.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return uemFileId;
    }
}
