package com.share.auth.controller;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ec.core.utils.ResultUtils;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;
import com.share.auth.service.SysTechnicalTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName SySTechnicalTitleController
 * @Description 岗位职称Controller层
 * @Author weiq
 * @Date 2022/7/25 16:38
 * @Version 1.0
 **/
@Api("岗位职称")
@RestController
@Slf4j
@RequestMapping("/sysTechnicalTitle")
public class SysTechnicalTitleController {

    @Autowired
    private SysTechnicalTitleService sysTechnicalTitleService;

    /**
     * 分页查询全部岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @ApiOperation("获取全部的岗位职称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "分页页数", dataTypeClass = Integer.class, paramType = "body"),
            @ApiImplicitParam(name = "pageSize", value = "分页条数", dataTypeClass = Integer.class, paramType = "body"),
    })
    @GetMapping ("/queryByPageAll")
    public Page<SysTechnicalTitleAndPostVO> queryByPageAll(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
       return sysTechnicalTitleService.queryByPageAll(sysTechnicalTitleAndPostVO);
    }

    /**
     * 通过条件分页查询岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @ApiOperation("根据职称名称、所属岗位或启禁用状态查询岗位职称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "分页页数", dataTypeClass = Integer.class, paramType = "body"),
            @ApiImplicitParam(name = "pageSize", value = "分页条数", dataTypeClass = Integer.class, paramType = "body"),
            @ApiImplicitParam(name = "status", value = "状态", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "postName", value = "所属岗位", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "technicalName", value = "职称名称", dataTypeClass = String.class, paramType = "body"),
    })
    @GetMapping ("/queryByTechnicalTitleName")
    public Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        return sysTechnicalTitleService.queryByTechnicalTitleName(sysTechnicalTitleAndPostVO);
    }

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @ApiOperation("新增职称信息")
    @ApiImplicitParam(name = "sysTechnicalTitleAndPostVO", value = "岗位职称封装类", required = true, dataType = "SysTechnicalTitleAndPostVO")
    @PostMapping("/saveSysTechnicalTitle")
    public Map<String,Object> saveSysTechnicalTitle(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO ) {
        sysTechnicalTitleService.saveSysTechnicalTitle(sysTechnicalTitleAndPostVO);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @ApiOperation("编辑职称信息")
    @ApiImplicitParam(name = "sysTechnicalTitleAndPostVO", value = "岗位职称封装类", required = true, dataType = "SysTechnicalTitleAndPostVO")
    @PostMapping("/updateSysTechnicalTitle")
    public Map<String,Object> updateSysTechnicalTitle(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        sysTechnicalTitleService.updateSysTechnicalTitle(sysTechnicalTitleAndPostVO);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 删除职称
     * @param technicalTitleId
     * @return
     */
    @ApiOperation("删除职称信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "technicalName", value = "职称名称", required = true, dataTypeClass = String.class, paramType = "body")
    })
    @DeleteMapping("/deleteSysTechnicalTitle")
    public Map<String,Object> deleteSysTechnicalTitle(Long technicalTitleId) {
        sysTechnicalTitleService.deleteSysTechnicalTitle(technicalTitleId);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 启用/禁用岗位职称
     * @param sysTechnicalTitle
     * @return
     */
    @ApiOperation("启用/禁用岗位职称")
    @ApiImplicitParam(name = "sysTechnicalTitle", value = "岗位职称类", required = true, dataType = "SysTechnicalTitle")
    @GetMapping("/updateStatus")
    public Map<String,Object> updateStatus(SysTechnicalTitle sysTechnicalTitle) {
        sysTechnicalTitleService.updateStatus(sysTechnicalTitle);
        return ResultUtils.getSuccessResultData(true);
    }

}
