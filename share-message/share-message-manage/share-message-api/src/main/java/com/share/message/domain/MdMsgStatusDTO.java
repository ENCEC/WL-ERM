package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.util.List;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月31日 11:41
 */
@Data
public class MdMsgStatusDTO {


    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;

    private List<Long> mdMessageIdList;

    /**
     * 当前系统代码
     */
    private String targetSystemId;
}
