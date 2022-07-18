package com.share.auth.controller;

import com.share.auth.service.FileService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-12-24 16:29
 */
@Api("文件控制器")
@Controller
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * @Author:chenxf
     * @Description: 查询文件上传大小限制
     * @Date: 17:30 2020/12/24
     * @Param: []
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @ApiOperation("查询文件上传大小限制")
    @GetMapping("/getFileSizeLimit")
    @ResponseBody
    public ResultHelper<Object> fileService() {
        return fileService.fileService();
    }

    /**
     * 下载模板
     * @param response 文件输出
     * @param fileType  文件类型（1-认证函模板）
     */
    @ApiOperation("下载模板")
    @GetMapping("/downloadTemplate")
    @ResponseBody
    public void downloadTemplate(HttpServletResponse response, String fileType) {
        fileService.downloadTemplate(response, fileType);
    }

}
