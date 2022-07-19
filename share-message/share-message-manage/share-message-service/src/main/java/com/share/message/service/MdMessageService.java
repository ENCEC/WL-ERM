package com.share.message.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.message.vo.MsgMessageVO;
import com.share.message.vo.QueryMsgMessageDTO;
import com.share.message.vo.mdmessage.MdMessageVO;
import com.share.message.vo.mdmessage.MdMsgStatusDTO;
import com.share.message.vo.mdmessage.QueryMessageByUserDTO;
import com.share.message.vo.mdmessage.UnReadCountDTO;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.ResultHelper;

public interface MdMessageService {

    /**
     * 查询消息
     * @author wangzicheng
     * @date 2021/5/28 18:10
     * @param queryMeetingConditionVO
     * @return ResultHelper<Page<MsgMessageVO>>
     */
    ResultHelper<Page<MsgMessageVO>> queryMsgMessage(QueryMsgMessageDTO queryMeetingConditionVO);
    /**
     * 保存消息
     * @author wangzicheng
     * @date 2021/5/28 18:10
     * @param msgMessageVO
     * @return ResultHelper
     */
    ApiResultHelper<Object> saveMsgMessage(MsgMessageVO msgMessageVO);

    /**
     * 查询消息
     * @author wangzicheng
     * @date 2021/5/28 18:03
     * @param queryMessageByUserDTO
     * @return ResultHelper<Page<MdMessageVO>>
     */
    ApiResultHelper<Object> queryMessageByUser(QueryMessageByUserDTO queryMessageByUserDTO);
    /**
     * 查询消息
     * @author wangzicheng
     * @date 2021/5/28 18:03
     * @param queryMessageByUserDTO
     * @return ResultHelper<Page<MdMessageVO>>
     */
    ApiResultHelper<Object> queryMessageByUserId(QueryMessageByUserDTO queryMessageByUserDTO);
    /**
     * 查询消息详情
     * @author wangzicheng
     * @date 2021/5/28 18:03
     * @param queryMessageByUserDTO
     * @return MdMessageVO
     */
    ApiResultHelper<Object> getMessageDetailByUser(QueryMessageByUserDTO queryMessageByUserDTO);

    /**
     * 未读消息统计
     * @author wangzicheng
     * @date 2021/5/31 11:18
     * @param unReadCountDTO
     * @return ApiResultHelper<Integer>
     */
    ApiResultHelper<Object> unReadCount(UnReadCountDTO unReadCountDTO);
    /**
     * 更新消息已读状态
     * @author wangzicheng
     * @date 2021/5/31 11:44
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Integer>
     */
    ApiResultHelper<Object> updateStatus2Read(MdMsgStatusDTO mdMsgStatusDTO);
    /**
     * 消息删除
     * @author wangzicheng
     * @date 2021/5/31 14:19
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Object>
     */
    ApiResultHelper<Object> update2IsValid(MdMsgStatusDTO mdMsgStatusDTO);
}
