package com.share.file.model.vo;

import com.gillion.ds.entity.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 查询文件列表入参 Vo
 * @Author nily
 * @Date 2020/11/25
 * @Time 5:12 下午
 */
@Getter
@Setter
public class FileListParamsVo extends BaseModel implements Serializable {

    private String systemId;
    private String fileName;
    private String fileType;
    private String fileKey;
    private int currentPage;
    private  int pageSize;
}
