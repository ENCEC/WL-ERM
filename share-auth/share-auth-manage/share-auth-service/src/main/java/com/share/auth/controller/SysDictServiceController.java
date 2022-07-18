package com.share.auth.controller;

import com.share.auth.domain.SysDictCodeVO;
import com.share.auth.service.SysDictService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description数据字典控制层
 * @Author: zhuyp
 * @Date: 2021/10/27 10:46
 */
@RestController
@RequestMapping("sysDict")
@Slf4j
@Api(tags = "数据字典信息操作类")
public class SysDictServiceController {
    @Autowired
    private SysDictService sysDictService;

    @ResponseBody
    @PostMapping("/selectSysDictCodeMapToCodeList")
    @ApiOperation(value = "查询数据字典数据", notes = "查询数据字典数据")
    @ApiImplicitParam(name = "SysDictCodeDTO", value = "数据字典封装类", dataType = "List<String>",type = "selectSysDictCodeMapToCodeList")
    public ResultHelper<Map<String, List<SysDictCodeVO>>> selectSysDictCodeMapToCodeList(@RequestBody List<String> dictTypeCodeList) {
        Map<String,List<SysDictCodeVO>> stringListMap = sysDictService.selectSysDictCodeMapToCodeList(dictTypeCodeList);
        return CommonResult.getSuccessResultData(stringListMap);
    }

}
