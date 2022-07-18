package com.share.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tujx
 * @description 用户信息修改DTO
 * @date 2021/02/23
 */
@ApiModel("用户信息修改")
@Data
public class UemUserEditDTO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @NotNull(message = "主键id不能为空")
    private Long uemUserId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String account;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Boolean sex;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 身份证正面图片地址id
     */
    @ApiModelProperty("身份证正面图片地址id")
    private String cardPositiveUrlId;

    /**
     * 身份证反面图片地址id
     */
    @ApiModelProperty("身份证反面图片地址id")
    private String cardBackUrlId;
}
