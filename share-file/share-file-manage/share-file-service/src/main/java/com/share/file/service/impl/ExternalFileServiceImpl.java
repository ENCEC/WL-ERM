package com.share.file.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.expressions.ConditionExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ec.upload.entity.CloudFile;
import com.gillion.ec.upload.support.CloudBucketProvider;
import com.share.file.constants.GlobalConstant;
import com.share.file.model.entity.FsFileInfo;
import com.share.file.model.entity.FsUploaderConfig;
import com.share.file.model.querymodels.QFsFileInfo;
import com.share.file.model.querymodels.QFsUploaderConfig;
import com.share.file.model.vo.FileInfoReturnVo;
import com.share.file.model.vo.FileInfoVO;
import com.share.file.model.vo.FileListParamsVo;
import com.share.file.model.vo.FileListVo;
import com.share.file.util.fastdfs.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.csource.fastdfs.StorageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.share.file.enums.GlobalEnum;
import com.share.file.service.ExternalFileService;

import javax.annotation.Resource;

/**
 * @author: baill
 * @description: 外部文件服务接口ServiceImpl
 * @data: 2020-11-03 14:10
 */
@Service
@Slf4j
public class ExternalFileServiceImpl implements ExternalFileService {

    /**
     * 注入fastdfs
     */
    @Autowired
    FastDfsClient fastDfsClient;
    /**
     * 注入storage
     */
    @Autowired
    StorageConfig storageConfig;


    @Autowired
    private CloudBucketProvider cloudBucketProvider;

    @Value("${upFileURL.subdirectory}")
    private String subdirectory;

    @Value("${upFileURL.lookHomeDirectory}")
    private String lookHomeDirectory;

    @Value("${upFileURL.deleteFileUrl}")
    private String deleteFileUrl;
    /**
     * systemId字段为空提示
     */
    private static final String SYSTEM_ID_NULL_TIPS = "【systemId字段为空】";
    /**
     * systemId字段为空提示
     */
    private static final String FILE_KEY_NULL_TIPS = "【fileKey字段为空】";
    /**
     * 下载文件失败提示
     */
    private static final String DOWNLOAD_FILE_FAIL_TIPS = "下载文件失败：";

