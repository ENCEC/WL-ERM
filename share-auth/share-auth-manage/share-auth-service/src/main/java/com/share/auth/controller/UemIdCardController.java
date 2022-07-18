package com.share.auth.controller;

import com.share.auth.domain.*;
import com.share.auth.service.UemIdCardService;
import com.share.auth.service.UserCompanyManageService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.file.domain.*;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author xrp
 */
@Api("实名认证控制器")
@Controller
@RequestMapping("uemIdCard")
@Slf4j
public class UemIdCardController {

    @Autowired
    private UemIdCardService uemIdCardService;

    @Autowired
    private UserCompanyManageService userCompanyManageService;

    @Autowired
    private DefaultUserService userService;

    /**
     * 新增实名认证
     *
     * @param uemIdCardDto 实名信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("新增实名认证")
    @ApiImplicitParam(name = "uemIdCardDto", value = "实名信息封装类", required = true, dataType = "UemIdCardDto", paramType = "saveUemIdCard")
    @PostMapping("/saveUemIdCard")
    @ResponseBody
    public ResultHelper<Object> saveUemIdCard(@RequestBody UemIdCardDto uemIdCardDto) {
        return uemIdCardService.saveUemIdCard(uemIdCardDto);
    }


    /**
     * 新增企业
     *
     * @param uemCompanyDto 企业信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("新增企业")
    @ApiImplicitParam(name = "uemCompanyDto", value = "企业信息表封装类", required = true, dataType = "UemCompanyDto", paramType = "saveUemCompany")
    @PostMapping("/saveUemCompany")
    @ResponseBody
    public ResultHelper<Map<String, Object>> saveUemCompany(@RequestBody UemCompanyDto uemCompanyDto) {
        return uemIdCardService.saveUemCompany(uemCompanyDto);
    }

    /**
     * 解除绑定
     *
     * @param uemUserCompanyDto 用户企业绑定表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("解除绑定")
    @ApiImplicitParam(name = "uemUserCompanyDto", value = "用户企业绑定表封装类", required = true, dataType = "UemUserCompanyDto", paramType = "unbindUemUser")
    @PostMapping("/unbindUemUser")
    @ResponseBody
    public ResultHelper<Object> unbindUemUser(@RequestBody UemUserCompanyDto uemUserCompanyDto) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData("data", "获取登录用户信息失败！");
        }
        return userCompanyManageService.unbindUser(userInfoModel.getUemUserId().toString(), uemUserCompanyDto.getUemCompanyId());
    }

    /**
     * 申请绑定企业
     *
     * @param uemCompanyManageDto 管理员表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("申请绑定企业")
    @ApiImplicitParam(name = "uemCompanyManageDto", value = "管理员表封装类", required = true, dataType = "UemCompanyManageDto", paramType = "bindUemCompany")
    @PostMapping("/bindUemCompany")
    @ResponseBody
    public ResultHelper<Object> bindUemCompany(@RequestBody UemCompanyManageDto uemCompanyManageDto) {
        return uemIdCardService.bindUemCompany(uemCompanyManageDto);
    }

    /**
     * 申请管理员
     *
     * @param uemCompanyManageDto 管理员表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("申请管理员")
    @ApiImplicitParam(name = "uemCompanyManageDto", value = "管理员表封装类", required = true, dataType = "UemCompanyManageDto", paramType = "applyAdmin")
    @PostMapping("/applyAdmin")
    @ResponseBody
    public ResultHelper<Object> applyAdmin(@RequestBody UemCompanyManageDto uemCompanyManageDto) {
        return uemIdCardService.applyAdmin(uemCompanyManageDto);
    }

    /**绑定原物流交换代码
     * @aram userid 物流交换代码
     * @param password 密码
     * @return String
     * @author xrp
     * */
    @ApiOperation("绑定原物流交换代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "物流交换代码", required = true, dataType = "String", paramType = "bingOriginalLogisticsSwap"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "bingOriginalLogisticsSwap")
    })
    @GetMapping("/bingOriginalLogisticsSwap")
    @ResponseBody
    public String bingOriginalLogisticsSwap(@RequestParam String userid,@RequestParam String password){
        return uemIdCardService.bingOriginalLogisticsSwap(userid,password);
    }

    /**文件上传
     *
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @param file 文件
     * @return FastDfsUploadResult
     * @author xrp
     * */
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "upload"),
            @ApiImplicitParam(name = "fileType", value = "文件类型", required = false, dataType = "String", paramType = "upload"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", required = false, dataType = "String", paramType = "upload")
    })
    @PostMapping("/upload")
    @ResponseBody
    public FastDfsUploadResult upload(MultipartFile file, @RequestParam(value = "fileType", required = false) String fileType,
                                      @RequestParam(value = "fileName", required = false) String fileName,
                                      @RequestParam(value = "businessSystemId",required = false) String businessSystemId) {

        return uemIdCardService.upload(fileType, fileName,businessSystemId, file);
    }

    /**
     * 文件下载
     *
     * @param fileKey 文件Id
     * @return FastDfsDownloadResult
     * @author xrp
     */
    @ApiOperation("文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "response", value = "response", required = true, dataType = "HttpServletResponse", paramType = "download"),
            @ApiImplicitParam(name = "fileKey", value = "文件Id", required = true, dataType = "String", paramType = "download")
    })
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, @RequestParam("fileKey") String fileKey) throws IOException {
        uemIdCardService.download(fileKey, response);
    }

    /**文件删除
     * @param fileInfoVO 文件信息V0
     * @return FastDfsDownloadResult
     * @author xrp
     * */
    @ApiOperation("文件删除")
    @ApiImplicitParam(name = "fileInfoVO", value = "文件信息V0", required = true, dataType = "FileInfoVO", paramType = "deleteFile")
    @PostMapping("/deleteFile")
    @ResponseBody
    public FastDfsDeleteResult deleteFile(@RequestBody FileInfoVO fileInfoVO){
        return uemIdCardService.deleteFile(fileInfoVO.getFileKey());
    }

    /**文件获取完整路径
     * @param fileInfoVO 文件
     * @return FastDfsTokenResult
     * @return xrp
     * */
    @ApiOperation("文件获取全路径")
    @ApiImplicitParam(name = "fileInfoVO", value = "文件信息VO", required = true, dataType = "FileInfoVO", paramType = "getFullUrl")
    @GetMapping("/getFullUrl")
    @ResponseBody
    public FastDfsTokenResult getFullUrl(FileInfoVO fileInfoVO) {
        return uemIdCardService.getFullUrl(fileInfoVO.getFileKey());
    }

    /**
     * 查询文件列表
     *
     * @param fileListParamsVo 查询文件列表入参 Vo
     * @return xrp
     */
    @ApiOperation("文件列表")
    @ApiImplicitParam(name = "fileListParamsVo", value = "查询文件列表入参", required = true, dataType = "FileListParamsVo", paramType = "selectFilesList")
    @PostMapping("/selectFilesList")
    @ResponseBody
    public FileInfoReturnVo<Object> selectFilesList(@RequestBody FileListParamsVo fileListParamsVo) {
        return uemIdCardService.selectFilesList(fileListParamsVo.getFileKey());
    }

}
