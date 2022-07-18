package com.share.auth.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 企业配置权限VO
 * @author chenhy
 * @date 20210715
 */
@Data
public class UemCompanyConfigPermissionVO implements Serializable {
    private static final long serialVersionUID = 1;

    /**应用名称*/
    private String applicationName;

    /**企业权限信息VO*/
    private List<UemCompanyRoleVO> uemCompanyRoleList;

}
