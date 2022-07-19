package com.share.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token返回结果
 * @author wangcl
 * @date 2020/11/28 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDfsTokenResult {
    /**
     * 返回码
     */
    private String resultCode;
    /**
     * 返回信息
     */
    private String resultMsg;
    /**
     * 完整路径
     */
    private String data;
}
