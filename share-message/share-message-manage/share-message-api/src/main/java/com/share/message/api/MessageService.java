package com.share.message.api;

import com.share.message.domain.*;
import com.share.support.result.ApiResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author wangzicheng
 * @date 2021/5/27 16:31
 */
@FeignClient(name = "${application.name.message}")
public interface MessageService {

    /**
     * 查询消息模板
     *
     * @param msgMessageTemplateCode
     * @return ResultHelper<Page < MsgMessageTemplateVO>>
     * @author wangzicheng
     * @date 2021/5/27 16:20
     */
    @GetMapping(value = "/MsgMessageTemplate/getMsgMessageTemplateByCode")
    ApiResultHelper<MsgMessageTemplateVO> getMsgMessageTemplateByCode(@RequestParam(value = "msgMessageTemplateCode") String msgMessageTemplateCode);

    /**
     * 保存站内消息
     *
     * @param msgMessageVO
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/28 17:59
     */
    @PostMapping(value = "/mdMessage/saveMsgMessage")
    ApiResultHelper<Object> saveMsgMessage(@RequestBody MsgMessageVO msgMessageVO);

    /**
     * 用户查询消息
     *
     * @param queryMessageByUserDTO
     * @return ResultHelper<Page < MdMessageVO>>
     * @author wangzicheng
     * @date 2021/5/28 17:59
     */
    @PostMapping(value = "/mdMessage/queryMessageByUser")
    ApiResultHelper<Object> queryMessageByUser(@RequestBody QueryMessageByUserDTO queryMessageByUserDTO);

    /**
     * 用户查询消息详情
     *
     * @param queryMessageByUserDTO
     * @return MdMessageVO
     * @author wangzicheng
     * @date 2021/5/28 18:00
     */
    @PostMapping(value = "/mdMessage/getMessageDetailByUser")
    ApiResultHelper<Object> getMessageDetailByUser(@RequestBody QueryMessageByUserDTO queryMessageByUserDTO);
    /**
     * 未读消息统计
     * @author wangzicheng
     * @date 2021/5/31 13:32
     * @param unReadCountDTO
     * @return ApiResultHelper<Integer>
     */
    @PostMapping(value = "/mdMessage/unReadCount")
    ApiResultHelper<Object> unReadCount(@RequestBody UnReadCountDTO unReadCountDTO);
    /**
     * 消息已读
     * @author wangzicheng
     * @date 2021/5/31 13:33
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Integer>
     */
    @PostMapping(value = "/mdMessage/updateStatus2Read")
    ApiResultHelper<Object> updateStatus2Read(@RequestBody MdMsgStatusDTO mdMsgStatusDTO);
    /**
     * 消息删除
     * @author wangzicheng
     * @date 2021/5/31 14:34
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Integer>
     */
    @PostMapping(value = "/mdMessage/update2IsValid")
    ApiResultHelper<Object> update2IsValid(@RequestBody MdMsgStatusDTO mdMsgStatusDTO);
}