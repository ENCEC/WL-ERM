package com.share.auth.domain;

import lombok.Data;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Data
public class PageDto {

    /**
     * 当前页数
     */
    private Integer pageNo;
    /**
     * 当前显示条数
     */
    private Integer pageSize;
}
