package com.share.message.controller;

import com.share.message.service.DictCodeService;
//import com.share.portal.domain.DictCodeVO;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    /**
//     * 根据类型编码查询数据字典明细
//     *
//     * @param typeCode 类型编码
//     * @return ResultHelper<List<DictCodeVO>>
//     * @throws
//     * @author tujx
//     */
//    @GetMapping("/getDictCodeByCode")
//    @ApiOperation(value = "根据类型编码查询数据字典明细", notes = "根据类型编码查询数据字典明细")
//    @ApiImplicitParam(name = "typeCode", value = "类型编码", required = true, dataType = "String", paramType = "query")
//    public ResultHelper<List<DictCodeVO>> getDictCodeByCode(String typeCode){
//        return dictCodeService.getDictCodeByCode(typeCode);
//    }


}
