package com.share.file.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.file.model.entity.FsFileInfo;

import java.math.BigDecimal;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QFsFileInfo extends BaseModelExpression<FsFileInfo, Long> {

    public static final BaseModelExpression<FsFileInfo, Long> fsFileInfo = new QFsFileInfo();
    public static final FieldExpression<String> businessSystemId = fsFileInfo.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Long> createCompanyId = fsFileInfo.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = fsFileInfo.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = fsFileInfo.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = fsFileInfo.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = fsFileInfo.fieldOf("creatorName", String.class);
    public static final FieldExpression<String> fileKey = fsFileInfo.fieldOf("fileKey", String.class);
    public static final FieldExpression<String> fileName = fsFileInfo.fieldOf("fileName", String.class);
    public static final FieldExpression<BigDecimal> fileSize = fsFileInfo.fieldOf("fileSize", BigDecimal.class);
    public static final FieldExpression<String> fileType = fsFileInfo.fieldOf("fileType", String.class);
    public static final FieldExpression<Long> fsFileInfoId = fsFileInfo.fieldOf("fsFileInfoId", Long.class);
    public static final FieldExpression<Boolean> isDeleted = fsFileInfo.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Long> modifierId = fsFileInfo.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = fsFileInfo.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = fsFileInfo.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = fsFileInfo.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = fsFileInfo.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Integer> recordVersion = fsFileInfo.fieldOf("recordVersion", Integer.class);


    public QFsFileInfo() {
        super("FsFileInfo", FsFileInfo.class);
    }

    QFsFileInfo(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "FsFileInfo", FsFileInfo.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return fsFileInfoId;
    }
}
