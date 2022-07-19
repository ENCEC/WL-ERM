package com.share.message.domain;

import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 16:34
 */
@Data
public class QueryMsgMessageTemplateDTO {

    public QueryMsgMessageTemplateDTO(Integer pageNow, Integer pageSize) {
        this.pageNow = pageNow;
        this.pageSize = pageSize;
    }

    /**
     *消息模板编号
     */
    private String msgTemplateCode;
    /**
     *消息模板名称
     */
    private String msgTemplateName;
    /**
     * 当前页数
     */
    private Integer pageNow;
    /**
     * 每页总条数
     */
    private Integer pageSize;
}
