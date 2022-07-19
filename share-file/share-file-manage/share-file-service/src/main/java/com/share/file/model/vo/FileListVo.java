package com.share.file.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 文件列表 Vo
 * @Author nily
 * @Date 2020/11/24
 * @Time 10:04 上午
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long fsFileInfoId;
    private String businessSystemId;
    private String fileKey;
    private String fileName;
    private BigDecimal fileSize;
    private String fileType;
    private Long creatorId;
    private String creatorName;
    private String createTime;
    private Long modifierId;
    private String modifierName;
    private String modifyTime;
}