    /**
     * 上传文件
     *
     * @param systemId
     * @param fileType
     * @param fileName
     * @param file
     * @return
     * @author bll
     */
    @Override
    public FastDfsUploadResult uploadExternalFile(String systemId, String fileType, String fileName,
                                                  MultipartFile file) {
        if (StringUtils.isEmpty(systemId)) {
            return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + SYSTEM_ID_NULL_TIPS, null);
        }
        if (StringUtils.isEmpty(fileType)) {
            return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + "【fileType字段为空】", null);
        }
        if (StringUtils.isEmpty(fileName)) {
            return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + "【fileName字段为空】", null);
        }
        if (file == null) {
            return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + "【file字段为空】", null);
        }
        try {
            List<FsUploaderConfig> fsUploaderConfig;
            // 获取文件配置类
            fsUploaderConfig = QFsUploaderConfig.fsUploaderConfig
                    .select(QFsUploaderConfig.fsUploaderConfig.fieldContainer())
                    .where(QFsUploaderConfig.businessSystemId.eq(":businessSystemId"))
                    .execute(ImmutableMap.of("businessSystemId", systemId));
            FsFileInfo fileInfo = new FsFileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setFileType(fileType);
            fileInfo.setBusinessSystemId(systemId);
            BigDecimal fileSize = new BigDecimal(file.getSize());
            // 比较文件大小是否超过限制
            if (!CollectionUtils.isEmpty(fsUploaderConfig) && (fileSize.compareTo(fsUploaderConfig.get(0).getSizeLimit())) > 0) {
                return FastDfsUploadResult.getFailureResult(
                        GlobalEnum.FileResultEnum.PARAMETER_INCONFORMITY_EXCEPTION.getCode(),
                        "文件" + fileInfo.getFileName() + "大小超过限制，可在文件上传配置页配置", null);
            }
            // 限制文件类型
            FastDfsUploadResult fastDfsUploadResult = this.restrictFileTypes(fsUploaderConfig, fileName, file);
            if (Objects.nonNull(fastDfsUploadResult)) {
                return fastDfsUploadResult;
            }
            fileInfo.setFileSize(fileSize);
            InputStream inputStream;
            inputStream = file.getInputStream();
//            StorageServer storageServer = storageConfig.getStorageServer();
//            String fileKey = fastDfsClient.upload(inputStream, fileInfo.getFileName(), null, storageServer);
            // 无类型文件不分割
            String str1=fileInfo.getFileName().substring(0, fileInfo.getFileName().contains(".") ? fileInfo.getFileName().indexOf(".") : fileInfo.getFileName().length());
            String str2=fileInfo.getFileName().substring(str1.length(), fileInfo.getFileName().length());
            String fileKey = UUID.randomUUID().toString()+str2;
            cloudBucketProvider.getCloudBucket(subdirectory).put(fileKey, CloudFile.of(inputStream,fileInfo.getFileName(),fileSize.longValue()));
            if (StringUtils.isNotEmpty(fileKey)) {
                fileInfo.setFileKey(fileKey);
                fileInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                int savedCount = QFsFileInfo.fsFileInfo.save(fileInfo);
                if (savedCount > 0) {
                    return FastDfsUploadResult.getSuccessResult(fileKey);
                }

            }
        } catch (IOException e) {
            return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getCode(),
                    GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getMessage(), null);
        }
        return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getCode(),
                GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getMessage(), null);
    }

    /**
     * 文件类型校验
     *
     * @param fsUploaderConfig
     * @param file
     * @return
     * @author huanghwh
     * @date 2021/4/27 上午10:34
     */
    private FastDfsUploadResult restrictFileTypes(List<FsUploaderConfig> fsUploaderConfig, String fileName, MultipartFile file) {
        if (!CollectionUtils.isEmpty(fsUploaderConfig)) {
            FsUploaderConfig item = fsUploaderConfig.get(0);
            if (StringUtils.isNotEmpty(item.getTypeLimit())) {
                // 将类型限制存为集合
                List<String> typeList = Arrays.asList(item.getTypeLimit().split(";"));
                if (!CollectionUtils.isEmpty(typeList)) {
                    // 获取文件扩展名
                    String originalFilename = file.getOriginalFilename();
                    if (Objects.isNull(originalFilename)) {
                        return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                                GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + "【原始文件名为空】", null);
                    }
                    String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                    // 集合转换为大写
                    typeList = typeList.stream().map(String::toUpperCase).collect(Collectors.toList());
                    if (!typeList.contains(type.toUpperCase()) ) {
                        return FastDfsUploadResult.getFailureResult(
                                GlobalEnum.FileResultEnum.PARAMETER_INCONFORMITY_EXCEPTION.getCode(),
                                "允许上传的文件类型：" + item.getTypeLimit(), null);
                    }
                    //限制文件名后綴
                    if(fileName.lastIndexOf(".")>0){
                        String nameType = fileName.substring(fileName.lastIndexOf(".")+1);
                        if(!typeList.contains(nameType.toUpperCase())){
                            return FastDfsUploadResult.getFailureResult(
                                    GlobalEnum.FileResultEnum.PARAMETER_INCONFORMITY_EXCEPTION.getCode(),
                                    "允许上传的文件类型：" + item.getTypeLimit(), null);
                        }

                    }
                }
            }
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param systemId
     * @param fileKey
     * @return
     * @author bll
     */
    @Override
    public FastDfsDownloadResult downloadExternalFile(String systemId, String fileKey) {
        if (StringUtils.isEmpty(systemId)) {
            return FastDfsDownloadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + SYSTEM_ID_NULL_TIPS, null, null);
        }
        if (StringUtils.isEmpty(fileKey)) {
            return FastDfsDownloadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + FILE_KEY_NULL_TIPS, null, null);
        }
        try {
            List<FsFileInfo> fsFileInfoList;
            fsFileInfoList = QFsFileInfo.fsFileInfo
                    .select(QFsFileInfo.fsFileInfo.fieldContainer())
                    .where(QFsFileInfo.fileKey.eq$(fileKey))
                    .execute();
            if (fsFileInfoList.isEmpty()) {
                return FastDfsDownloadResult.getFailureResult(GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getCode(),
                        GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getMessage(), null, null);
            } else {
//                StorageServer storageServer = storageConfig.getStorageServer();
//                InputStream is = fastDfsClient.download(fileKey, storageServer);
                //EC本地下载 subdirectory子目录
                InputStream is = cloudBucketProvider.getCloudBucket(subdirectory).get(fileKey);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len = 0;
                byte[] buff = new byte[1024];
                while ((len = is.read(buff)) != -1) {
                    byteArrayOutputStream.write(buff, 0, len);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String base64File = Base64.encodeBase64String(bytes);
                return FastDfsDownloadResult.getSuccessResult(base64File, fsFileInfoList.get(0).getFileName());
            }
        } catch (FileNotFoundException e) {
            log.error(DOWNLOAD_FILE_FAIL_TIPS + e);
            return FastDfsDownloadResult.getFailureResult(GlobalEnum.FileResultEnum.DOWNLOAD_FAILURE.getCode(),
                    GlobalEnum.FileResultEnum.DOWNLOAD_FAILURE.getMessage(), null, null);
        } catch (IOException e) {
            log.error(DOWNLOAD_FILE_FAIL_TIPS + e);
            return FastDfsDownloadResult.getFailureResult(GlobalEnum.FileResultEnum.DOWNLOAD_FAILURE.getCode(),
                    GlobalEnum.FileResultEnum.DOWNLOAD_FAILURE.getMessage(), null, null);
        }
    }

    /**
     * 删除文件
     *
     * @param fileInfoVO 文件信息参数
     * @return
     * @author wangcl
     */
    @Override
    public FastDfsDeleteResult deleteFile(FileInfoVO fileInfoVO) {
        if (StringUtils.isEmpty(fileInfoVO.getSystemId())) {
            return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + SYSTEM_ID_NULL_TIPS);
        }
        if (StringUtils.isEmpty(fileInfoVO.getFileKey())) {
            return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + FILE_KEY_NULL_TIPS);
        }
        try {
//            StorageServer storageServer = storageConfig.getStorageServer();
            // 因为是逻辑删除，所以要根据businessSystemId和fileKey查找到对应的主键
            List<FsFileInfo> files = getFileByParam(fileInfoVO);
            if (files.isEmpty()) {
                return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getCode(),
                        GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getMessage());
            }
            FsFileInfo fsFileInfo = files.get(0);
            int count = QFsFileInfo.fsFileInfo.delete(fsFileInfo);
            if (count <= 0) {
                return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                        GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
            }
            String fileName = deleteFileUrl+ fileInfoVO.getFileKey();
            File file = new File(fileName);
            if(file.isFile() && file.exists() && file.delete()){
                return FastDfsDeleteResult.getSuccessResult();
            }else{
                log.error("删除单个文件：{}失败！",fileName);
                return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                        GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
            }
