package com.share.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息修改DTO
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-25
 */
@ApiModel("UemUserEditDTO 用户信息新增/修改接口入参")
@Data
public class UemUserEditDTO {

    /**
     * 主键id(新增时为空,修改时不为空)
     */
    @ApiModelProperty("主键id")
    private Long uemUserId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String account;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @NotNull(message = "性别不能为空")
    private Boolean sex;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 在职状态（0：试用员工 1：正式员工 2：离职员工）
     */
    @ApiModelProperty("在职状态（0：试用员工 1：正式员工 2：离职员工）")
    @Range(min = 0, max = 2, message = "在职状态错误")
    @NotNull(message = "在职状态不能为空")
    private Long jobStatus;

    /**
     * 工作年限
     */
    @ApiModelProperty("工作年限")
    @NotNull(message = "工作年限不能为空")
    private BigDecimal seniority;

    /**
     * 入职时间
     */
    @ApiModelProperty("入职时间")
    @NotNull(message = "入职时间不能为空")
    private Date entryDate;

    /**
     * 人员岗位code
     */
    @ApiModelProperty("人员岗位code")
    @NotBlank(message = "人员岗位不能为空")
    private String staffDutyCode;

    /**
     * 人员岗位
     */
    @ApiModelProperty("人员岗位")
    private String staffDuty;

    /**
     * 所属项目ID
     */
    @ApiModelProperty("所属项目ID")
    @NotNull(message = "所属项目ID不能为空")
    private Long projectId;

    /**
     * 所属项目名称
     */
    @ApiModelProperty("所属项目名称")
    private String projectName;
}
