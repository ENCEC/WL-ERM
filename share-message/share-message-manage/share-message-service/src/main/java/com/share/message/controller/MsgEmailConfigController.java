package com.share.message.controller;

import com.share.message.vo.MsgEmailConfigVO;
import com.share.message.service.MsgEmailConfigService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 邮件发送配置控制器
 *
 * @author tujx
 * @date 2020/10/16
 */
@Slf4j
@RestController
@RequestMapping("/msgEmailConfig")
@Api(value = "邮件发送配置控制器")
public class MsgEmailConfigController {

    @Autowired
    private MsgEmailConfigService msgEmailConfigService;


    /**
     * 新增邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/saveEmailConfig")
    @ApiOperation(value = "新增邮件发送配置", notes = "新增邮件发送配置")
    public ResultHelper<String> saveEmailConfig(@RequestBody MsgEmailConfigVO msgEmailConfigVO) {
        return msgEmailConfigService.saveEmailConfig(msgEmailConfigVO);
    }


    /**
     * 编辑邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/updateEmailConfig")
    @ApiOperation(value = "编辑邮件发送配置", notes = "编辑邮件发送配置")
    public ResultHelper<String> updateEmailConfig(@RequestBody MsgEmailConfigVO msgEmailConfigVO) {
        return msgEmailConfigService.updateEmailConfig(msgEmailConfigVO);
    }


    /**
     * 停用/启用邮件发送配置
     *
     * @param msgEmailConfigId 邮件发送配置主键
     * @param disabledTemplate 是否同时停用相关模板，0：否，1：是
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/updateEmailConfigStatusById")
    @ApiOperation(value = "停用/启用邮件发送配置", notes = "停用/启用邮件发送配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgEmailConfigId", value = "邮件发送配置主键", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "disabledTemplate", value = "是否同时停用相关模板", required = true, dataType = "Integer", paramType = "query")
    })
    public ResultHelper<String> updateEmailConfigStatusById(Long msgEmailConfigId, Integer disabledTemplate) {
        return msgEmailConfigService.updateEmailConfigStatusById(msgEmailConfigId, disabledTemplate);
    }

}
