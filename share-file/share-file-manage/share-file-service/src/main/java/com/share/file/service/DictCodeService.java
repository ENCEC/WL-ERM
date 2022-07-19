package com.share.file.service;

//import com.share.portal.domain.DictCodeVO;
import com.share.support.result.ResultHelper;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 数据字典接口
 * @date 2020/12/02
 */
public interface DictCodeService {


    /**
     * 根据类型编码查询数据字典明细
     *
     * @param type  数据类型编码
     * @return ResultHelper<List<DictCodeVO>>
     * @throws
     * @author tujx
     */
//    ResultHelper<List<DictCodeVO>> getDictCodeByCode(String type);


    /**
     * 批量根据类型编码查询数据字典明细
     *
     * @param types  数据类型编码
     * @return ResultHelper<List<DictCodeVO>>
     * @throws
     * @author huanghwh
     */
//    ResultHelper<Map<String, List<DictCodeVO>>> getDictCodesByCodes(String []types);
}
