package com.share.auth.center.model.dto;

/**
 * @Author:chenxf
 * @Description: openId信息
 * @Date: 16:15 2021/1/22
 * @Param: 
 * @Return:
 *
 */
public class OpenIdResponseDto {
    private String ret;
    private String msg;
    private String openid;
    private String code;

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
