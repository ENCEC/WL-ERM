package com.share.auth.controller;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.PageDto;
import com.share.auth.domain.SysDictCodeDto;
import com.share.auth.domain.SysDictTypeDto;
import com.share.auth.domain.daoService.SysDictCodeDTO;
import com.share.auth.model.entity.SysDictCode;
import com.share.auth.model.entity.SysDictType;
import com.share.auth.service.SysDictTypeService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Api("基础管理")
@Controller
@RequestMapping("sysDictType")
@Slf4j
public class SysDictTypeController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 基础管理 数据字典 按条件分页查询
     * @param sysDictTypeDto 数据字典表映射表
     * @param pageDto 分页映射
     * @return Page<SysDictType>
     * @author xrp
     * */
    @ApiOperation("基础管理 数据字典 按条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictTypeDto", value = "查询条件入参", required = true, dataType = "SysDictTypeDto", paramType = "queryByPage"),
            @ApiImplicitParam(name = "pageDto", value = "分页参数", required = true, dataType = "PageDto", paramType = "queryByPage")
    })

    @GetMapping("/queryByPage")
    @ResponseBody
    public ResultHelper<Page<SysDictTypeDto>> queryByPage(SysDictTypeDto sysDictTypeDto, PageDto pageDto) {
        Page<SysDictTypeDto> sysDictTypeDtoPage = sysDictTypeService.queryByPage(sysDictTypeDto, pageDto);
        return CommonResult.getSuccessResultData(sysDictTypeDtoPage);
    }

    /**
     * 基础管理 数据字典 新增字典
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 新增字典")
    @ApiImplicitParam(name = "sysDictTypeDto", value = "数据字典实体", required = true, dataType = "SysDictTypeDto", paramType = "saveSysDictType")
    @PostMapping("/saveSysDictType")
    @ResponseBody
    public ResultHelper<Object> saveSysDictType(@RequestBody SysDictTypeDto sysDictTypeDto) {
        return sysDictTypeService.saveSysDictType(sysDictTypeDto);
    }
    /**
     * 基础管理 数据字典 修改字典
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 修改字典")
    @ApiImplicitParam(name = "sysDictTypeDto", value = "数据字典实体", required = true, dataType = "SysDictTypeDto", paramType = "updateSysDictType")
    @PostMapping("/updateSysDictType")
    @ResponseBody
    public ResultHelper<Object> updateSysDictType(@RequestBody SysDictTypeDto sysDictTypeDto) {
        return sysDictTypeService.updateSysDictType(sysDictTypeDto);
    }

    /**基础管理 数据字典 详情
     * @param sysDictTypeId 数据字典表ID
     * @return List<SysDictType>
     * @author xrp
     * */
    @ApiOperation("基础管理 数据字典 详情")
    @ApiImplicitParam(name = "sysDictTypeId", value = "数据字典表Id", required = true, dataType = "String", paramType = "getSysDictType")
    @GetMapping("/getSysDictType")
    @ResponseBody
    public ResultHelper<List<SysDictType>> getSysDictType(@RequestParam String sysDictTypeId) {
        List<SysDictType> sysDictTypeList = sysDictTypeService.getSysDictType(sysDictTypeId);
        return CommonResult.getSuccessResultData(sysDictTypeList);
    }

    /**
     * 基础管理 数据字典 启停
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 启停")
    @ApiImplicitParam(name = "sysDictTypeDto", value = "数据字典表dto", required = true, dataType = "SysDictTypeDto", paramType = "updateDictTypeStatus")
    @PostMapping("/updateDictTypeStatus")
    @ResponseBody
    public ResultHelper<Object> updateDictTypeStatus(@RequestBody SysDictTypeDto sysDictTypeDto) {
        return sysDictTypeService.updateDictTypeStatus(sysDictTypeDto);
    }

    /**基础管理 数据字典 配置项分页查询
     * @param sysDictCodeDto 数据字典配置项表映射
     * @param pageDto 分页
     * @return Page<SysDictCodeDto>
     * @author xrp
     * */
    @ApiOperation("基础管理 数据字典 配置项分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDictCodeDto", value = "查询条件入参", required = true, dataType = "SysDictCodeDto", paramType = "queryByDictTypeId"),
            @ApiImplicitParam(name = "pageDto", value = "分页参数", required = true, dataType = "PageDto", paramType = "queryByDictTypeId")
    })
    @GetMapping("/queryByDictTypeId")
    @ResponseBody
    public ResultHelper<Page<SysDictCodeDto>> queryByDictTypeId(SysDictCodeDto sysDictCodeDto, PageDto pageDto) {
        Page<SysDictCodeDto> sysDictCodeDtoPage = sysDictTypeService.queryByDictTypeId(sysDictCodeDto, pageDto);
        return CommonResult.getSuccessResultData(sysDictCodeDtoPage);
    }


    /**
     * 基础管理 数据字典 配置项新增
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 配置项新增")
    @ApiImplicitParam(name = "sysDictCodeDto", value = "数据字典配置项表dto", required = true, dataType = "SysDictCodeDto", paramType = "saveSysDictCode")
    @PostMapping("/saveSysDictCode")
    @ResponseBody
    public ResultHelper<Object> saveSysDictCode(@RequestBody SysDictCodeDto sysDictCodeDto) {
        return sysDictTypeService.saveSysDictCode(sysDictCodeDto);
    }


    /**
     * 基础管理 数据字典 配置项修改
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 配置项修改")
    @ApiImplicitParam(name = "sysDictCodeDto", value = "数据字典配置项表dto", required = true, dataType = "SysDictCodeDto", paramType = "updateSysDictCode")
    @PostMapping("/updateSysDictCode")
    @ResponseBody
    public ResultHelper<Object> updateSysDictCode(@RequestBody SysDictCodeDto sysDictCodeDto) {
        return sysDictTypeService.updateSysDictCode(sysDictCodeDto);
    }

    /**基础管理 数据字典 配置项详情
     * @param sysDictCodeId 数据字典配置项ID
     * @return List<SysDictCode>
     * @author xrp
     * */
    @ApiOperation("基础管理 数据字典 配置项详情")
    @ApiImplicitParam(name = "sysDictCodeId", value = "数据字典配置项ID", required = true, dataType = "String", paramType = "getSysDictCode")
    @GetMapping("/getSysDictCode")
    @ResponseBody
    public ResultHelper<List<SysDictCode>> getSysDictCode(@RequestParam String sysDictCodeId) {
        List<SysDictCode> sysDictCodeList = sysDictTypeService.getSysDictCode(sysDictCodeId);
        return CommonResult.getSuccessResultData(sysDictCodeList);
    }

    /**
     * 基础管理 数据字典 配置项 启停
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("基础管理 数据字典 配置项 启停")
    @ApiImplicitParam(name = "sysDictCodeDto", value = "数据字典配置项表映射", required = true, dataType = "SysDictCodeDto", paramType = "updateDictCodeStatus")
    @PostMapping("/updateDictCodeStatus")
    @ResponseBody
    public ResultHelper<Object> updateDictCodeStatus(@RequestBody SysDictCodeDto sysDictCodeDto) {
        return sysDictTypeService.updateDictCodeStatus(sysDictCodeDto);
    }


    /**
     * @Author Linja
     * @param dictTypeCodeList
     * @return
     */
    @ApiOperation("基础管理 数据字典")
    @ApiImplicitParam(name = "sysDictCodeDto", value = "数据字典配置项表映射", required = true, dataType = "SysDictCodeDto", paramType = "updateDictCodeStatus")
    @PostMapping("/selectSysDictCodeMapToCodeList")
    @ResponseBody
    public ResultHelper<Object> selectSysDictCodeMapToCodeList(@RequestBody List<String> dictTypeCodeList) {
        Map<String,List<SysDictCodeDTO>> stringListMap = sysDictTypeService.selectSysDictCodeMapToCodeList(dictTypeCodeList);
        return CommonResult.getSuccessResultData(stringListMap);
    }

    /**
     * 查询数据字典
     * @param sysDictTypeDto
     * @return
     */
    @PostMapping("/querySysDictCodeByDictType")
    @ResponseBody
    public ResultHelper<List<SysDictCodeDTO>> querySysDictCodeByDictType(@RequestBody SysDictTypeDto sysDictTypeDto) {
        return sysDictTypeService.querySysDictCodeByDictType(sysDictTypeDto);
    }
}
