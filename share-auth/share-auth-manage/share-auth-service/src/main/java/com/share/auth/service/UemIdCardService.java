package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.auth.model.vo.UserIdCardQueryVO;
import com.share.file.domain.*;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author xrp
 * @date 20201021
 */
@Api("用户模块")
public interface UemIdCardService {


    /**
     * 新增实名认证
     *
     * @param uemIdCardDto 实名信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> saveUemIdCard(UemIdCardDto uemIdCardDto);

    /**
     * 新增企业
     *
     * @param uemCompanyDto 企业信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Map<String, Object>> saveUemCompany(UemCompanyDto uemCompanyDto);

    /**
     * 申请绑定企业
     *
     * @param uemCompanyManageDto 管理员表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> bindUemCompany(UemCompanyManageDto uemCompanyManageDto);

    /**
     * 申请管理员
     *
     * @param uemCompanyManageDto 管理员表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> applyAdmin(UemCompanyManageDto uemCompanyManageDto);


    /**绑定原物流交换代码
     * @param  userid 物流交换代码
     * @param password 密码
     * @return String
     * @author xrp
     * */
    String bingOriginalLogisticsSwap(String userid,String password);

    /**文件上传
     *
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @param file 文件
     * @return FastDfsUploadResult
     * @author xrp
     * */
    FastDfsUploadResult upload(String fileType, String fileName,String businessSystemId, MultipartFile file);

    /**文件下载
     *
     * @param fileKey 文件Id
     * @author xrp
     * @param response 响应
     * @throws IOException 异常
     * */
    void download(String fileKey, HttpServletResponse response) throws IOException;


    /**文件删除
     * @param fileKey 文件Id
     * @return FastDfsDownloadResult
     * @author xrp
     * */
    FastDfsDeleteResult deleteFile(String fileKey);


    /**
     * 文件获取完整路径
     *
     * @param fileKey 文件Id
     * @return xrp
     */
    FastDfsTokenResult getFullUrl(String fileKey);


    /**
     * 查询文件列表
     *
     * @param fileKey 文件Id
     * @return xrp
     */
    FileInfoReturnVo<Object> selectFilesList(String fileKey);


    /**
     * 分页查询用户实名认证记录
     * @Author:chenxf
     * @Description: 分页查询用户实名认证记录
     * @Date: 15:56 2020/10/29
     * @param userIdCardQueryVO: [userIdCardQueryVO] 查询条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.QueryUserIdCardDTO>
     *
     */
    Page<QueryUserIdCardDTO> queryByPage(UserIdCardQueryVO userIdCardQueryVO);
    
    /**
     * 根据实名认证表id查询用户信息
     * @Author:chenxf
     * @Description: 根据实名认证表id查询用户信息
     * @Date: 15:57 2020/10/29
     * @param uemIdCardId: [uemIdCardId]
     * @return :com.share.auth.domain.QueryUserIdCardDTO
     *
     */
    QueryUserIdCardDTO queryByUemIdCardId(Long uemIdCardId);

    /**
     * 实名认证审核操作
     * @Author:chenxf
     * @Description: 实名认证审核操作
     * @Date: 16:03 2020/10/29
     * @param queryUserIdCardDTO: [queryUserIdCardDTO]
     * @Return:void
     *
     */
    void reviewIdCard(QueryUserIdCardDTO queryUserIdCardDTO);
}
