package com.share.auth.center.model.querymodels;

import com.gillion.ds.client.api.queryobject.expressions.BaseModelExpression;
import com.gillion.ds.client.api.queryobject.expressions.FieldExpression;
import com.gillion.ds.client.api.queryobject.expressions.OperatorExpression;
import com.share.auth.center.model.entity.OauthClientDetails;


/**
* @author daoServiceGenerator
* @version 1.0.0.0
*/
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused", "AlibabaClassNamingShouldBeCamel"})
public class QOauthClientDetails extends BaseModelExpression<OauthClientDetails, String> {

    public static final BaseModelExpression<OauthClientDetails, String> oauthClientDetails = new QOauthClientDetails();
    public static final FieldExpression<Integer> accessTokenValidity = oauthClientDetails.fieldOf("accessTokenValidity", Integer.class);
    public static final FieldExpression<String> additionalInformation = oauthClientDetails.fieldOf("additionalInformation", String.class);
    public static final FieldExpression<String> authorities = oauthClientDetails.fieldOf("authorities", String.class);
    public static final FieldExpression<String> authorizedGrantTypes = oauthClientDetails.fieldOf("authorizedGrantTypes", String.class);
    public static final FieldExpression<String> autoapprove = oauthClientDetails.fieldOf("autoapprove", String.class);
    public static final FieldExpression<String> clientId = oauthClientDetails.fieldOf("clientId", String.class);
    public static final FieldExpression<String> clientSecret = oauthClientDetails.fieldOf("clientSecret", String.class);
    public static final FieldExpression<Integer> refreshTokenValidity = oauthClientDetails.fieldOf("refreshTokenValidity", Integer.class);
    public static final FieldExpression<String> resourceIds = oauthClientDetails.fieldOf("resourceIds", String.class);
    public static final FieldExpression<String> scope = oauthClientDetails.fieldOf("scope", String.class);
    public static final FieldExpression<Long> sysApplicationId = oauthClientDetails.fieldOf("sysApplicationId", Long.class);
    public static final FieldExpression<String> webServerRedirectUri = oauthClientDetails.fieldOf("webServerRedirectUri", String.class);


    public QOauthClientDetails() {
        super("OauthClientDetails", OauthClientDetails.class);
    }

    QOauthClientDetails(BaseModelExpression<?, ?> parent, String alias) {
        super(parent, "OauthClientDetails", OauthClientDetails.class, alias);
    }

    @Override
    public OperatorExpression<String> primaryKey() {
        return clientId;
    }
}
