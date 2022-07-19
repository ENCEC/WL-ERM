package com.share.message.controller;

import com.share.message.dto.MsgMarcoDto;
import com.share.message.service.impl.MsgMaecoServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author nly
 * @description 宏
 * @date 2020/11/19
 */
@RestController
@RequestMapping("/MsgMarco")
public class MsgMarcoController {
    @Autowired
    private MsgMaecoServiceImpl msgMaecoServiceimpl;

    /**
     * 5.6.3.3.2.	新增宏
     * @param msgMarcoDto
     * @return
     */
    @ApiOperation(value = "新增宏",notes = "新增宏")
    @ApiImplicitParam(name = "saveMsgMarco",value = "短信宏Vo",required = true,dataType ="MsgMarcoDto")
    @RequestMapping(value = "/saveMsgMarco",method = RequestMethod.POST)
    public Map<String, Object> saveMsgMarco( MsgMarcoDto msgMarcoDto) {
        return msgMaecoServiceimpl.saveMsgMarco(msgMarcoDto);
    }

    /**
     * 5.6.3.3.3.	编辑宏
     * @param msgMarcoDto
     * @return
     */
    @ApiOperation(value = "编辑宏",notes = "编辑宏")
    @ApiImplicitParam(name = "updateMsgMarco",value = "编辑宏Vo",required = true,dataType ="MsgMarcoDto")
    @RequestMapping(value = "/updateMsgMarco",method = RequestMethod.POST)
    public Map<String, Object> updateMsgMarco(MsgMarcoDto msgMarcoDto) {
        return msgMaecoServiceimpl.updateMsgMarco(msgMarcoDto);
    }

    /**
     * 5.6.3.3.4.	删除宏
     * @param msgMarcoId
     * @return
     */
    @ApiOperation(value = "删除宏",notes = "删除宏")
    @ApiImplicitParam(name = "deleteMsgMarcoById",value = "短信 id",required = true,dataType ="long")
    @RequestMapping(value = "/deleteMsgMarcoById",method = RequestMethod.GET)
    public Map<String, Object> deleteMsgMarcoById(Long msgMarcoId) {
        return msgMaecoServiceimpl.deleteMsgMarcoById(msgMarcoId);
    }

    /**
     * 5.6.3.3.5.	禁用/启用宏
     * @param msgMarcoId
     * @return
     */
    @ApiOperation(value = "禁用/启用宏",notes = "禁用/启用宏")
    @ApiImplicitParam(name = "updateMsgMarcoStatusById",value = "短信id",required = true,dataType ="long  ")
    @RequestMapping(value = "/updateMsgMarcoStatusById",method = RequestMethod.GET)
    public Map<String, Object> updateMsgMarcoStatusById(Long msgMarcoId) {
        return msgMaecoServiceimpl.updateMsgMarcoStatusById(msgMarcoId);
    }

}
