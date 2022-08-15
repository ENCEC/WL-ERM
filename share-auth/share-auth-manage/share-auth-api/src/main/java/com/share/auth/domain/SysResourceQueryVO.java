package com.share.auth.domain;

import lombok.Data;

/**
 * @author chenxf
 * @date 2020-10-26 16:28
 */
@Data
public class SysResourceQueryVO {

    private String clientId;

    private Long uid;

    private Long uemUserId;

    /** 调度系统用户当前角色id */
    private Long sysRoleId;

    /** 按钮所属页面的资源URL */
    private String resourceUrl;

    private Long sysApplicationId;

}
