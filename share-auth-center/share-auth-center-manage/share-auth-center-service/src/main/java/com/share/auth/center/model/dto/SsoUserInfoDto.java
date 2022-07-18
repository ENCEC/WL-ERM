package com.share.auth.center.model.dto;

/**
 * @Author:chenxf
 * @Description: 用户信息
 * @Date: 16:15 2021/1/22
 * @Param: 
 * @Return:
 *
 */
public class SsoUserInfoDto {
    private String mail;
    private String mobile;
    private String description;
    private String cn;
    private String userLvl;
    private String userCode;
    private String deptmentTitle;
    private String headship;
    private String uid;
    private String accountstatu;
    private String deptment;
    private String account;
    private String fixphone;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(String userLvl) {
        this.userLvl = userLvl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDeptmentTitle() {
        return deptmentTitle;
    }

    public void setDeptmentTitle(String deptmentTitle) {
        this.deptmentTitle = deptmentTitle;
    }

    public String getHeadship() {
        return headship;
    }

    public void setHeadship(String headship) {
        this.headship = headship;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccountstatu() {
        return accountstatu;
    }

    public void setAccountstatu(String accountstatu) {
        this.accountstatu = accountstatu;
    }

    public String getDeptment() {
        return deptment;
    }

    public void setDeptment(String deptment) {
        this.deptment = deptment;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFixphone() {
        return fixphone;
    }

    public void setFixphone(String fixphone) {
        this.fixphone = fixphone;
    }
}
