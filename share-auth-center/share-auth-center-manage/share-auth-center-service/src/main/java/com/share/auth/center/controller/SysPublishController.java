package com.share.auth.center.controller;

import com.share.auth.center.service.SysPublishConfigService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wangzicheng
 * @description
 * @date 2021年04月12日 22:09
 */
@Api("查询系统发布时间")
@RestController
@RequestMapping("/sysPublishConfig")
@Slf4j
public class SysPublishController {

    @Autowired
    private SysPublishConfigService sysPublishConfigService;

    @RequestMapping(value = "/getTimes", method = RequestMethod.GET)
    public String getTimes() {
       return sysPublishConfigService.getTimes();
    }
}
