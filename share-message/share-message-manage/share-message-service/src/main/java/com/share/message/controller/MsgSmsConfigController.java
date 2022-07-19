package com.share.message.controller;

import com.share.message.dto.MsgSmsConfigDto;
import com.share.message.service.impl.MsgSmsConfigServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 短信接口
 * @author nily
 * @date 2020/11/2
 */
@RestController
@RequestMapping("/MsgSmsConfig")
public class MsgSmsConfigController {
    @Autowired
    private MsgSmsConfigServiceImpl smsConfigServiceimpl;

    /**
     * 5.5.3.3.2.	新增短信发送配置
     * @param msgSmsConfigDto
     * @return
     */
    @ApiOperation(value = "新增短信发送配置",notes = "新增短信发送配置")
    @ApiImplicitParam(name = "MsgSmsConfigDto",value = "短信发送配置dto",required = true,dataType ="MsgSmsConfigDto")
    @RequestMapping(value = "/saveSmsConfig",method = RequestMethod.GET)
    public Map<String, Object> saveSmsConfig(MsgSmsConfigDto msgSmsConfigDto) {
        return smsConfigServiceimpl.saveSmsConfig(msgSmsConfigDto);
    }

    /**
     * 5.5.3.3.3.	编辑短信发送配置
     * @param msgSmsConfigDto
     * @return
     */
    @ApiOperation(value = "编辑短信发送配置",notes = "编辑短信发送配置")
    @ApiImplicitParam(name = "msgSmsConfigDto",value = "短信发送配置dto",required = true,dataType ="msgSmsConfigDto")
    @RequestMapping(value = "/updateSmsConfig",method = RequestMethod.POST)
    public Map<String, Object> updateSmsConfig(MsgSmsConfigDto msgSmsConfigDto) {
        return smsConfigServiceimpl.updateSmsConfig(msgSmsConfigDto);
    }
}
