package com.share.auth.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.model.entity.SysPost;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
public class QSysPost extends BaseModelExpression<SysPost, Long> {

    public static final BaseModelExpression<SysPost, Long> sysPost = new QSysPost();
    public static final FieldExpression<String> creatorName = sysPost.fieldOf("creatorName", String.class);
    public static final FieldExpression<Date> createTime = sysPost.fieldOf("createTime", Date.class);
    public static final FieldExpression<String> postCode = sysPost.fieldOf("postCode", String.class);
    public static final FieldExpression<Long> postId = sysPost.fieldOf("postId", Long.class);
    public static final FieldExpression<String> postName = sysPost.fieldOf("postName", String.class);
    public static final FieldExpression<Integer> postSort = sysPost.fieldOf("postSort", Integer.class);
    public static final FieldExpression<String> remark = sysPost.fieldOf("remark", String.class);
    public static final FieldExpression<String> status = sysPost.fieldOf("status", String.class);
    public static final FieldExpression<String> modifierName = sysPost.fieldOf("modifierName", String.class);
    public static final FieldExpression<Date> modifyTime = sysPost.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> modifierId = sysPost.fieldOf("modifierId", Long.class);
    public static final FieldExpression<Long> creatorId = sysPost.fieldOf("creatorId", Long.class);

    public QSysPost() {
        super("SysPost", SysPost.class);
    }

    QSysPost(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "SysPost", SysPost.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return postId;
    }
}
