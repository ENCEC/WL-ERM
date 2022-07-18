package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.daoService.MdDistrictDTO;
import com.share.auth.domain.daoService.MdDistrictVO;
import com.share.auth.model.querymodels.QMdDistrict;
import com.share.auth.service.MdDistrictService;
import org.springframework.stereotype.Service;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 16:36
 */
@Service
public class MdDistrictServiceImpl implements MdDistrictService {
    @Override
    public Page<MdDistrictVO> selectDistrictByCityId(MdDistrictDTO mdDistrictDTO) {
        AndExpression expression = QMdDistrict.mdDistrictId.ne$(-1L)
                .and(QMdDistrict.isValid.eq$(true))
                .and(QMdDistrict.mdCityId.eq$(mdDistrictDTO.getMdCityId()))
                .and(QMdDistrict.districtName.like$(mdDistrictDTO.getKeyword()))
                .and(QMdDistrict.mdProvinceId.eq$(mdDistrictDTO.getMdProvinceId()));

        Page<MdDistrictVO> mdDistrictVOPage = QMdDistrict.mdDistrict.select().where(expression)
                .paging(mdDistrictDTO.getCurrentPage(),mdDistrictDTO.getPageSize())
                .mapperTo(MdDistrictVO.class)
                .execute();
        return mdDistrictVOPage;
    }
}
