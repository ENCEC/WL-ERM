package com.share.auth.domain.daoService;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 15:57
 */@Api("区县查询条件DTO类")
@Data
public class MdDistrictDTO {
    private String  keyword;
    private Integer currentPage;
    private Integer pageSize;
    private Long mdCityId;
    private Long mdProvinceId;
}
