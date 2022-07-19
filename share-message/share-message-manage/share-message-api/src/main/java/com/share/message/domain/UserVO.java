package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Builder;
import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 14:30
 */
@Data
@Builder
public class UserVO {

    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long userId;

    private String userName;

    private Boolean isReaded = false;
}
