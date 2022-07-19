package com.share.file.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 查询文件列表入参 Vo
 * @Author nily
 * @Date 2020/11/25
 * @Time 5:12 下午
 */
@Getter
@Setter
public class FileListParamsVo {

    private String systemId;
    private String fileName;
    private String fileType;
    private String fileKey;
    private String name;
    private int currentPage;
    private  int pageSize;
}
