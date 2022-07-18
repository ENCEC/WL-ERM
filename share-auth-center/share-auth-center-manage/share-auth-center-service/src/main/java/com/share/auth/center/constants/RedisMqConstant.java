package com.share.auth.center.constants;

/**
 * @Author:chenxf
 * @Description: Redis相关常量
 * @Date: 10:46 2020/12/21
 * @Param:
 * @Return:
 *
 */
public class RedisMqConstant {

    private RedisMqConstant() {
    }

    /**用户信息Key*/
    public static final String USER_INFO_KEY_PRE = "USER_INFO_";

    /**用户账号锁定前缀*/
    public static final String ACCOUNT_LOCKED_KEY_PRE = "ACCOUNT_LOCKED_";

    /**用户账号锁定秒数*/
    public static final Long ACCOUNT_LOCKED_SECONDS = 600L;

}
