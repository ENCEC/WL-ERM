package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemProjectDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserProjectDto;
import com.share.auth.service.UemProjectService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.share.auth.model.entity.UemUserProject;

import java.util.Collections;
import java.util.List;

/**
 * @author tanjp
 * @Date 2022/7/29 10:37
 */
@RestController
@RequestMapping("uemProject")
@Api(value = "部门项目控制器")
public class UemProjectController {

    @Autowired
    private UemProjectService uemProjectService;
    /**
     * 查询岗位信息
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return Page<UemProjectDTO>
     * @author tanjp
     * @date 2022/7/29
     */
    @ApiOperation("根据项目名称、项目经理、项目客户、启禁用状态查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "dutyName", value = "项目经理", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "customer", value = "项目客户", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "status", value = "启用/禁用状态", dataTypeClass = String.class, paramType = "body")
    })
    @PostMapping("/queryUemProject")
    public ResultHelper<Page<UemProjectDTO>> selectUemProject(@RequestBody UemProjectDTO uemProjectDTO) {
        return uemProjectService.selectUemProject(uemProjectDTO);
    }

    /**
     *新增项目
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    @PostMapping("/addUemProject")
    public ResultHelper<UemProjectDTO> addUemProject(@RequestBody UemProjectDTO uemProjectDTO) {
        return uemProjectService.addUemProject(uemProjectDTO);
    }

    /**
     *删除
     *
     * @param uemProjectById 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @ApiOperation("删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemProjectById", value = "ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/deleteUemProject")
    public ResultHelper<?> deleteUemProjectById(@RequestParam Long uemProjectById) {
        return uemProjectService.deleteUemProjectById(uemProjectById);
    }

    /**
     *项目编辑
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    @ApiOperation("修改信息")
    @PostMapping("/updateUemProject")
    @ApiImplicitParam(name = "uemProjectDTO", value = "信息封装类", required = true, dataType = "SysPostDTO")
    public ResultHelper<UemProjectDTO> updateUemProject(@RequestBody UemProjectDTO uemProjectDTO) {
        return uemProjectService.updateUemProject(uemProjectDTO);
    }

    /**
     * 联想控件参数
     *
     * @param uemUserDto 传入名字
     * @return ResultHelper<Page<UemUserDto>>
     * @author tanjp
     * @date 2022/7/29
     */
    @ApiOperation("联想控件查找")
    @PostMapping("/queryUemUserName")
    @ApiImplicitParam(name = "uemProjectDTO", value = "信息封装类", required = true, dataType = "SysPostDTO")
    ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemProjectService.queryUemUser(uemUserDto);
    }

    /**
     * 根据开发成员和需求成员的id  查找
     *
     * @param   ViewDetailID
     * @return ResultHelper<List<String>>
     * @author tanjp
     * @date 2022/7/29
     */
    @ApiOperation("查找开发成员和需求成员")
    @GetMapping("/queryViewDetailById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ViewDetailID", value = "ID", required = true, dataTypeClass = String.class, paramType = "body")
    })
    ResultHelper<List<String>> selectViewDetailById(@RequestParam String ViewDetailID) {
        return  uemProjectService.selectViewDetailById(ViewDetailID);
    }

    @PostMapping("/selectUemUserProject")
    public ResultHelper<Page<UemUserProjectDto>> selectUserProject(@RequestBody UemUserDto uemUserDto){
        int pageSize = uemUserDto.getPageSize();
        int pageNo = uemUserDto.getPageNo();
        Page<UemUserProjectDto> uemUserProjectPage= uemProjectService.selectUserProject(pageNo,pageSize);
        return CommonResult.getSuccessResultData(uemUserProjectPage);
    }

}
