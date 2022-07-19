package com.share.message.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.message.model.entity.MsgMessageTemplate;
import com.share.message.vo.MsgMessageTemplateVO;
import com.share.message.vo.QueryMsgMessageTemplateDTO;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;

/**
 * 消息模板
 * @author wangzicheng
 * @date 2021/5/25 15:00
 */
public interface MsgMessageTemplateService {
    /**
     * 查询消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:27
     * @param queryMeetingConditionVO
     * @return ResultHelper<Page<MsgMessageTemplate>>
     */
    ResultHelper<Page<MsgMessageTemplateVO>> queryMsgMessageTemplate(QueryMsgMessageTemplateDTO queryMeetingConditionVO);
    /**
     * 新增消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateVO
     * @return ResultHelper
     */
    ResultHelper addMsgMessageTemplate(MsgMessageTemplateVO msgMessageTemplateVO);
    /**
     * 更新消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateVO
     * @return ResultHelper
     */
    ResultHelper updateMsgMessageTemplate(MsgMessageTemplateVO msgMessageTemplateVO);
    /**
     * 禁用/启用消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateId
     * @return ResultHelper
     */
    ResultHelper updateIsValid(Long msgMessageTemplateId);
    /**
     * 删除消息模板
     * @author wangzicheng
     * @date 2021/5/25 15:55
     * @param msgMessageTemplateId
     * @return ResultHelper
     */
    ResultHelper deleteMsgMessageTemplate(Long msgMessageTemplateId);
    /**
     * 根据模板编码获取消息模板
     * @author wangzicheng
     * @date 2021/5/28 18:09
     * @param msgMessageTemplateCode
     * @return MsgMessageTemplateVO
     */
    ApiResultHelper<Object> getMsgMessageTemplateByCode(String msgMessageTemplateCode);
}
