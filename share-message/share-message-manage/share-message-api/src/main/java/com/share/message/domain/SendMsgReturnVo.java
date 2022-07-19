package com.share.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description SendMsgReturnVo
 * @Author nily
 * @Date 2020/11/25
 * @Time 9:24 上午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMsgReturnVo<T> {
    private String resultCode;
    private String resultMsg;
    private T data;


}
