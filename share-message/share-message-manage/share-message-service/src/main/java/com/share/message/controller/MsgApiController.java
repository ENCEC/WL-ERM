package com.share.message.controller;


import com.share.message.dto.SendMsgReturnVo;
import com.share.message.service.MsgApiService;
import com.share.message.vo.MsgSmsApiVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 短信对外接口
 * @Author nily
 * @Date 2020/11/25
 * @Time 9:22 上午
 */
@RestController
@RequestMapping("/MsgApi")
public class MsgApiController {

    @Autowired
    private MsgApiService msgApiService;


    /**
     * 3.3.3 根据短信模板编号获取短信模板的消息宏参数
     * @param msgSmsApiVO
     * @return
     * @Description
     * @Author nily
     * @Date 2020/11/25
     * @Time 9:21 上午
     */
    @ApiOperation(value = "获取短信模板的消息宏参数", notes = "获取短信模板的消息宏参数")
    @ApiImplicitParam(name = "MsgSmsApiVO", value = "短信发送Vo", required = true, dataType = "MsgSmsApiVO")
    @PostMapping(value = "/getMarcoById")
    public SendMsgReturnVo<Object> getMarcoById(@RequestBody MsgSmsApiVO msgSmsApiVO) {
        return msgApiService.getMarcoById(msgSmsApiVO);
    }


    /**
     * 3.3.4 发送短信 接口
     *
     * @param msgSmsApiVO
     * @return , String acceptNo,  MsgMarcoDto msgMarcoDto,  String content
     * @Description
     * @Author nily
     * @Date 2020/11/25
     * @Time 9:17 上午
     */
    @ApiOperation(value = "发送短信", notes = "发送短信")
    @ApiImplicitParam(name = "MsgSmsApiVO", value = "短信发送Vo", required = true, dataType = "MsgSmsApiVO")
    @PostMapping(value = "/sendMsg")
    public SendMsgReturnVo<String> sendMsg(@RequestBody MsgSmsApiVO msgSmsApiVO) {
        return msgApiService.sendMsg(msgSmsApiVO);
    }

}
