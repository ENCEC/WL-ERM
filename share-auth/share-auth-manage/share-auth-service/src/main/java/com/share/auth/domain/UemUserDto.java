package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gillion.ds.entity.base.BaseModel;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @date 20201021
 * @author xrp
 * */
@Api("用户表")
@Data
public class UemUserDto extends BaseModel implements Serializable {

    /**id*/
    private String uemUserId;

    /**用户名*/
    private String account;

    /**手机号*/
    private String mobile;

    /**密码*/
    private String password;

    /**验证码*/
    private String authCode;

    /**新密码*/
    private String newPassword;

    /**手机号或者邮箱*/
    private String mobileOrEmail;

    /**
     * 绑定企业
     */
    private String blindCompanny;
    /**
     * 绑定企业时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date blindCompannyTime;
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 用户来源
     */
    private String source;

    /**
     * 来源应用
     */
    private Long oriApplication;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否禁用(0禁用,1启用)
     */
    private Boolean isValid;

    /**
     * 是否同意协议(0不同意，1同意)
     */
    private Boolean isAgreemeent;

    /**
     * 启/禁用时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invalidTime;
    /**
     * 分页页数
     */
    private Integer pageNo;
    /**
     * 分页显示条数
     */
    private Integer pageSize;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /**解除绑定时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date quitTime;
    /**绑定企业时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entryTime;
    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    private String userType;

    private String companyNameCn;

    @JsonProperty("selectedItemCode")
    private List<String> selectedItemCode;
    /**用户评分*/
    private Integer score;
    /**审批状态（0待审批，1审批通过，2审批失败）*/
    private String auditStatus;
    /**身份证号码*/
    private String idCard;
    /**身份证反面图片地址id*/
    private String cardBackUrlId;
    /**身份证正面图片地址id*/
    private String cardPositiveUrlId;
    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private List<Long> uemCompanyIdList;
    /** 发送验证码接口： 标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号 */
    private String sign;

    /** 更新密码时的token*/
    private String updatePwdToken;

    /** 用户身份，1是普通用户/企业用户，2是企业管理员，3是平台客服*/
    private String identity;

    /** 是否可以解除绑定*/
    private boolean hasBind;

    /** 管理员申请展示： 1申请管理员，2管理员申请审批中，3管理员申请历史*/
    private int showManagerButton;

    /** 企业审批状态*/
    private String companyAuditStatus;

    /** 实名认证审批时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    /** 实名认证表id*/
    private Long uemIdCardId;
    /**版本号*/
    private Integer recordVersion;

    /** 是否有调度系统角色*/
    private boolean hasRole;

    /** 绑定企业审核状态*/
    private String blindStatus;

    /** 固定电话*/
    private String telephone;
    /** 所属组织代码*/
    private String orgCode;
    /** 国家综合平台用户工号*/
    private String staffCode;
    /** 人员岗位*/
    private String staffDuty;
    /** 人员级别*/
    private String staffLevel;
    /** 排序号*/
    private String seqNo;
    /** 更新时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;


    /** 人员岗位code*/
    private String staffDutyCode;

    /**平台客服新增用户：是否企业管理员（true-是，false-否）*/
    private Boolean isCompanyManager;

    /**绑定企业是否存在企业管理员（用于用户绑定企业且待审核时，展示不同的提示语）*/
    private Boolean hasCompanyManager;

    /**企业管理员名称*/
    private String companyManagerNames;

    /**企业启用/禁用状态(0禁用,1启用)*/
    private Boolean companyIsValid;
    /**组织类型**/
    private String organizationType;

    /**是否已经获取验证码**/
    private Boolean authCodeFlag;
}
