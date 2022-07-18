package com.share.auth.service.impl;

import com.share.auth.domain.daoService.CargoTypeDTO;
import com.share.auth.domain.daoService.CargoTypeVO;
import com.share.auth.model.querymodels.QUemCompanyCargoType;
import com.share.auth.service.CargoTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/26 20:07
 */
@Service
public class CargoTypeServiceImpl implements CargoTypeService {
    @Override
    public List<CargoTypeVO> queryChosenGoods(CargoTypeDTO cargoTypeDTO) {
        List<CargoTypeVO> cargoTypeVOS = QUemCompanyCargoType.uemCompanyCargoType
                .select(QUemCompanyCargoType.cargoTypeCode,QUemCompanyCargoType.cargoType.as("dictName"))
                .where(QUemCompanyCargoType.uemCompanyId
                        .eq$(cargoTypeDTO.getUemCompanyCargoTypeId())
                        .and(QUemCompanyCargoType.cargoType.eq$(cargoTypeDTO.getCargoType())))
                .mapperTo(CargoTypeVO.class).execute();
        return cargoTypeVOS;
    }
}
