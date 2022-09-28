package com.share.auth.controller;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysTagDTO;
import com.share.auth.service.SysTagService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author cec
 * @Date 2022/7/26 11:46
 */
@RestController
@RequestMapping("sysTag")
@Api(value = "标签管理控制器")
public class SysTagController {
    @Autowired
    private SysTagService sysTagService;


    /**
     * 新增标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return Page<SysTagDTO>
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("标签新增")
    @ApiImplicitParam(name = "sysTagDTO", value = "新增接口入参", required = true, dataType = "SysTagDTO")
    @PostMapping(value = "/saveSysTag")
    public ResultHelper<?> saveSysTag(@RequestBody @Valid SysTagDTO sysTagDTO){
        return sysTagService.saveSysTag(sysTagDTO);
    }

    /**
     * 查询标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return Page<SysTagDTO>
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("根据标签名、启禁用状态查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TagName", value = "标签名称", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "status", value = "启用/禁用状态", dataTypeClass = String.class, paramType = "body")
    })
    @PostMapping("/querySysTag")
    public ResultHelper<Page<SysTagDTO>> querySysTag(@RequestBody SysTagDTO sysTagDTO) {
        return sysTagService.querySysTag(sysTagDTO);
    }
    /**
     * 标签详细
     *
     * @param sysTagId 标签ID
     * @return SysTagDTO
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("获取标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTagId", value = "ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/getSysTag")
    public ResultHelper<SysTagDTO> getSysTag(@RequestParam Long sysTagId) {
        return sysTagService.getSysTag(sysTagId);
    }
    /**
     * 启动禁止
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("启用/禁用")
    @ApiImplicitParam(name = "sysTagDTO", value = "信息封装类", required = true, dataType = "SysTagDTO")
    @PostMapping("/sysTagStartStop")
    public ResultHelper<?> sysTagStartStop(@RequestBody @Valid SysTagDTO sysTagDTO) {
        return sysTagService.sysTagStartStop(sysTagDTO);
    }

    /**
     *修改
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("修改信息")
    @PostMapping("/updateSysTag")
    @ApiImplicitParam(name = "sysTagDTO", value = "信息封装类", required = true, dataType = "SysTagDTO")
    public ResultHelper<?> updateSysTag(@RequestBody @Valid SysTagDTO sysTagDTO) {
        return sysTagService.updateSysTag(sysTagDTO);
    }

    /**
     *删除
     *
     * @param sysTagId 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @ApiOperation("删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysTagId", value = "ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/deleteSysTag")
    public ResultHelper<?> deleteTagById(@RequestParam Long sysTagId) {
        return sysTagService.deleteTagById(sysTagId);
    }
}
