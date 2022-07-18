package com.share.auth.center.enums;

/**
 * 地址映射枚举
 * @author wangcl
 * @date 2020/01/13
 */
public enum AuthAddressEnum {
    /**
     * 开发环境
     */
    DEV("http://172.16.8.248:9542","http://172.16.8.248:9542"),
    /**
     * 测试环境
     */
    SIT("http://172.16.10.222:10003","http://117.149.228.171:10003"),
    /**
     * uat环境
     */
    UAT("http://172.16.10.231:10003","http://117.149.228.204:10003"),
    /**
     * 生产环境
     */
    PRD("http://172.16.10.231:10003","http://117.149.228.204:10003");

    private String innerAdd;

    private String outerAdd;

    public String getInnerAdd() {
        return innerAdd;
    }

    public String getOuterAdd() {
        return outerAdd;
    }


    AuthAddressEnum(String innerAdd, String outerAdd) {
        this.innerAdd = innerAdd;
        this.outerAdd = outerAdd;
    }

    /**
     * 根据外网地址获取内网地址
     * @param outerAdd
     * @return
     */
    public static String getInnerByOuter(String outerAdd){
        for(AuthAddressEnum auth : AuthAddressEnum.values()){
            if(outerAdd.equals(auth.getOuterAdd())){
                return auth.getInnerAdd();
            }
        }
        return null;
    }
}
