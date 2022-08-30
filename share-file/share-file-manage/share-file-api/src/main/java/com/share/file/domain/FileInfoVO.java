package com.share.file.domain;

import lombok.Data;

/**
 * 文件信息V0
 *
 * @author wangcl
 * @date 20201103
 */
@Data
public class FileInfoVO {
    /**
     * 使用方系统Id
     */
    private String systemId;
    /**
     * 文件Id
     */
    private String fileKey;

    /**
     * 文件Id数组
     */
    private String[] fileKeys;
}
