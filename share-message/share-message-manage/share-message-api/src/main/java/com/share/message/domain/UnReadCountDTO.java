package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月31日 11:25
 */
@Data
public class UnReadCountDTO {


    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;

    private String targetSystemId;

}
