package com.share.message.service;

import com.share.message.vo.MsgEmailConfigVO;
import com.share.support.result.ResultHelper;


/**
 * 邮件发送配置接口
 *
 * @author tujx
 * @date 2020/10/16
 */
public interface MsgEmailConfigService {

    /**
     * 新增一条邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return ResultHelper
     * @author tujx
     */
    ResultHelper<String> saveEmailConfig(MsgEmailConfigVO msgEmailConfigVO);

    /**
     * 编辑一条邮件发送配置
     *
     * @param msgEmailConfigVO 邮件发送配置信息
     * @return ResultHelper
     * @author tujx
     */
    ResultHelper<String> updateEmailConfig(MsgEmailConfigVO msgEmailConfigVO);

    /**
     * 禁用/启用邮件发送配置
     *
     * @param msgEmailConfigId 邮件发送配置id
     * @param disabledTemplate 是否同时停用相关模板，0：否，1：是
     * @return ResultHelper
     * @author tujx
     */
    ResultHelper<String> updateEmailConfigStatusById(Long msgEmailConfigId, Integer disabledTemplate);

}
