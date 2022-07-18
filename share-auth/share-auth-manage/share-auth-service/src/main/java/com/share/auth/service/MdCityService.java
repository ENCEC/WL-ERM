package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.daoService.MdCityDTO;
import com.share.auth.domain.daoService.MdCityVO;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 11:32
 */
public interface MdCityService {
    Page<MdCityVO> selectCityByProvinceCode(MdCityDTO mdCityDTO);
}
