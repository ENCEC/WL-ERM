package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemCompanyNameDTO;
import com.share.auth.domain.daoService.MdProvinceDTO;
import com.share.auth.domain.daoService.MdProvinceVO;
import com.share.auth.model.vo.UemCompanyNameVO;

/**
 * @param
 * @Author Linja
 * @return
 * @Description 联想控件省市区的查询后台
 * @Date 2021/10/12 17:57
 */
public interface MdProvinceService {
    Page<MdProvinceVO> queryProvinceList(MdProvinceDTO mdProvinceDTO);
}
