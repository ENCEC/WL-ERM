package com.share.file.service.impl;

import com.share.file.service.DictCodeService;
//import com.share.portal.domain.DictCodeVO;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 数据字段接口实现
 * @date 2020/12/02
 */
@Service
public class DictCodeServiceImpl implements DictCodeService {

    /**
     * 根据类型编码查询数据字典明细
     *
     * @param typeCode 数据类型编码
     * @return ResultHelper<List<DictCodeVO>>
     * @throws
     * @author tujx
     */
//    @Override
//    public ResultHelper<List<DictCodeVO>> getDictCodeByCode(String typeCode) {
////        return portalDictService.getDictCodeByCode(typeCode);
//        return null;
//    }

    /**
     * 批量根据类型编码查询数据字典明细
     *
     * @param typeCodes 数据类型编码
     * @return ResultHelper<List<DictCodeVO>>
     * @throws
     * @author huanghwh
     */
//    @Override
//    public ResultHelper<Map<String, List<DictCodeVO>>> getDictCodesByCodes(String []typeCodes) {
////        return portalDictService.getDictCodesByCodes(typeCodes);
//        return null;
//    }
}
