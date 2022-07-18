package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxf
 * @date 2021-01-20 15:13
 */
@Data
public class VaccineCompanyDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /** 企业证书*/
    private String fileUrlId;

    /**企业中文名称*/
    private String companyNameCn;

    /**企业英文名称*/
    private String companyNameEn;


    /**企业联系人*/
    private String contact;


    /**联系人手机*/
    private String contactTel;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**数据来源（0用户新增，2客服新增）*/
    private String dataSource;

    /**国际货运代理人工商登记材料*/
    private String internationalBusinessFileUrl;

    /**启/禁用时间*/
    private Date invalidTime;

    /**是否禁用(1启用,0禁用)*/
    private Boolean isValid;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**组织机构代码*/
    private String organizationCode;

    /**版本号*/
    private Integer recordVersion;

}
