package com.share.file.controller;

import com.share.file.enums.GlobalEnum;
import com.share.file.model.entity.FsFileInfo;
import com.share.file.model.querymodels.QFsFileInfo;
import com.share.file.model.vo.FileInfoVO;
import com.share.file.util.fastdfs.FastDfsClient;
import com.share.file.util.fastdfs.FastDfsDeleteResult;
import com.share.file.util.fastdfs.FastDfsException;
import com.share.file.util.fastdfs.StorageConfig;
import org.apache.http.entity.ContentType;
import org.csource.fastdfs.StorageServer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ExternalFileControllerTest {
    @Autowired
    private StorageConfig storageConfig;
    @Value("${fastdfs.config.name}")
    private String FASTDFS_CONFIG_PATH;
    @Autowired
    private FastDfsClient fastDfsClient;

    @Test
    void uploadExternalFile() throws IOException {
        InputStream inputStream = null;
        String filePath = "C:\\Users\\lenovo\\Desktop\\202001.xlsx";
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        try {
            System.out.println(FASTDFS_CONFIG_PATH);
            StorageServer storageServer = storageConfig.getStorageServer();
            String fileKey = fastDfsClient.upload(inputStream, "202001.xlsx", null,storageServer);
            System.out.println(fileKey);
        } catch (FastDfsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void download() throws IOException {
        String fileKey = "group1/M00/00/00/rBAK31-byRGAD2mYAAA22PZxQ3U119.jpg";
        FileOutputStream fos = null;
        try {
            StorageServer storageServer = storageConfig.getStorageServer();
            InputStream is = fastDfsClient.download(fileKey, storageServer);
            fos = new FileOutputStream("D:/feigei.jpg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
                fos.flush();
            }
        } catch (FastDfsException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                fos.close();
            }
        }
    }

    @Test
    void deleteFile() {
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setFileKey("group1/M00/00/04/rBAK31_GQg6AdCoZAGydi4ujY44171.doc");
        fileInfoVO.setSystemId("SHAREAUTH");
        List<FsFileInfo> files;
        files = QFsFileInfo.fsFileInfo.select(QFsFileInfo.fsFileInfo.fieldContainer())
                .where(QFsFileInfo.businessSystemId.eq$(fileInfoVO.getSystemId())
                        .and(QFsFileInfo.fileKey.eq$(fileInfoVO.getFileKey())))
                .execute();
        if (files.isEmpty()) {
//            return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.File_NOT_FOUND.getCode(),
//                    GlobalEnum.FileResultEnum.File_NOT_FOUND.getMessage());
        }
        FsFileInfo fsFileInfo = files.get(0);
        int count = QFsFileInfo.fsFileInfo.delete(fsFileInfo);
        if (count <= 0) {
//            return FastDfsDeleteResult.getFailureResult(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(),
//                    GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
        }
    }
}