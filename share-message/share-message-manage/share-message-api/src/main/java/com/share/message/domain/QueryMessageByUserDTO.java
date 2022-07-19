package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月28日 13:49
 */
@Data
public class QueryMessageByUserDTO {

    private Long mdMessageId;

    /**
     * 当前页数
     */
    private Integer currentPage;
    /**
     * 每页总条数
     */
    private Integer pageSize;

    /**
     * 当前登录用户ID
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;

    /**
     * 当前系统代码
     */
    private String targetSystemId;
}
