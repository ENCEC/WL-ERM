package com.share.auth.service;

import com.share.auth.domain.daoService.CargoTypeDTO;
import com.share.auth.domain.daoService.CargoTypeVO;

import java.util.List;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/26 19:48
 */
public interface CargoTypeService {
        List<CargoTypeVO> queryChosenGoods(CargoTypeDTO cargoTypeDTO);
}
