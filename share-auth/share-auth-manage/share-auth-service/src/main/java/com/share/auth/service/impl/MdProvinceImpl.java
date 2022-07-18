package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.daoService.MdProvinceDTO;
import com.share.auth.domain.daoService.MdProvinceVO;
import com.share.auth.model.querymodels.QMdCity;
import com.share.auth.model.querymodels.QMdProvince;
import com.share.auth.model.querymodels.QUemCompany;
import com.share.auth.service.MdProvinceService;
import org.springframework.stereotype.Service;

import java.beans.Expression;
import java.util.ArrayList;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/12 18:55
 */
@Service
public class MdProvinceImpl implements MdProvinceService {


    @Override
    public Page<MdProvinceVO> queryProvinceList(MdProvinceDTO mdProvinceDTO) {

        AndExpression expression = QMdProvince.mdProvinceId.ne$(-1L)
                .and(QMdProvince.provinceNameCn.like$(mdProvinceDTO.getKeyword())
                .and(QMdProvince.isValid.eq$(true)));

        Iterable<Expression> sorting = new ArrayList<>();


//        ((ArrayList<Expression>) sorting).add(QMdProvince.provinceNameCn.asc())
        Page<MdProvinceVO> provinceVOPage = QMdProvince.mdProvince.select().where(expression)
                .paging(mdProvinceDTO.getCurrentPage(),mdProvinceDTO.getPageSize())
                .mapperTo(MdProvinceVO.class)
                .execute();

        return provinceVOPage;
    }
}
