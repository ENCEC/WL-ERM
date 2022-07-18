package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gillion.ds.entity.base.BaseModel;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/10/30 0030
 */
@Api("登录日志表")
@Data
public class UemLogDto extends BaseModel implements Serializable {

    /**登录结果（0成功，1锁定）*/
    private String result;
    /**登录时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date logDate;
    /**登录开始时间*/
    private String beginTime;
    /**IP地址*/
    private String ipAddress;
    /**城市*/
    private String city;
    /**登录设备（0手机，1电脑）*/
    private String equipment;
    /**登录方式(0用户名，1手机，2邮箱，3微信，4qq，5国家政务平台)*/
    private String way;
    /**当前页数*/
    private Integer pageNo;
    /**显示条数*/
    private Integer pageSize;
    /**姓名*/
    private String name;
    /**用户名*/
    private String account;
    /**用户类型*/
    private String userType;
    /**用户ID*/
    private String uemUserId;
    /**绑定企业ID*/
    private String companyId;

}
