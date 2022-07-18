package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/10/28 0028
 */
@Api("用户企业绑定表")
@Data
public class UemUserCompanyDto implements Serializable {

    /**关联用户表id*/
    private String uemUserId;

    /**关联企业ID*/
    private String uemCompanyId;

    /**用户所在企业角色（0企业普通用户，1企业管理员）*/
    private Boolean userRole;

    private String account;

    private String name;

    private String mobile;

    private String source;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date quitTime;

    private String applicationName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /**绑定企业*/
    private String blindCompanny;

    private Integer pageNo;

    private Integer pageSize;
}
