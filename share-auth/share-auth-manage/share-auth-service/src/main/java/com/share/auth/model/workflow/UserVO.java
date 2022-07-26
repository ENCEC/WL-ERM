package com.share.auth.model.workflow;

import lombok.Data;

/**
 * 角色数据--返回给工作流的固定值
 * @author wangcl
 * @date 2020/01/19
 */
@Data
public class UserVO {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色id
     */
    private String assignerId;
}
