package com.share.auth.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 国家综合交通运输信息平台增、删、改用户VO
 * @author chenhy
 * @date 2021-05-14
 */
@Data
public class UemUserOperateVO {

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
     * 登录账号（仅在同步存在用户重名的情况下，账号添加前缀，前缀为GJIM_）
     */
    @JsonProperty("LoginNo")
    private String loginNo;

    /**
     * 人员工号（编号）
     */
    @JsonProperty("StaffCode")
    private String staffCode;

    /**
     * 人员名称
     */
    @JsonProperty("StaffName")
    private String staffName;

    /**
     * 固定电话
     */
    @JsonProperty("Fixphone")
    private String fixphone;

    /**
     * 移动电话
     */
    @JsonProperty("StaffPhone")
    private String staffPhone;

    /**
     * 人员岗位
     */
    @JsonProperty("StaffDuty")
    private String staffDuty;

    /**
     * 所在组织
     */
    @JsonProperty("OrgId")
    private String orgId;

    /**
     * 人员邮箱
     */
    @JsonProperty("StaffMail")
    private String staffMail;

    /**
     * 人员状态：0-在职；1-离职；2-锁定
     */
    @JsonProperty("StaffStatus")
    private String staffStatus;

    /**
     * 排序号
     */
    @JsonProperty("SeqNo")
    private String seqNo;

    /**
     * 人员级别
     */
    @JsonProperty("StaffLvl")
    private String staffLvl;

    /**
     * 当前时间戳，用于授权校验
     */
    @JsonProperty("Time")
    private Long time;

}