//            fastDfsClient.deleteFile(fileInfoVO.getFileKey(), storageServer);
        } catch (Exception e) {
            log.error("删除文件失败：" + e);
            return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
                    GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
        }
    }


    /**
     * @return
     * @Description 为用户返回文件列表功能
     * @Author nily
     * @Date 2020/11/24
     * @Time 9:25 上午
     */
    @Override
    public FileInfoReturnVo selectFilesList(FileListParamsVo fileListParamsVo) {
        String systemId = fileListParamsVo.getSystemId();
        int currentPage = fileListParamsVo.getCurrentPage();
        int pageSize = fileListParamsVo.getPageSize();
        String fileName = fileListParamsVo.getFileName();
        String fileType = fileListParamsVo.getFileType();
        String fileKey = fileListParamsVo.getFileKey();
        //1.验证参数
        FileInfoReturnVo<Object> fileInfoReturnVo = new FileInfoReturnVo<>();
        if (Boolean.FALSE.equals(validateFilesListParams(systemId, currentPage, pageSize))) {
            fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode());
            fileInfoReturnVo.setResultMsg(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage());
            return fileInfoReturnVo;
        }

        try {
            //2 查询数据库列表

            AndExpression andExpression = null;
            Page<FileListVo> list = null;
            ConditionExpression<String> stringConditionExpression = QFsFileInfo.businessSystemId.eq$(systemId);
            if (StringUtils.isNotEmpty(fileName)) {
                andExpression = stringConditionExpression.and(QFsFileInfo.fileName.eq$(fileName));
            }
            if (StringUtils.isNotEmpty(fileType)) {
                if (andExpression == null) {
                    andExpression = stringConditionExpression.and(QFsFileInfo.fileType.eq$(fileType));
                } else {
                    andExpression = andExpression.and(QFsFileInfo.fileType.eq$(fileType));
                }
            }
            if (StringUtils.isNotEmpty(fileKey)) {
                if (andExpression == null) {
                    andExpression = stringConditionExpression.and(QFsFileInfo.fileKey.eq$(fileKey));
                } else {
                    andExpression = andExpression.and(QFsFileInfo.fileKey.eq$(fileKey));
                }
            }
            if (andExpression == null) {
                list = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                        .where(stringConditionExpression)
                        .paging(currentPage, pageSize).mapperTo(FileListVo.class).execute();
            } else {
                list = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                        .where(andExpression)
                        .paging(currentPage, pageSize).mapperTo(FileListVo.class).execute();
            }


            //3 封装返回参数
            if (list != null) {
                fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.FILESLIST_SUCCESS.getCode());
                fileInfoReturnVo.setResultMsg(GlobalEnum.FileResultEnum.FILESLIST_SUCCESS.getMessage());

                JSONObject data = new JSONObject();
                data.put("pageSize", list.getPageSize());
                data.put("currentPage", list.getCurrentPage());
                data.put("totalRecord", list.getRecords().size());
                data.put("records", list.getRecords());
                fileInfoReturnVo.setData(data);

                return fileInfoReturnVo;

            } else {
                fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.FILESLIST_FAILURE.getCode());
                fileInfoReturnVo.setResultMsg(GlobalEnum.FileResultEnum.FILESLIST_FAILURE.getMessage() + ",查询 List 为空");
                return fileInfoReturnVo;
            }

        } catch (Exception e) {
            log.error(e.toString());
            fileInfoReturnVo.setResultCode(GlobalEnum.FileResultEnum.FILESLIST_FAILURE.getCode());
            fileInfoReturnVo.setResultMsg(GlobalEnum.FileResultEnum.FILESLIST_FAILURE.getMessage());
            return fileInfoReturnVo;
        }

    }

    /**
     * 根据filekey获取token
     *
     * @param fileInfoVO
     * @return FastDfsTokenResult
     * @author wangcl
     */
    @Override
    public FastDfsTokenResult getToken(FileInfoVO fileInfoVO) {
        if (StringUtils.isEmpty(fileInfoVO.getFileKey())) {
            return FastDfsTokenResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + FILE_KEY_NULL_TIPS, null);
        }
