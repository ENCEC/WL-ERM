package com.share.auth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "oauth_client_details")
public class OauthClientDetails extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "client_id")
    @Generator("snowFlakeGenerator")
    private String clientId;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "autoapprove")
    private String autoapprove;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "scope")
    private String scope;

    /**应用表id*/
    @Column(name = "sys_application_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

}