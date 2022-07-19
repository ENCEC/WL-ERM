package com.share.file.model.vo;

import lombok.Data;

/**
 * 接收外部文件实体
 * @author wangcl
 * @date 2020/11/25
 */
@Data
public class FileInfoApiVO {

    /**
     * 使用方系统Id
     */
    private String systemId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * base64加密文件
     */
    private String file;

    /**
     * 文件id
     */
    private String fileKey;
}
