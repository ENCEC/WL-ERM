package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ec.core.utils.ResultUtils;
import com.share.auth.domain.SysPostDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.model.entity.SysPost;
import com.share.auth.service.SysPostService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanjp
 * @Date 2022/7/26 11:46
 */
@RestController
@RequestMapping("SysPost")
@Api(value = "岗位管理控制器")
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;

    @PostMapping("addSysPost")
    @ApiOperation(value = "新增岗位")
    @ApiImplicitParam(name = "sysPost", value = "岗位信息新增修改接口入参", required = true, dataType = "SysPost", paramType = "addSysPost")
    public Map<String, Object> addSysPost(@RequestBody SysPost sysPost){
        sysPostService.addSysPost(sysPost);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 查询岗位信息
     *
     * @param sysPostDTO 岗位信息封装类
     * @return Page<SysPostDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("根据岗位名、启禁用状态查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postName", value = "岗位名称", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "status", value = "启用/禁用状态", dataTypeClass = Boolean.class, paramType = "body")
    })
    @PostMapping("/queryUemUser")
    public ResultHelper<Page<SysPostDTO>> querySysPost(@RequestBody SysPostDTO sysPostDTO) {
        return sysPostService.querySysPost(sysPostDTO);
    }
    /**
     * 岗位详细
     *
     * @param sysPostId 岗位ID
     * @return SysPostDTO
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("获取岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPostId", value = "ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/getUemUser")
    public ResultHelper<SysPostDTO> getSysPost(@RequestParam Long sysPostId) {
        return sysPostService.getSysPost(sysPostId);
    }
    /**
     * 启动禁止
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("启用/禁用")
    @ApiImplicitParam(name = "sysPostDTO", value = "信息封装类", required = true, dataType = "SysPostDTO")
    @PostMapping("/uemUserStartStop")
    public ResultHelper<?> sysPostStartStop(SysPostDTO sysPostDTO) {
        return sysPostService.sysPostStartStop(sysPostDTO);
    }

    /**
     *修改
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("修改信息")
    @PostMapping("/editUemUser")
    @ApiImplicitParam(name = "sysPostDTO", value = "信息封装类", required = true, dataType = "SysPostDTO")
    public ResultHelper<?> updatePostStartStop(@RequestBody @Valid SysPostDTO sysPostDTO) {
        return sysPostService.updatePostStartStop(sysPostDTO);
    }

    /**
     *删除
     *
     * @param sysPostId 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPostId", value = "ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @PostMapping("/deleteUemUser")
    public ResultHelper<?> deletePostById(@RequestParam Long sysPostId) {
        return sysPostService.deletePostById(sysPostId);
    }
}
