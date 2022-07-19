package com.share.message.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.message.model.entity.MsgEmailAttachment;
import com.share.message.model.entity.MsgEmailTemplate;

import java.util.Date;

/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QMsgEmailAttachment extends BaseModelExpression<MsgEmailAttachment, Long> {

    public static final BaseModelExpression<MsgEmailAttachment, Long> msgEmailAttachment = new QMsgEmailAttachment();
    public static final FieldExpression<String> attachmentType = msgEmailAttachment.fieldOf("attachmentType", String.class);
    public static final FieldExpression<String> attachmentUrl = msgEmailAttachment.fieldOf("attachmentUrl", String.class);
    public static final FieldExpression<String> businessSystemId = msgEmailAttachment.fieldOf("businessSystemId", String.class);
    public static final FieldExpression<Long> createCompanyId = msgEmailAttachment.fieldOf("createCompanyId", Long.class);
    public static final FieldExpression<String> createCompanyName = msgEmailAttachment.fieldOf("createCompanyName", String.class);
    public static final FieldExpression<Date> createTime = msgEmailAttachment.fieldOf("createTime", Date.class);
    public static final FieldExpression<Long> creatorId = msgEmailAttachment.fieldOf("creatorId", Long.class);
    public static final FieldExpression<String> creatorName = msgEmailAttachment.fieldOf("creatorName", String.class);
    public static final FieldExpression<Boolean> isDeleted = msgEmailAttachment.fieldOf("isDeleted", Boolean.class);
    public static final FieldExpression<Long> modifierId = msgEmailAttachment.fieldOf("modifierId", Long.class);
    public static final FieldExpression<String> modifierName = msgEmailAttachment.fieldOf("modifierName", String.class);
    public static final FieldExpression<Long> modifyCompanyId = msgEmailAttachment.fieldOf("modifyCompanyId", Long.class);
    public static final FieldExpression<String> modifyCompanyName = msgEmailAttachment.fieldOf("modifyCompanyName", String.class);
    public static final FieldExpression<Date> modifyTime = msgEmailAttachment.fieldOf("modifyTime", Date.class);
    public static final FieldExpression<Long> msgEmailAttachmentId = msgEmailAttachment.fieldOf("msgEmailAttachmentId", Long.class);
    public static final FieldExpression<Long> msgEmailTemplateId = msgEmailAttachment.fieldOf("msgEmailTemplateId", Long.class);
    public static final FieldExpression<Integer> recordVersion = msgEmailAttachment.fieldOf("recordVersion", Integer.class);

    public static final BaseModelExpression<MsgEmailTemplate, Long> msgEmailTemplate = new QMsgEmailTemplate(msgEmailAttachment, "msgEmailTemplate");

    public QMsgEmailAttachment() {
        super("MsgEmailAttachment", MsgEmailAttachment.class);
    }

    QMsgEmailAttachment(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "MsgEmailAttachment", MsgEmailAttachment.class, alias);
    }

    @Override
    public OperatorExpression<Long> primaryKey() {
        return msgEmailAttachmentId;
    }
}
