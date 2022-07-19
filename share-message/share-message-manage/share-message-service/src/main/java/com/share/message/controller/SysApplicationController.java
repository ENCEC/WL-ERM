package com.share.message.controller;

import com.share.auth.domain.QueryApplicationDTO;
import com.share.message.service.SysApplicationService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 系统切换控制层
 * @date 2020/11/19
 */
@Slf4j
@RestController
@RequestMapping("/sysApplication")
@Api(value = "系统切换控制器")
public class SysApplicationController {

    @Autowired
    private SysApplicationService sysApplicationService;

    /**
     * 获取系统列表
     *
     * @param
     * @return Map
     * @throws
     * @author tujx
     */
    @GetMapping("/getSystemList")
    @ApiOperation(value = "查询系统列表", notes = "查询系统列表")
    public ResultHelper<List<QueryApplicationDTO>> getSystemList() {
        return sysApplicationService.getSystemList();
    }


    /**
     * 进行系统切换
     *
     * @param applicationCode 系统编码
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/changeApplication")
    @ApiOperation(value = "切换系统", notes = "切换系统")
    @ApiImplicitParam(name = "applicationCode", value = "切换系统编码", required = true, dataType = "String", paramType = "query")
    public ResultHelper<Object> changeApplication(String applicationCode) {
        return sysApplicationService.updateApplication(applicationCode);
    }
}
