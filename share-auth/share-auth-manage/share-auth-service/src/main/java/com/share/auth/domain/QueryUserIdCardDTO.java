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
 * @date 2020-10-29 14:51
 */
@Data
public class QueryUserIdCardDTO implements Serializable{
    private static final long serialVersionUID = 1;
    /** 用户id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /** 用户实名认证表id */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemIdCardId;
    /** 来源应用名称 */
    private String oriApplicationName;

    /**用户来源*/
    private String source;

    /**用户名*/
    private String account;

    /**手机号*/
    private String mobile;

    /**邮箱*/
    private String email;

    /**注册时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registeredTime;

    /**姓名*/
    private String name;
    /**性别*/
    private Boolean sex;

    /**身份证号码*/
    private String idCard;

    /**身份证反面图片地址id*/
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    private String cardPositiveUrlId;

    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**审批客服*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;
    /** 审批客服真实姓名*/
    private String auditorName;

    /**审批备注*/
    private String auditRemark;

    /**审批状态*/
    private String auditStatus;

    /**审批时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    /**绑定企业名称*/
    private String companyNameCn;

}
