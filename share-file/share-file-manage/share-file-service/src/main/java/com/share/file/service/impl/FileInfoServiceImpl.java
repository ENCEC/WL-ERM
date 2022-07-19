package com.share.file.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.share.file.constants.GlobalConstant;
import com.share.file.enums.GlobalEnum;
import com.share.file.model.vo.FileInfo;
import com.share.file.model.vo.FileInfoReturnVo;
import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.StorageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ds.utils.ResultUtils;
import com.gillion.saas.redis.SassRedisInterface;
import com.google.common.collect.ImmutableMap;
import com.share.file.model.entity.FsFileInfo;
import com.share.file.model.entity.FsUploaderConfig;
import com.share.file.model.querymodels.QFsFileInfo;
import com.share.file.model.querymodels.QFsUploaderConfig;
import com.share.file.service.FileInfoService;
import com.share.file.util.fastdfs.FastDfsClient;
import com.share.file.util.fastdfs.FastDfsException;
import com.share.file.util.fastdfs.StorageConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baill
 * @description: 文件服务接口ServiceImpl
 * @data: 2020-11-03 14:10
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    FastDfsClient fastDfsClient;
    @Autowired
    StorageConfig storageConfig;

    @Autowired
    SassRedisInterface sassRedisInterface;

    /**
     * 上传文件
     * @param files
     * @param fileType
     * @param systemId
     * @return
     */
    @Override
    public Map<String, Object> uploadFile(MultipartFile[] files, String[] fileType, String systemId) {
        if (StringUtils.isEmpty(systemId)) {
            return ResultUtils.getFailedResultData("systemId为空");
        }
        if (files.length == 0) {
            return ResultUtils.getFailedResultData("没有上传的文件");
        }
        List<FsUploaderConfig> fsUploaderConfig;
        // 获取文件配置类
        fsUploaderConfig = QFsUploaderConfig.fsUploaderConfig
                .select(QFsUploaderConfig.fsUploaderConfig.fieldContainer())
                .where(QFsUploaderConfig.businessSystemId.eq(":businessSystemId"))
                .execute(ImmutableMap.of("businessSystemId", systemId));
        // 文件上传限制
        if (!CollectionUtils.isEmpty(fsUploaderConfig) && fsUploaderConfig.get(0).getCountLimit() != null && files.length > fsUploaderConfig.get(0).getCountLimit()) {
            return ResultUtils.getFailedResultData("文件数量超过限制，可在文件上传配置页配置");
        }
        List<FsFileInfo> fileList = new ArrayList<>();
        try {
            for (int i = 0; i < files.length; i++) {
                FsFileInfo fileInfo = new FsFileInfo();
                fileInfo.setFileName(files[i].getOriginalFilename());
                fileInfo.setFileType(fileType[i]);
                fileInfo.setBusinessSystemId(systemId);
                BigDecimal fileSize = new BigDecimal(files[i].getSize());
                // 比较文件大小是否超过限制
                if (!CollectionUtils.isEmpty(fsUploaderConfig)
                        && (fileSize.compareTo(fsUploaderConfig.get(0).getSizeLimit())) > 0) {
                    return ResultUtils.getFailedResultData("文件" + fileInfo.getFileName() + "大小超过限制，可在文件上传配置页配置");
                }
                // 限制文件类型
                Map<String, Object> resultMap = this.restrictFileTypes(files, fsUploaderConfig, i);
                if (!CollectionUtils.isEmpty(resultMap)) {
                    return resultMap;
                }
                fileInfo.setFileSize(fileSize);

                InputStream inputStream = null;
                inputStream = files[i].getInputStream();

                StorageServer storageServer = storageConfig.getStorageServer();
                String fileKey = fastDfsClient.upload(inputStream, fileInfo.getFileName(), null, storageServer);
                if (StringUtils.isNotEmpty(fileKey)) {
                    fileInfo.setFileKey(fileKey);
                    fileList.add(fileInfo);
                }
            }

            if (CollectionUtils.isEmpty(fileList)) {
                return ResultUtils.getFailedResultData("没有上传成功的文件");
            }
            fileList.forEach(x -> x.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED));
            int savedCount = QFsFileInfo.fsFileInfo.save(fileList);
            if (savedCount > 0) {
                return ResultUtils.getSuccessResultData(savedCount);
            }
            return ResultUtils.getFailedResultData("文件对象保存数据库失败");

        } catch (FastDfsException | IOException e) {
            log.error("上传文件失败：" + e);
        }
        return ResultUtils.getFailedResultData("上传失败");
    }

    /**
     * 文件类型限制判断
     *
     * @return
     * @author huanghwh
     * @date 2021/4/26 下午8:09
     */
    private Map<String, Object> restrictFileTypes(MultipartFile[] files, List<FsUploaderConfig> fsUploaderConfig, int i) {
        if (!CollectionUtils.isEmpty(fsUploaderConfig)) {
            FsUploaderConfig item = fsUploaderConfig.get(0);
            if (StringUtils.isNotEmpty(item.getTypeLimit())) {
                // 将类型限制存为集合
                List<String> typeList = Arrays.asList(item.getTypeLimit().split(";"));
                if (!CollectionUtils.isEmpty(typeList)) {
                    // 获取文件扩展名
                    String originalFilename = files[i].getOriginalFilename();
                    if (Objects.isNull(originalFilename)) {
                        return ResultUtils.getFailedResultData("获取文件扩展名失败");
                    }
                    String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    // 集合转换为大写
                    typeList = typeList.stream().map(String::toUpperCase).collect(Collectors.toList());
                    if (!typeList.contains(type.toUpperCase())) {
                        return ResultUtils.getFailedResultData("允许上传的文件类型：" + item.getTypeLimit());
                    }
                }
            }
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @return
     * @author bll
     */
    @Override
    public void downloadFile(String fileId, HttpServletResponse response) {
        BufferedInputStream bis = null;

        try (OutputStream outputStream = response.getOutputStream()) {
            if (StringUtils.isEmpty(fileId)) {
                throw new NullPointerException("文件id为空");
            }

            List<FsFileInfo> files;
            files = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                    .where(QFsFileInfo.fsFileInfoId.eq(":fsFileInfoId"))
                    .execute(ImmutableMap.of("fsFileInfoId", fileId));

            if (CollectionUtils.isEmpty(files)) {
                log.error("不存在相应的文件");
            }

            String fileName = URLEncoder.encode(files.get(0).getFileName(), "UTF-8");
            response.setHeader("content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName);

            StorageServer storageServer = storageConfig.getStorageServer();
            InputStream is = fastDfsClient.download(files.get(0).getFileKey(), storageServer);
            byte[] buff = new byte[1024];
            bis = new BufferedInputStream(is);

            int i = bis.read(buff);
            while (i != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            log.error("下载文件失败：" + e);
        }
    }

    /**
     * 批量下载
     *
     * @param fileId
     * @param response
     * @return
     * @author bll
     */
    @Override
    public void batchDownloadFile(Long[] fileId, HttpServletResponse response) {
        try {
            if (fileId == null || fileId.length == 0) {
                log.error("缺少fileId");
            }

            List<FsFileInfo> files;
            files = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                    .where(QFsFileInfo.fsFileInfoId.in$(fileId)).execute();
            if (CollectionUtils.isEmpty(files)) {
                log.error("不存在响应的文件");
            }

            String zipName = "文件批量下载.zip";
            String fileName = URLEncoder.encode(zipName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName);
            StorageServer storageServer = storageConfig.getStorageServer();
            ByteArrayOutputStream baos = fastDfsClient.batchDownload2(files, storageServer);

            OutputStream os = response.getOutputStream();
            InputStream is = new ByteArrayInputStream(baos.toByteArray());

            byte[] buffer1 = new byte[1024 * 5];
            int len = 0;
            while ((len = is.read(buffer1)) > 0) {
                os.write(buffer1, 0, len);
            }
            os.flush();
            is.close();
            baos.close();
            os.close();

        } catch (Exception e) {
            log.error("下载文件失败：" + e);
        }
    }

    /**
     * @Description 删除文件
     * @Author nily
     * @Date 2020/11/23
     * @Time 2:10 下午
     */
    @Override
    public Map<String, Object> deleteFile(FileInfo fileInfo) {
        // 1.验证参数

        if (validateFileParams(fileInfo) == null) {
            // 2.查数据库文件记录
            FsFileInfo fsFileInfo = QFsFileInfo.fsFileInfo.selectOne().where(QFsFileInfo.fsFileInfoId
                    .eq$(fileInfo.getFileId()).and(QFsFileInfo.fileKey.eq$(fileInfo.getFileKey()))).execute();

            // 3 客户端删除文件
            if (fsFileInfo != null) {
                try {
                    StorageServer storageServer = storageConfig.getStorageServer();
                    int deleteByClient = fastDfsClient.deleteFile(fsFileInfo.getFileKey(), storageServer);
                    if (deleteByClient == 0) {
                        // 4 数据库表删除
                        fsFileInfo.setRowStatus(GlobalConstant.DELETE);
                        int delete = QFsFileInfo.fsFileInfo.deleteById(fileInfo.getFileId());
                        if (delete == 1) {
                            return ResultUtils.getSuccessResultData(GlobalEnum.FileResultEnum.DELETE_SUCCESS.getCode(),
                                    "删除 fsFileInfo 成功");
                        } else {
                            return ResultUtils.getFailedResultData(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                                    "删除 FsFileInfo 失败");
                        }

                    } else {
                        return ResultUtils.getFailedResultData(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                                "fastDfsClient 删除文件失败");
                    }

                } catch (FastDfsException | IOException e) {
                    log.error("删除文件失败" + e);
                    return ResultUtils.getFailedResultData(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                            GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
                }
            }

            return ResultUtils.getFailedResultData("查询 fsFileInfo 为null");

        }
        return ResultUtils.getFailedResultData(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(), "删除 fileInfo 失败");
    }

    /**
     * @Description 批量删除文件
     * @Author nily
     * @Date 2020/11/23
     * @Time 4:29 下午
     */
    @Override
    public Map<String, Object> batchDeleteFile(FileInfo[] fileInfos) {
        // 1.验证参数
        Map<String, Object> returnMap = new HashMap<>(16);
        for (FileInfo fileInfo : fileInfos) {
            String validateFileParamsResult = validateFileParams(fileInfo);
            if (StringUtils.isNotEmpty(validateFileParamsResult)) {
                continue;
            }
            // 2 查数据库文件记录
            FsFileInfo fsFileInfo = QFsFileInfo.fsFileInfo.selectOne().where(QFsFileInfo.fsFileInfoId
                    .eq$(fileInfo.getFileId()).and(QFsFileInfo.fileKey.eq$(fileInfo.getFileKey()))).execute();

            // 3 客户端删除文件
            if (Objects.isNull(fsFileInfo)) {
                return ResultUtils.getFailedResultData("查询 fsFileInfo 为null");
            }
            try {
                StorageServer storageServer = storageConfig.getStorageServer();
                int deleteByClient = fastDfsClient.deleteFile(fsFileInfo.getFileKey(), storageServer);
                if (deleteByClient == 0) {
                    // 4 数据库表删除
                    fsFileInfo.setRowStatus(GlobalConstant.DELETE);
                    int delete = QFsFileInfo.fsFileInfo.deleteById(fileInfo.getFileId());
                    if (delete == 1) {
                        returnMap = ResultUtils.getSuccessResultData(
                                GlobalEnum.FileResultEnum.DELETE_SUCCESS.getCode(), "删除 fsFileInfo 成功");
                    } else {
                        returnMap = ResultUtils.getFailedResultData(
                                GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(), "删除 FsFileInfo 失败");
                    }

                } else {
                    returnMap = ResultUtils.getFailedResultData(
                            GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(), "fastDfsClient 删除文件失败");
                }

            } catch (FastDfsException | IOException e) {
                log.error("批量删除文件失败" + e);
                return ResultUtils.getFailedResultData(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                        GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
            }

        }

        return returnMap;
    }

    /**
     * @Description 验证参数
     * @Author nily
     * @Date 2020/11/23
     * @Time 4:28 下午
     */
    private String validateFileParams(FileInfo fileInfo) {
        FileInfoReturnVo<String> fileInfoReturnVo = new FileInfoReturnVo<>();
        if (fileInfo != null) {
            if (StringUtils.isEmpty(fileInfo.getFileId().toString())) {
                fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.KEYID_EMPTY_EXCEPTION.getCode());
                fileInfoReturnVo.setResultMsg(GlobalEnum.FileResultEnum.KEYID_EMPTY_EXCEPTION.getMessage());
                return JSON.toJSONString(fileInfoReturnVo);

            }
            if (StringUtils.isEmpty(fileInfo.getFileKey())) {
                fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode());
                fileInfoReturnVo.setResultMsg("fileKey 必填参数为空");
                return JSON.toJSONString(fileInfoReturnVo);
            }
            return null;
        } else {
            fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode());
            fileInfoReturnVo.setResultMsg("fileInfo 文件对象为空");
            return JSON.toJSONString(fileInfoReturnVo);
        }
    }

    /**
     * 保存当前系统code
     *
     * @param systemCode
     * @return
     * @author bll
     */
    @Override
    public Map<String, Object> saveCurrentSystem(String systemCode) {
        if (StringUtils.isEmpty(systemCode)) {
            return ResultUtils.getFailedResultData("systemCode为空");
        }
        sassRedisInterface.set(GlobalConstant.SESSION_KEY_APP_CODE, systemCode);
        return ResultUtils.getSuccessResultData("保存systemCode成功");
    }

}
