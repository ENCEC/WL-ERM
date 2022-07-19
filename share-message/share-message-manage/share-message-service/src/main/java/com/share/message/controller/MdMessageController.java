package com.share.message.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.message.service.MdMessageService;
import com.share.message.vo.MsgMessageVO;
import com.share.message.vo.QueryMsgMessageDTO;
import com.share.message.vo.mdmessage.MdMsgStatusDTO;
import com.share.message.vo.mdmessage.QueryMessageByUserDTO;
import com.share.message.vo.mdmessage.UnReadCountDTO;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月28日 13:44
 */
@RestController
@RequestMapping("/mdMessage")
public class MdMessageController {

    @Autowired
    private MdMessageService mdMessageService;

    /**
     * 消息统计列表
     * @author wangzicheng
     * @date 2021/5/25 15:27
     * @param queryMsgMessageDTO
     * @return ResultHelper<Page<MsgMessageTemplate>>
     */
    @PostMapping(value = "/queryMsgMessage")
    @ApiOperation(value = "查询消息")
    @ApiImplicitParam(name = "QueryMsgMessageDTO", value = "查询消息入参", required = true, dataType = "QueryMsgMessageDTO", paramType = "body")
    public ResultHelper<Page<MsgMessageVO>> queryMsgMessage(@RequestBody QueryMsgMessageDTO queryMsgMessageDTO) {
        return mdMessageService.queryMsgMessage(queryMsgMessageDTO);
    }
    /**
     * 保存消息
     * @author wangzicheng
     * @date 2021/5/27 17:22
     * @param msgMessageVO
     * @return com.share.support.result.ResultHelper
     */
    @PostMapping(value = "/saveMsgMessage")
    @ApiOperation(value = "保存消息")
    @ApiImplicitParam(name = "MsgMessageVO", value = "消息VO", required = true, dataType = "MsgMessageVO", paramType = "body")
    public ApiResultHelper<Object> saveMsgMessage(@RequestBody MsgMessageVO msgMessageVO) {
        return mdMessageService.saveMsgMessage(msgMessageVO);
    }
    /**
     * 查询消息
     * @author wangzicheng
     * @date 2021/5/28 18:01
     * @param queryMessageByUserDTO
     * @return ResultHelper<Page<MdMessageVO>>
     */
    @PostMapping(value = "/queryMessageByUser")
    @ApiOperation(value = "用户查询消息")
    @ApiImplicitParam(name = "QueryMessageByUserDTO", value = "用户查询消息入参", required = true, dataType = "QueryMessageByUserDTO", paramType = "body")
    public ApiResultHelper<Object> queryMessageByUser(@RequestBody QueryMessageByUserDTO queryMessageByUserDTO) {
        return mdMessageService.queryMessageByUser(queryMessageByUserDTO);
    }
    /**
     * 查询消息
     * @author wangzicheng
     * @date 2021/5/28 18:01
     * @param queryMessageByUserDTO
     * @return ResultHelper<Page<MdMessageVO>>
     */
    @PostMapping(value = "/queryMessageByUserId")
    @ApiOperation(value = "用户查询消息")
    @ApiImplicitParam(name = "QueryMessageByUserDTO", value = "用户查询消息入参", required = true, dataType = "QueryMessageByUserDTO", paramType = "body")
    public ApiResultHelper<Object> queryMessageByUserId(@RequestBody QueryMessageByUserDTO queryMessageByUserDTO) {
        return mdMessageService.queryMessageByUserId(queryMessageByUserDTO);
    }
    /**
     * 查询消息详情
     * @author wangzicheng
     * @date 2021/5/28 18:02
     * @param queryMessageByUserDTO
     * @return MdMessageVO
     */
    @PostMapping(value = "/getMessageDetailByUser")
    @ApiOperation(value = "用户查询消息详情")
    @ApiImplicitParam(name = "QueryMessageByUserDTO", value = "用户查询消息入参", required = true, dataType = "QueryMessageByUserDTO", paramType = "body")
    public ApiResultHelper<Object> getMessageDetailByUser(@RequestBody QueryMessageByUserDTO queryMessageByUserDTO) {
        return mdMessageService.getMessageDetailByUser(queryMessageByUserDTO);
    }

    /**
     * 未读消息统计
     * @author wangzicheng
     * @date 2021/5/31 11:28
     * @param unReadCountDTO
     * @return ApiResultHelper<Integer>
     */
    @PostMapping(value = "/unReadCount")
    @ApiOperation(value = "未读消息统计")
    @ApiImplicitParam(name = "QueryMsgMessageDTO", value = "查询消息入参", required = true, dataType = "QueryMsgMessageDTO", paramType = "body")
    public ApiResultHelper<Object> unReadCount(@RequestBody UnReadCountDTO unReadCountDTO) {
        return mdMessageService.unReadCount(unReadCountDTO);
    }
    /**
     * 消息已读
     * @author wangzicheng
     * @date 2021/5/31 13:30
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Integer>
     */
    @PostMapping(value = "/updateStatus2Read")
    @ApiOperation(value = "消息已读")
    @ApiImplicitParam(name = "MdMsgStatusDTO", value = "消息已读入参", required = true, dataType = "MdMsgStatusDTO", paramType = "body")
    public ApiResultHelper<Object> updateStatus2Read(@RequestBody MdMsgStatusDTO mdMsgStatusDTO) {
        return mdMessageService.updateStatus2Read(mdMsgStatusDTO);
    }
    /**
     * 消息删除
     * @author wangzicheng
     * @date 2021/5/31 14:28
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Object>
     */
    @PostMapping(value = "/update2IsValid")
    @ApiOperation(value = "消息删除")
    @ApiImplicitParam(name = "MdMsgStatusDTO", value = "消息删除入参", required = true, dataType = "MdMsgStatusDTO", paramType = "body")
    public ApiResultHelper<Object> update2IsValid(@RequestBody MdMsgStatusDTO mdMsgStatusDTO) {
        return mdMessageService.update2IsValid(mdMsgStatusDTO);
    }
}
