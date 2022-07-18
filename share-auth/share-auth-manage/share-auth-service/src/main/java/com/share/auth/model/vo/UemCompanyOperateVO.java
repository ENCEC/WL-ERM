package com.share.auth.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 国家综合交通运输信息平台增、删、改组织机构VO
 * @author chenhy
 * @date 2021-05-14
 */
@Data
public class UemCompanyOperateVO {

    /**
     * 授权码：业务系统用于验证授权码是否合法，双方规定
     * OrgCode、Time、Key拼接后的字符串经过MD5加密
     */
    @JsonProperty("Accesskey")
    private String accessKey;

    /**
     * 参考操作类型1：新增，2：修改，3：删除
     */
    @JsonProperty("OptionType")
    private String optionType;

    /**
     * 组织机构编码（唯一）
     */
    @JsonProperty("OrgCode")
    private String orgCode;

    /**
     * 组织机构名称
     */
    @JsonProperty("OrgName")
    private String orgName;

    /**
     * 上级组织机构（最上级组织的上级组织代码为root）
     */
    @JsonProperty("ParentOrgCode")
    private String parentOrgCode;

    /**
     * 组织状态：0-正常；1-无效
     */
    @JsonProperty("OrgStatus")
    private String orgStatus;

    /**
     * 组织机构树顺序
     */
    @JsonProperty("OrgSeq")
    private String orgSeq;

    /**
     * 当前时间戳，用于授权校验
     */
    @JsonProperty("Time")
    private Long time;

}
