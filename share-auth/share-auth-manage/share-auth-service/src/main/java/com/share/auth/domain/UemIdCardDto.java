package com.share.auth.domain;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @Date 20201021
 * @author xrp*/
@Data
@Api("实名信息表")
public class UemIdCardDto {

    /**姓名*/
    private String name;

    /**性别（0男，1女）*/
    private Boolean sex;

    /**身份证号码*/
    private String idCard;

    /**关联用户表id*/
    private String uemUserId;

    /**身份证正面图片地址id*/
    private String cardPositiveUrlId;

    /**身份证反面图片地址id*/
    private String cardBackUrlId;
}
