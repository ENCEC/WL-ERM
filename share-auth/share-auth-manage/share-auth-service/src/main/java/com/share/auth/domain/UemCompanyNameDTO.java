package com.share.auth.domain;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;

/**
 * @date 2021-10-5
 * @author cjh
 * */
@Api("联想控件企业名查询")
@Data
public class UemCompanyNameDTO implements Serializable {

    // 关键字
    private String keyword;

    // 当前页
    private Integer currentPage;

    // 分页大小
    private Integer pageSize;
}
