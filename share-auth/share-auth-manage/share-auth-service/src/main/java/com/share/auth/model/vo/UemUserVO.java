package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author: cjh
 * @Description: 联想控件返回结果
 * @Date: 2021-10-5
 */
@Data
public class UemUserVO extends BaseModel implements Serializable{

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /**用户名*/
    private String account;

    /**审批客服*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批失败）*/
    private String auditStatus;

    /**审批时间*/
    private Date auditTime;

    /**绑定企业*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long blindCompanny;

    /**绑定企业时间*/
    private Date blindCompannyTime;

    /**绑定企业*/
    private String companyNameCn;

    /**身份证反面图片地址id*/
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    private String cardPositiveUrlId;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**邮箱*/
    private String email;

    /**身份证号码*/
    private String idCard;

    /**启/禁用时间*/
    private Date invalidTime;

    /**是否同意协议(0不同意，1同意)*/
    private Boolean isAgreemeent;

    /**是否显示（0显示，1隐藏）*/
    private Boolean isDisplayed;

    /**是否锁定（0-否；1-是）*/
    private Boolean isLocked;

    /**是否禁用(0禁用,1启用)*/
    private Boolean isValid;

    /**手机号*/
    private String mobile;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**姓名*/
    private String name;

    /**所属组织机构代码*/
    private String orgCode;

    /**来源应用*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long oriApplication;

    /**密码*/
    private String password;

    /**QQ绑定ID*/
    private String qqId;

    /**版本号*/
    private Integer recordVersion;

    /**用户评分*/
    private Integer score;

    /**排序号*/
    private String seqNo;

    /**性别（0男，1女）*/
    private Boolean sex;

    /**用户来源(0-用户注册，1-管理员新增，2-国家综合交通运输信息平台，3-一期数据)*/
    private String source;

    /**人员岗位*/
    private String staffDuty;

    /**人员岗位code*/
    private String staffDutyCode;

    /**人员级别*/
    private String staffLevel;

    /**固定电话*/
    private String telephone;

    /**实名信息ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemIdCardId;

    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    private String userType;

    /**微信绑定ID*/
    private String wxId;

}
