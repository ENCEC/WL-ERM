package com.share.auth.center.model.dto;

import java.io.Serializable;

/**
 * @Author cec
 * @Description 校验用户实体
 * @Date 2021/10/27 14:30
 * @Param
 * @return
 **/
public class ValidateUser implements Serializable {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 图标移动ID
     */
    private String checkMoveId;
    /**
     * 宽度
     */
    private String xWidth;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCheckMoveId() {
        return checkMoveId;
    }

    public void setCheckMoveId(String checkMoveId) {
        this.checkMoveId = checkMoveId;
    }

    public String getxWidth() {
        return xWidth;
    }

    public void setxWidth(String xWidth) {
        this.xWidth = xWidth;
    }
}
