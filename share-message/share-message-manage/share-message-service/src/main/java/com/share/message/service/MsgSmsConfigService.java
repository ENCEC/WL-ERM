package com.share.message.service;

import com.share.message.dto.MsgSmsConfigDto;

import java.util.List;
import java.util.Map;

/**
 * @Description 短信发送配置
 * @Author nily
 * @Date 2021/1/14
 * @Time 上午9:57
 */
public interface MsgSmsConfigService {

    /**
     * 新增短信发送配置
     * @param msgSmsConfigDto
     * @return
     * @author nily
     */
    Map<String, Object> saveSmsConfig(MsgSmsConfigDto msgSmsConfigDto);

    /**
     * 编辑短信发送配置
     * @param msgSmsConfigDto
     * @return
     * @author nily
     */
    Map<String, Object> updateSmsConfig(MsgSmsConfigDto msgSmsConfigDto);

    /**
     * 根据 businessSystemId 获取服务商列表
     * @param businessSystemId
     * @return
     * @author nily
     */
    List<MsgSmsConfigDto> getMsgConfigByBusinessSystemId(String businessSystemId);
}
