package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.model.vo.UemUserPermissionVO;
import com.share.auth.service.UemUserPermissionService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限审核Controller
 * @author chenhy
 * @date 2021-05-19
 */
@Slf4j
@RestController
@Api(tags = "权限Controller")
@RequestMapping("/uemUserPermission")
public class UemUserPermissionController {

    @Autowired
    private UemUserPermissionService uemUserPermissionService;

    /**
     * 保存权限申请信息
     * @param uemUserPermissionVO 权限申请信息
     * @return 保存结果
     */
    @ApiOperation(value = "保存权限申请信息")
    @PostMapping(value = "/saveUemUserPermission")
    public ResultHelper<String> saveUemUserPermission(@RequestBody UemUserPermissionVO uemUserPermissionVO) {
        uemUserPermissionService.saveUemUserPermission(uemUserPermissionVO);
        return CommonResult.getSuccessResultData();
    }

    /**
     * 查询权限申请列表
     * @param uemUserPermissionVO 权限vo
     * @return 权限申请列表
     */
    @ApiOperation(value = "查询权限申请列表")
    @GetMapping(value = "/queryUemUserPermission")
    public ResultHelper<Page<UemUserPermissionVO>> queryUemUserPermission(UemUserPermissionVO uemUserPermissionVO) {
        Page<UemUserPermissionVO> uemUserPermissionPage = uemUserPermissionService.queryUemUserPermission(uemUserPermissionVO);
        return CommonResult.getSuccessResultData(uemUserPermissionPage);
    }

    /**
     * 查询权限申请详情
     * @param uemUserPermissionId 权限id
     * @return 权限申请详情
     */
    @ApiOperation(value = "查询权限申请详情")
    @ApiImplicitParam(name = "uemUserPermissionId", value = "权限申请id", required = true, dataType = "Long", paramType = "getUemUserPermissionById")
    @GetMapping(value = "/getUemUserPermissionById")
    public ResultHelper<UemUserPermissionVO> getUemUserPermissionById(@RequestParam Long uemUserPermissionId) {
        UemUserPermissionVO uemUserPermission = uemUserPermissionService.getUemUserPermissionById(uemUserPermissionId);
        return CommonResult.getSuccessResultData(uemUserPermission);
    }

    /**
     * 权限申请审核
     * @param uemUserPermissionVO 审核信息
     * @return 权限申请审核结果
     */
    @ApiOperation(value = "权限申请审核")
    @PostMapping(value = "/auditUemUserPermission")
    public ResultHelper<String> auditUemUserPermission(@RequestBody UemUserPermissionVO uemUserPermissionVO) {
        uemUserPermissionService.auditUemUserPermission(uemUserPermissionVO);
        return CommonResult.getSuccessResultData();
    }

}
