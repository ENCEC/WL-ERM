package com.share.auth.service.impl;

import com.gillion.exception.BusinessRuntimeException;
import com.share.auth.constants.CodeFinal;
import com.share.auth.service.FileService;
import com.share.auth.util.MessageUtil;
import com.share.file.api.ShareFileInterface;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenxf
 * @date 2020-12-24 16:32
 */
@Service
@Slf4j
@Transactional(rollbackFor = { Exception.class })
public class FileServiceImpl implements FileService {

    @Value("${user.template.managerFile}")
    private String managerFilePath;

    @Autowired
    private ShareFileInterface shareFileInterface;

    /**
     * @return
     * @Author:chenxf
     * @Description: 查询文件上传大小限制
     * @Date: 17:24 2020/12/24
     * @Param: []
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> fileService() {
        Map<String, Object> resultMap = shareFileInterface.getUploaderConfigBySystemCode(MessageUtil.getApplicationCode());
        String successCode = "0";
        if (Objects.nonNull(resultMap) && successCode.equals(resultMap.get(CodeFinal.RESULTCODE))) {
            Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
            Integer sizeLimit = (Integer) data.get("sizeLimit");
            return CommonResult.getSuccessResultData(sizeLimit);
        }
        return CommonResult.getFaildResultData(Objects.nonNull(resultMap) ? resultMap.get("resultMsg").toString() : "文件服务调用失败，请联系管理员！");
    }

    @Override
    public void downloadTemplate(HttpServletResponse response, String fileType) {
        String filePath = null;
        // 获取模板文件路径
        if (Objects.equals(fileType, CodeFinal.TEMPLATE_FILE_TYPE_ONE)) {
            filePath = managerFilePath;
        }
        // 判空
        if (StringUtils.isBlank(filePath)) {
            throw new BusinessRuntimeException("根据文件类型未匹配到模板文件");
        }
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream, outputStream);
            // 下载文件
            String fileName = URLEncoder.encode(new String(file.getName().getBytes(), StandardCharsets.UTF_8), "UTF-8");
            response.setContentType(ContentType.MULTIPART_FORM_DATA.toString());
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            response.flushBuffer();
        } catch (IOException e) {
            log.error("下载认证函模板失败：", e);
            throw new BusinessRuntimeException("下载认证函模板失败");
        }
    }

}
