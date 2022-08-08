package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-26
 */
@ApiModel("UemUserDto 用户信息")
@Data
public class UemUserDto extends BaseModel implements Serializable {

    /**id*/
    @ApiModelProperty("id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /**
     * 主键id数组
     */
    @ApiModelProperty("主键数组")
    private List<String> uemUserIds;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String account;

    /**现住址*/
    @ApiModelProperty("现住址")
    private String address;

    /**审批客服*/
    @ApiModelProperty("审批客服")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    @ApiModelProperty("审批备注")
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批失败）*/
    @ApiModelProperty("审批状态（0待审批，1审批通过，2审批失败）")
    private String auditStatus;

    /**审批时间*/
    @ApiModelProperty("审批时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditTime;

    /**出生日期*/
    @ApiModelProperty("出生日期")
    private String birthday;

    /**绑定企业*/
    @ApiModelProperty("绑定企业")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long blindCompanny;

    /**身份证反面图片地址id*/
    @ApiModelProperty("身份证反面图片地址id")
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    @ApiModelProperty("身份证正面图片地址id")
    private String cardPositiveUrlId;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**创建人id*/
    @ApiModelProperty("创建人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**用户所属部门编码*/
    @ApiModelProperty("用户所属部门编码")
    private String deptCode;

    /**用户所属部门名称*/
    @ApiModelProperty("用户所属部门名称")
    private String deptName;

    /**学历（0：专科 1：本科 2：研究生 3：博士生）*/
    @ApiModelProperty("学历（0：专科 1：本科 2：研究生 3：博士生）")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long education;

    /**邮箱*/
    @ApiModelProperty("邮箱")
    private String email;

    /**入职时间*/
    @ApiModelProperty("入职时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;

    /**毕业时间*/
    @ApiModelProperty("毕业时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date graduateDate;

    /**毕业学校*/
    @ApiModelProperty("毕业学校")
    private String graduateSchool;

    /**身份证号码*/
    @ApiModelProperty("身份证号码")
    private String idCard;

    /**启/禁用时间*/
    @ApiModelProperty("启/禁用时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invalidTime;

    /**是否同意协议(0不同意，1同意)*/
    @ApiModelProperty("是否同意协议(0不同意，1同意)")
    private Boolean isAgreemeent;

    /**是否显示（0显示，1隐藏）*/
    @ApiModelProperty("是否显示（0显示，1隐藏）")
    private Boolean isDisplayed;

    /**是否锁定（0-否；1-是）*/
    @ApiModelProperty("是否锁定（0-否；1-是）")
    private Boolean isLocked;

    /**是否禁用(0禁用,1启用)*/
    @ApiModelProperty("是否禁用(0禁用,1启用)")
    private Boolean isValid;

    /**在职状态（0：试用员工 1：正式员工 2：离职员工）*/
    @ApiModelProperty("在职状态（0：试用员工 1：正式员工 2：离职员工）")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long jobStatus;

    /**离职时间*/
    @ApiModelProperty("离职时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveDate;

    /**离职原因*/
    @ApiModelProperty("离职原因")
    private String leaveReason;

    /**婚姻状况（0：未婚 1：已婚 2：离婚）*/
    @ApiModelProperty("婚姻状况（0：未婚 1：已婚 2：离婚）")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long maritalStatus;

    /**手机号*/
    @ApiModelProperty("手机号")
    private String mobile;

    /**修改人id*/
    @ApiModelProperty("修改人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @ApiModelProperty("修改人名称")
    private String modifierName;

    /**修改时间*/
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    /**姓名*/
    @ApiModelProperty("姓名")
    private String name;

    /**转正时间*/
    @ApiModelProperty("转正时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date offerDate;

    /**转正原因*/
    @ApiModelProperty("转正原因")
    private String offerReason;

    /**
     * 辞退时间
     */
    @ApiModelProperty("辞退时间")
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    private Date dismissDate;

    /**
     * 辞退原因
     */
    @ApiModelProperty("辞退原因")
    private String dismissReason;

    /**
     * 来源应用
     */
    @ApiModelProperty("来源应用")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long oriApplication;

//    /**密码*/
//    @ApiModelProperty("密码")
//    private String password;

    /**所属项目ID*/
    @ApiModelProperty("所属项目ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long projectId;

    /**所属项目名称*/
    @ApiModelProperty("所属项目名称")
    private String projectName;

    /**QQ绑定ID*/
    @ApiModelProperty("QQ绑定ID")
    private String qqId;

    /**版本号*/
    @ApiModelProperty("版本号")
    private Integer recordVersion;

    /**简历*/
    @ApiModelProperty("简历")
    private String resume;

    /**用户评分*/
    @ApiModelProperty("用户评分")
    private Integer score;

    /**工作年限*/
    @ApiModelProperty("工作年限")
    private BigDecimal seniority;

    /**排序号*/
    @ApiModelProperty("排序号")
    private String seqNo;

    /**性别（0男，1女）*/
    @ApiModelProperty("性别（0男，1女）")
    private Boolean sex;

    /**用户来源(0-用户注册，1-管理员新增)*/
    @ApiModelProperty("用户来源(0-用户注册，1-管理员新增)")
    private String source;

    /**户籍地址*/
    @ApiModelProperty("户籍地址")
    private String sourceAddress;

    /**
     * 转正类型
     */
    @ApiModelProperty("转正类型")
    private Long positiveType;
    /**
     * 答辩成绩
     */
    @ApiModelProperty("答辩成绩")
    private Long defenseScore;

    /**
     * 面谈评语
     */
    @ApiModelProperty("面谈评语")
    private String interviewComments;

    /**
     * 转正评语
     */
    @ApiModelProperty("转正评语")
    private String positiveComments;

    /**
     * 在校专业
     */
    @ApiModelProperty("在校专业")
    private String speciality;

    /**人员岗位*/
    @ApiModelProperty("人员岗位")
    private String staffDuty;

    /**人员岗位code*/
    @ApiModelProperty("人员岗位code")
    private String staffDutyCode;

    /**岗位级别*/
    @ApiModelProperty("岗位级别")
    private String staffLevel;

    /**职称名称*/
    @ApiModelProperty("职称名称")
    private String technicalName;

    /**岗位职称ID*/
    @ApiModelProperty("岗位职称ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long technicalTitleId;

    /**固定电话*/
    @ApiModelProperty("固定电话")
    private String telephone;

    /**用户所属部门ID*/
    @ApiModelProperty("用户所属部门ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemDeptId;

    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    @ApiModelProperty("用户类型（0-普通用户，1-企业用户，2-企业管理员）")
    private String userType;

    /**微信绑定ID*/
    @ApiModelProperty("微信绑定ID")
    private String wxId;

    /** 分页页数 */
    @ApiModelProperty("分页页数")
    private Integer pageNo;

    /** 分页显示条数 */
    @ApiModelProperty("分页显示条数")
    private Integer pageSize;

    /** 发送验证码接口： 标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号 */
    @ApiModelProperty("发送验证码接口")
    private String sign;

    /** 更新密码时的token */
    @ApiModelProperty("更新密码时的token")
    private String updatePwdToken;

    /**验证码**/
    @ApiModelProperty("验证码")
    private String authCode;

    /**是否已经获取验证码**/
    @ApiModelProperty("是否已经获取验证码")
    private Boolean authCodeFlag;

    @ApiModelProperty("修改密码")
    private String newPassword;

    @ApiModelProperty("角色列表")
    private List<SysRoleDTO> roleList;
}
