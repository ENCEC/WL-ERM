package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.daoService.MdDistrictDTO;
import com.share.auth.domain.daoService.MdDistrictVO;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 16:34
 */
public interface MdDistrictService {
    Page<MdDistrictVO> selectDistrictByCityId(MdDistrictDTO mdDistrictDTO);
}
