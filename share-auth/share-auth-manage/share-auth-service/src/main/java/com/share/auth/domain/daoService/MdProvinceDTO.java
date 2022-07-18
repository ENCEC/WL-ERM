package com.share.auth.domain.daoService;

import io.swagger.annotations.Api;
import lombok.Data;

import java.security.PrivateKey;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/12 18:01
 */
@Api("省份查询条件DTO类")
@Data
public class MdProvinceDTO {
    private String  keyword;
    private Integer currentPage;
    private Integer pageSize;
    private String provinceCode;
}
