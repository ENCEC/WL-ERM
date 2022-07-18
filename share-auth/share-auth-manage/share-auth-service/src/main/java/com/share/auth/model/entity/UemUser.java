package com.share.auth.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gillion.ec.core.annotations.Generator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import com.gillion.ds.entity.base.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "uem_user")
public class UemUser extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "uem_user_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemUserId;

    /**用户名*/
    @Column(name = "account")
    private String account;

    /**审批客服*/
    @Column(name = "auditor")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    @Column(name = "audit_remark")
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批失败）*/
    @Column(name = "audit_status")
    private String auditStatus;

    /**审批时间*/
    @Column(name = "audit_time")
    private Date auditTime;

    /**绑定企业*/
    @Column(name = "blind_companny")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long blindCompanny;

    /**绑定企业时间*/
    @Column(name = "blind_companny_time")
    private Date blindCompannyTime;

    /**身份证反面图片地址id*/
    @Column(name = "card_back_url_id")
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    @Column(name = "card_positive_url_id")
    private String cardPositiveUrlId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**邮箱*/
    @Column(name = "email")
    private String email;

    /**政务账号绑定ID*/
    @Column(name = "gvm_id")
    private String gvmId;

    /**身份证号码*/
    @Column(name = "id_card")
    private String idCard;

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否同意协议(0不同意，1同意)*/
    @Column(name = "is_agreemeent")
    private Boolean isAgreemeent;

    /**是否显示（0显示，1隐藏）*/
    @Column(name = "is_displayed")
    private Boolean isDisplayed;

    /**是否锁定（0-否；1-是）*/
    @Column(name = "is_locked")
    private Boolean isLocked;

    /**是否禁用(0禁用,1启用)*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**手机号*/
    @Column(name = "mobile")
    private String mobile;

    /**修改人id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**姓名*/
    @Column(name = "name")
    private String name;

    /**所属组织机构代码*/
    @Column(name = "org_code")
    private String orgCode;

    /**来源应用*/
    @Column(name = "ori_application")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long oriApplication;

    /**密码*/
    @Column(name = "password")
    private String password;

    /**QQ绑定ID*/
    @Column(name = "qq_id")
    private String qqId;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**用户评分*/
    @Column(name = "score")
    private Integer score;

    /**排序号*/
    @Column(name = "seq_no")
    private String seqNo;

    /**性别（0男，1女）*/
    @Column(name = "sex")
    private Boolean sex;

    /**用户来源(0-用户注册，1-管理员新增，2-国家综合交通运输信息平台，3-一期数据)*/
    @Column(name = "source")
    private String source;

    /**国家综合平台用户工号*/
    @Column(name = "staff_code")
    private String staffCode;

    /**人员岗位*/
    @Column(name = "staff_duty")
    private String staffDuty;

    /**人员岗位code*/
    @Column(name = "staff_duty_code")
    private String staffDutyCode;

    /**人员级别*/
    @Column(name = "staff_level")
    private String staffLevel;

    /**固定电话*/
    @Column(name = "telephone")
    private String telephone;

    /**实名信息ID*/
    @Column(name = "uem_id_card_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemIdCardId;

    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    @Column(name = "user_type")
    private String userType;

    /**微信绑定ID*/
    @Column(name = "wx_id")
    private String wxId;

}