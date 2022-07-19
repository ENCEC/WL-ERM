package com.share.message.controller;

import com.share.message.dto.MsgSmsTemplateDto;
import com.share.message.service.impl.MsgSmsTemplateServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 短信模板接口
 * @author nily
 * @date 2020/11/2
 */
@RestController
@RequestMapping("/MsgSmsTemplate")
public class MsgSmsTemplateController {
    @Autowired
    private MsgSmsTemplateServiceImpl msgSmsTemplateServiceimpl;

    /**
     * 5.4.3.3.2.	新增短信模板
     * @param msgSmsTemplateDto
     * @return
     */
    @ApiOperation(value = "新增短信模板",notes = "新增短信模板")
    @ApiImplicitParam(name = "msgSmsTemplateDto",value = "短信模板dto",required = true,dataType ="msgSmsTemplateDto")
    @RequestMapping(value = "/saveSmsTemplate",method = RequestMethod.POST)
    public Map<String, Object> saveSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto) {
        return msgSmsTemplateServiceimpl.saveSmsTemplate(msgSmsTemplateDto);
    }

    /**
     * 5.4.3.3.3.	编辑短信模板
     * @param msgSmsTemplateDto
     * @return
     */
    @ApiOperation(value = "编辑短信模板",notes = "编辑短信模板")
    @ApiImplicitParam(name = "MsgSmsTemplateDto",value = "短信模板dto",required = true,dataType ="MsgSmsTemplateDto")
    @RequestMapping(value = "/updateSmsTemplate",method = RequestMethod.POST)
    public Map<String, Object> updateSmsTemplate(MsgSmsTemplateDto msgSmsTemplateDto) {
        return msgSmsTemplateServiceimpl.updateSmsTemplate(msgSmsTemplateDto);
    }
}
