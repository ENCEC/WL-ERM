package com.share.message.service;

import com.share.message.dto.SendMsgReturnVo;
import com.share.message.vo.MsgSmsApiVO;

/**
 * @Description
 * @Author nily
 * @Date 2020/11/25
 * @Time 5:09 下午
 */
public interface MsgApiService {


    /**
     * 根据短信模板编号获取短信模板的消息宏参数
     *
     * @param msgSmsApiVO
     * @return
     * @author nily
     */
    SendMsgReturnVo<Object> getMarcoById(MsgSmsApiVO msgSmsApiVO);


    /**
     * 发送短信 接口
     *
     * @param msgSmsApiVO
     * @return
     * @author nily
     * @Date 2020/11/25
     */
    SendMsgReturnVo<String> sendMsg(MsgSmsApiVO msgSmsApiVO);
}
