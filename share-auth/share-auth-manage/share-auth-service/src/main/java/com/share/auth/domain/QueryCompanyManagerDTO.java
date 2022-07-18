package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxf
 * @date 2020-10-30 15:23
 */
@Data
public class QueryCompanyManagerDTO implements Serializable{
    private static final long serialVersionUID = 1;

    /** 企业管理员表Id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyManagerId;

    /** 用户Id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /** 用户名*/
    private String account;

    /** 姓名*/
    private String name;

    /** 手机号*/
    private String mobile;

    /** 绑定企业Id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long blindCompany;

    /** 绑定企业名称*/
    private String  companyNameCn;

    /** 审批状态*/
    private String auditStatus;

    /**审批时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    /**邮箱*/
    private String email;

    /**性别*/
    private Boolean sex;

    /**身份证号码*/
    private String idCard;

    /**身份证反面图片地址id*/
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    private String cardPositiveUrlId;

    /**审批客服*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;
    /** 审批客服真实姓名*/
    private String auditorName;

    /**审批备注*/
    private String auditRemark;

    /**公正函上传地址id*/
    private String fileUrlId;

    /** 实名认证审批状态*/
    private String idCardStatus;

    /** 创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
