package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.daoService.MdCityDTO;
import com.share.auth.domain.daoService.MdCityVO;
import com.share.auth.model.querymodels.QMdCity;
import com.share.auth.service.MdCityService;
import org.springframework.stereotype.Service;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 11:45
 */
@Service
public class MdCityServiceImpl implements MdCityService {
    @Override
    public Page<MdCityVO> selectCityByProvinceCode(MdCityDTO mdCityDTO) {
        AndExpression expression = QMdCity.mdCityId.ne$(-1L)
                .and(QMdCity.mdProvinceId.eq$(mdCityDTO.getMdProvinceId()))
                .and(QMdCity.cityName.like$(mdCityDTO.getKeyword()))
                .and(QMdCity.isValid.eq$(true));

        Page<MdCityVO> mdCityVOPage = QMdCity.mdCity.select()
                .where(expression)
                .paging(mdCityDTO.getCurrentPage(),mdCityDTO.getPageSize())
                .mapperTo(MdCityVO.class)
                .execute();
        return mdCityVOPage;
    }
}
