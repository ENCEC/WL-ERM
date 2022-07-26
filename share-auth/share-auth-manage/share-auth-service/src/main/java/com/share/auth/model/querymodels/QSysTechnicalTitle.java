package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;

import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QSysTechnicalTitle extends BaseModelExpression<SysTechnicalTitle, Long> {

    public static final BaseModelExpression<SysTechnicalTitle, Long> sysTechnicalTitle = new QSysTechnicalTitle();
    public static final FieldExpression<String> createBy = sysTechnicalTitle.fieldOf("createBy", String.class);
    public static final FieldExpression<Date> createTime = sysTechnicalTitle.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> postId = sysTechnicalTitle.fieldOf("postId", Long.class);
    public static final FieldExpression<String> remark = sysTechnicalTitle.fieldOf("remark", String.class);
    public static final FieldExpression<String> seniority = sysTechnicalTitle.fieldOf("seniority", String.class);
    public static final FieldExpression<String> status = sysTechnicalTitle.fieldOf("status", String.class);
    public static final FieldExpression<String> technicalName = sysTechnicalTitle.fieldOf("technicalName", String.class);
    public static final FieldExpression<Long> technicalTitleId = sysTechnicalTitle.fieldOf("technicalTitleId", Long.class);
    public static final FieldExpression<String> updateBy = sysTechnicalTitle.fieldOf("updateBy", String.class);
    public static final FieldExpression<Date> updateTime = sysTechnicalTitle.fieldOf("updateTime", Date.class);

    public static final BaseModelExpression<SysPost, Long> sysPost = new QSysPost(sysTechnicalTitle, "sysPost");
    public QSysTechnicalTitle() {
        super("SysTechnicalTitle", SysTechnicalTitle.class);
    }

    QSysTechnicalTitle(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysTechnicalTitle", SysTechnicalTitle.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return technicalTitleId;
    }
}
