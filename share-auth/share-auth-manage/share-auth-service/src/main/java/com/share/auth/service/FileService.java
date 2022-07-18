package com.share.auth.service;


import com.share.support.result.ResultHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-12-24 16:32
 */
public interface FileService {
    /**
     * 查询文件上传大小限制
     *
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 查询文件上传大小限制
     * @Date: 17:30 2020/12/24
     * @Param: []
     */
    ResultHelper<Object> fileService();

    /**
     * 下载模板
     * @param response 文件输出
     * @param fileType 文件类型（1-认证函模板）
     */
    void downloadTemplate(HttpServletResponse response, String fileType);
}
