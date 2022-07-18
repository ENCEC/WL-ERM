package com.share.auth.center.model.dto;

/**
 * @Author:chenxf
 * @Description: 统一认证平台返回access_token封装类
 * @Date: 14:18 2021/1/22
 * @Param: 
 * @Return:
 *
 */
public class OauthTokenDto {
    private String ret;
    private String msg;
    private String access_token;
    private String code;
    private String refresh_token;
    private Integer expires_in;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
