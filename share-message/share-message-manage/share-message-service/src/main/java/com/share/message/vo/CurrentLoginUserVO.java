package com.share.message.vo;

import lombok.Data;

/**
 * @author tujx
 * @description 当前登录用户信息
 * @date 2020/12/29
 */
@Data
public class CurrentLoginUserVO {

    /**
     * 用户名
     */
    private String name;

    /**
     * 所属企业
     */
    private LoginCompany uemCompany;


    /**
     * 所属企业信息
     */
    @Data
    public class LoginCompany {
        /**
         * 企业中文名称
         */
        private String companyNameCn;

    }
}