//        if (StringUtils.isEmpty(fileInfoVO.getSystemId())) {
//            return FastDfsTokenResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
//                    GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + SYSTEM_ID_NULL_TIPS, null);
//        }
        try {
//            List<FsFileInfo> files = getFileByParam(fileInfoVO);
//            if (files.isEmpty()) {
//                return FastDfsTokenResult.getFailureResult(GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getCode(),
//                        GlobalEnum.FileResultEnum.FILE_NOT_FOUND.getMessage(), null);
//            }
//            String token = fastDfsClient.getToken(fileInfoVO.getFileKey());
//            if (StringUtils.isEmpty(token)) {
//                return FastDfsTokenResult.getFailureResult(GlobalEnum.FileResultEnum.TOKEN_GET_FAILURE.getCode(),
//                        GlobalEnum.FileResultEnum.TOKEN_GET_FAILURE.getMessage(), null);
//            }
            String url = lookHomeDirectory  + fileInfoVO.getFileKey();
//            String url = TrackerServerPool.getPara(GlobalConstant.HTTP_NGINX_URL) + fileInfoVO.getFileKey() + "?" + token;
            return FastDfsTokenResult.getSuccessResult(url);
        } catch (Exception e) {
            log.error("获取文件完整路径失败：" + e);
            return FastDfsTokenResult.getFailureResult(GlobalEnum.FileResultEnum.TOKEN_GET_FAILURE.getCode(),
                    GlobalEnum.FileResultEnum.TOKEN_GET_FAILURE.getMessage(), null);
        }
    }
    /**
     * 根据filekey获取token
     *
     * @param fileInfoList
     * @return FastDfsTokenResult
     * @author wangzicheng
     */
    @Override
    public Map<String, FastDfsTokenResult> getTokenMap(List<FileInfoVO> fileInfoList) {
        Map<String, FastDfsTokenResult> tokenResultMap = new HashMap<>(16);
        for (FileInfoVO fileInfoVO : fileInfoList) {
            FastDfsTokenResult tokenResult = this.getToken(fileInfoVO);
            String fileKey = fileInfoVO.getFileKey();
            if(!tokenResultMap.containsKey(fileKey) && StringUtils.equals(tokenResult.getResultCode(), GlobalEnum.FileResultEnum.TOKEN_GET_SUCCESS.getCode())){
                tokenResultMap.put(fileKey, tokenResult);
            }
        }
        return tokenResultMap;
    }

    private Boolean validateFilesListParams(String systemId, int currentPage, int pageSize) {
        if (StringUtils.isEmpty(systemId)) {
            return false;
        }
        if (StringUtils.isEmpty(String.valueOf(currentPage))) {
            return false;
        }
        return !StringUtils.isEmpty(String.valueOf(pageSize));

    }

    /**
     * 根据systemId和fileKey获取文件信息
     *
     * @param fileInfoVO
     * @return
     * @author wangcl
     */
    private List<FsFileInfo> getFileByParam(FileInfoVO fileInfoVO) {
        List<FsFileInfo> files;
        files = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                .where(QFsFileInfo.fileKey.eq$(fileInfoVO.getFileKey()))
                .execute();
        return files;
    }


    /**
     * 根据多个filekey获取多个file
     * @param fileKeyList
     * @return FastDfsTokenResult
     * @author kzb
     */
    @Override
    public List<FileListParamsVo> getFsFileInfoList(List<String> fileKeyList){
        List<FileListParamsVo> files = new ArrayList<>();
        if (fileKeyList.size()>0){
            files = QFsFileInfo.fsFileInfo.select()
                    .where(QFsFileInfo.fileKey.in$(fileKeyList))
                    .mapperTo(FileListParamsVo.class)
                    .execute();
        }
        return files;
    }
}
