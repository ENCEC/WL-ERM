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
import java.math.BigDecimal;
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

    /**现住址*/
    @Column(name = "address")
    private String address;

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

    /**出生日期*/
    @Column(name = "birthday")
    private String birthday;

    /**绑定企业*/
    @Column(name = "blind_companny")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long blindCompanny;

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

    /**转正员工答辩成绩*/
    @Column(name = "defense_score")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long defenseScore;

    /**用户所属部门编码*/
    @Column(name = "dept_code")
    private String deptCode;

    /**用户所属部门名称*/
    @Column(name = "dept_name")
    private String deptName;

    /**辞退时间*/
    @Column(name = "dismiss_date")
    private Date dismissDate;

    @Column(name = "dismiss_reason")
    private String dismissReason;

    /**学历（0：专科 1：本科 2：研究生 3：博士生）*/
    @Column(name = "education")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long education;

    /**邮箱*/
    @Column(name = "email")
    private String email;

    /**入职时间*/
    @Column(name = "entry_date")
    private Date entryDate;

    /**毕业时间*/
    @Column(name = "graduate_date")
    private Date graduateDate;

    /**毕业学校*/
    @Column(name = "graduate_school")
    private String graduateSchool;

    /**身份证号码*/
    @Column(name = "id_card")
    private String idCard;

    /**面谈评语*/
    @Column(name = "interview_comments")
    private String interviewComments;

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否同意协议(0不同意，1同意)*/
    @Column(name = "is_agreemeent")
    private Boolean isAgreemeent;

    /**逻辑删除flag*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**是否显示（0显示，1隐藏）*/
    @Column(name = "is_displayed")
    private Boolean isDisplayed;

    /**是否锁定（0-否；1-是）*/
    @Column(name = "is_locked")
    private Boolean isLocked;

    /**是否禁用(0禁用,1启用)*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**在职状态（0：试用员工 1：正式员工 2：离职员工）*/
    @Column(name = "job_status")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long jobStatus;

    /**离职时间*/
    @Column(name = "leave_date")
    private Date leaveDate;

    /**离职原因*/
    @Column(name = "leave_reason")
    private String leaveReason;

    /**婚姻状况（0：未婚 1：已婚 2：离婚）*/
    @Column(name = "marital_status")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long maritalStatus;

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

    /**转正时间*/
    @Column(name = "offer_date")
    private Date offerDate;

    /**转正原因*/
    @Column(name = "offer_reason")
    private String offerReason;

    /**来源应用*/
    @Column(name = "ori_application")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long oriApplication;

    /**密码*/
    @Column(name = "password")
    private String password;

    /**政治面貌（来源数据字典)*/
    @Column(name = "political_status")
    private String politicalStatus;

    /**转正评语*/
    @Column(name = "positive_comments")
    private String positiveComments;

    /**转正类型(0 正常转正 1 提前转正)*/
    @Column(name = "positive_type")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long positiveType;

    /**所属项目ID*/
    @Column(name = "project_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long projectId;

    /**所属项目名称*/
    @Column(name = "project_name")
    private String projectName;

    /**QQ绑定ID*/
    @Column(name = "qq_id")
    private String qqId;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**简历*/
    @Column(name = "resume")
    private String resume;

    /**员工申请表*/
    @Column(name = "staff_application")
    private String staffApplication;


    /**用户评分*/
    @Column(name = "score")
    private Integer score;

    /**工作年限*/
    @Column(name = "seniority")
    private BigDecimal seniority;

    /**排序号*/
    @Column(name = "seq_no")
    private String seqNo;

    /**性别（0男，1女）*/
    @Column(name = "sex")
    private Boolean sex;

    /**用户来源(0-用户注册，1-管理员新增)*/
    @Column(name = "source")
    private String source;

    /**户籍地址*/
    @Column(name = "source_address")
    private String sourceAddress;

    /**在校专业*/
    @Column(name = "speciality")
    private String speciality;

    /**人员岗位*/
    @Column(name = "staff_duty")
    private String staffDuty;

    /**人员岗位code*/
    @Column(name = "staff_duty_code")
    private String staffDutyCode;

    /**人员岗位ID*/
    @Column(name = "staff_duty_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long staffDutyId;

    /**岗位级别*/
    @Column(name = "staff_level")
    private String staffLevel;

    /**职称名称*/
    @Column(name = "technical_name")
    private String technicalName;

    /**岗位职称ID*/
    @Column(name = "technical_title_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long technicalTitleId;

    /**固定电话*/
    @Column(name = "telephone")
    private String telephone;

    /**用户所属部门ID*/
    @Column(name = "uem_dept_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemDeptId;

    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    @Column(name = "user_type")
    private String userType;

    /**微信绑定ID*/
    @Column(name = "wx_id")
    private String wxId;

}