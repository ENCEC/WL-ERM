package com.share.message.api;

import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendMsgReturnVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 短信发送 feign 接口
 * @Author nily
 * @Date 2020/11/26
 * @Time 6:53 下午
 */
@FeignClient(name = "${application.name.message}")
public interface MsgApiService {
    /**
     * 3.3.3 根据短信模板编号获取短信模板的消息宏参数
     *
     * @param msgSmsApiVO
     * @return
     */
    @RequestMapping(value = "/MsgApi/getMarcoById")
    SendMsgReturnVo<Object> getMarcoById(@RequestBody MsgSmsApiVO msgSmsApiVO);

    /**
     * 3.3.4 发送短信 接口
     *
     * @param msgSmsApiVO
     * @return , String acceptNo,  MsgMarcoDto msgMarcoDto,  String content
     */
    @RequestMapping(value = "/MsgApi/sendMsg")
    SendMsgReturnVo<String> sendMsg(@RequestBody MsgSmsApiVO msgSmsApiVO);
}
