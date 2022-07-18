package com.share.support.result;

import lombok.Data;

/**
 * @author tujx
 * @description 对外接口统一返回对象
 * @date 2021/05/12
 */
@Data
public class ResultData<T> {

    /**
     * 返回代码，0：成功，1：失败
     */
    String resultCode;

    /**
     * resultCode为1时返回的错误信息
     */
    String resultMsg;

    /**
     * 返回的数据对象
     */
    T data;


}
