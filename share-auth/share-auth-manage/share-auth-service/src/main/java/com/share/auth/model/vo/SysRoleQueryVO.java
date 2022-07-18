package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

/**
 * @Author:chenxf
 * @Description:  角色查询入参
 * @Date: 14:22 2020/11/16
 * @Param:
 * @Return:
 *
 */
@Data
public class SysRoleQueryVO {
    /**
     * 每页限制行数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /** 应用id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /** 角色名称*/
    private String roleName;
    /** 父级角色id*/
    private Long pid;
    /** 启/禁用状态*/
    private Boolean isValid;

    /**用户id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;
}
