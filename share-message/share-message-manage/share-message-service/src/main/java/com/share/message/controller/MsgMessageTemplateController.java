package com.share.message.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.message.service.MsgMessageTemplateService;
import com.share.message.vo.MsgMessageTemplateVO;
import com.share.message.vo.QueryMsgMessageTemplateDTO;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzicheng
 * @description 消息模板
 * @date 2021年05月25日 14:58
 */
@RestController
@RequestMapping("/MsgMessageTemplate")
public class MsgMessageTemplateController {

    @Autowired
    private MsgMessageTemplateService msgMessageTemplateService;
    /**
     * 查询消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:27
     * @param queryMsgMessageTemplateDTO
     * @return ResultHelper<Page<MsgMessageTemplate>>
     */
    @PostMapping(value = "/queryMsgMessageTemplate")
    @ApiOperation(value = "查询消息模板")
    @ApiImplicitParam(name = "QueryMsgMessageTemplateDTO", value = "查询消息模板入参", required = true, dataType = "QueryMsgMessageTemplateDTO", paramType = "body")
    public ResultHelper<Page<MsgMessageTemplateVO>> queryMsgMessageTemplate(@RequestBody QueryMsgMessageTemplateDTO queryMsgMessageTemplateDTO) {
        return msgMessageTemplateService.queryMsgMessageTemplate(queryMsgMessageTemplateDTO);
    }
    /**
     * 新增消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateVO
     * @return ResultHelper
     */
    @PostMapping(value = "/addMsgMessageTemplate")
    @ApiOperation(value = "新增消息模板")
    @ApiImplicitParam(name = "MsgMessageTemplateVO", value = "新增消息模板入参", required = true, dataType = "MsgMessageTemplateVO", paramType = "body")
    public ResultHelper addMsgMessageTemplate(@RequestBody MsgMessageTemplateVO msgMessageTemplateVO) {
        return msgMessageTemplateService.addMsgMessageTemplate(msgMessageTemplateVO);
    }
    /**
     * 更新消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateVO
     * @return ResultHelper
     */
    @PutMapping(value = "/updateMsgMessageTemplate")
    @ApiOperation(value = "编辑消息模板")
    @ApiImplicitParam(name = "MsgMessageTemplateVO", value = "编辑消息模板入参", required = true, dataType = "MsgMessageTemplateVO", paramType = "body")
    public ResultHelper updateMsgMessageTemplate(@RequestBody MsgMessageTemplateVO msgMessageTemplateVO) {
        return msgMessageTemplateService.updateMsgMessageTemplate(msgMessageTemplateVO);
    }
    /**
     * 禁用/启用消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateId
     * @return ResultHelper
     */
    @GetMapping(value = "/updateIsValid")
    @ApiOperation(value = "禁用/启用消息模板")
    @ApiImplicitParam(name = "MsgMessageTemplateId", value = "消息模板id", required = true, dataType = "Long", paramType = "query")
    public ResultHelper updateIsValid(@RequestParam Long msgMessageTemplateId) {
        return msgMessageTemplateService.updateIsValid(msgMessageTemplateId);
    }
    /**
     * 删除消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateId
     * @return ResultHelper
     */
    @DeleteMapping(value = "/deleteMsgMessageTemplate")
    @ApiOperation(value = "删除消息模板")
    @ApiImplicitParam(name = "MsgMessageTemplateId", value = "消息模板id", required = true, dataType = "Long", paramType = "query")
    public ResultHelper deleteMsgMessageTemplate(@RequestParam Long msgMessageTemplateId) {
        return msgMessageTemplateService.deleteMsgMessageTemplate(msgMessageTemplateId);
    }
    /**
     * 根据模板编码获取消息模板
     * @author wangzicheng
     * @date 2021/5/28 18:09
     * @param msgMessageTemplateCode
     * @return MsgMessageTemplateVO
     */
    @GetMapping(value = "/getMsgMessageTemplateByCode")
    @ApiOperation(value = "根据模板编码获取消息模板")
    @ApiImplicitParam(name = "MsgMessageTemplateId", value = "消息模板编码", required = true, dataType = "String", paramType = "query")
    public ApiResultHelper<Object> getMsgMessageTemplateByCode(@RequestParam String msgMessageTemplateCode) {
        return msgMessageTemplateService.getMsgMessageTemplateByCode(msgMessageTemplateCode);
    }

}
