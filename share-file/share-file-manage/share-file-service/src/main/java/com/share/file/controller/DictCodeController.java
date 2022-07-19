package com.share.file.controller;

import com.share.file.service.DictCodeService;
//import com.share.portal.domain.DictCodeVO;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 数据字典控制层
 * @date 2020/12/02
 */
@RestController
@RequestMapping("/dictCode")
public class DictCodeController {

    @Autowired
    private DictCodeService dictCodeService;

    /**
     * 根据类型编码查询数据字典明细
     *
     * @param typeCode
     * @return
     * @throws
     * @author tujx
     */
//    @GetMapping("/getDictCodeByCode")
//    @ApiOperation(value = "根据类型编码查询数据字典明细", notes = "根据类型编码查询数据字典明细")
//    public ResultHelper<List<DictCodeVO>> getDictCodeByCode(String typeCode){
//        return dictCodeService.getDictCodeByCode(typeCode);
//    }

    /**
     * 批量根据类型编码查询数据字典明细
     *
     * @param typeCodes
     * @return
     * @throws
     * @author huanghwh
     */
//    @GetMapping("/getDictCodesByCodes")
//    @ApiOperation(value = "批量根据类型编码查询数据字典明细", notes = "批量根据类型编码查询数据字典明细")
//    public ResultHelper<Map<String, List<DictCodeVO>>> getDictCodesByCodes(String []typeCodes){
//        return dictCodeService.getDictCodesByCodes(typeCodes);
//    }


}
