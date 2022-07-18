package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.exception.BusinessRuntimeException;
import com.share.auth.domain.QueryCompanyManagerDTO;
import com.share.auth.domain.QueryUserIdCardDTO;
import com.share.auth.domain.ReviewCompanyDTO;
import com.share.auth.model.vo.CompanyCheckQueryVO;
import com.share.auth.model.vo.CompanyManagerQueryVO;
import com.share.auth.model.vo.UserIdCardQueryVO;
import com.share.auth.service.UemCompanyHistoryService;
import com.share.auth.service.UemCompanyManagerService;
import com.share.auth.service.UemIdCardService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author:chenxf
 * @Description: 审核功能控制器
 * @Date: 10:23 2020/10/29
 * @Param: 
 * @Return:
 *
 */
@Api("审核模块控制器")
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private UemCompanyHistoryService uemCompanyHistoryService;

    @Autowired
    private UemIdCardService uemIdCardService;

    @Autowired
    private UemCompanyManagerService uemCompanyManagerService;

    /**
     * 无当前页数、每页行数传参提示
     */
    private static final String PAGES_INFO_REQUIRED_PROMPT = "列表接口需传入当前页数、每页限制行数参数";
    /**
     * 审核操作成功提示
     */
    private static final String SUCCESS_OPERATION_PROMPT = "审核操作成功";

    /**
     * @return
     * @Author:chenxf
     * @Description: 获取企业资质审核列表
     * @Date: 17:51 2020/11/28
     * @Param: [companyCheckQueryVO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/queryCompanyByReview")
    @ResponseBody
    @ApiOperation(value = "获取企业资质审核列表", notes = "获取企业资质审核列表")
    @ApiImplicitParam(name = "companyCheckQueryVO", value = "获取企业资质审核列表入参", required = true, dataType = "CompanyCheckQueryVO", paramType = "queryCompanyByReview")
    public ResultHelper<Page<ReviewCompanyDTO>> queryCompanyByReview(@RequestBody CompanyCheckQueryVO companyCheckQueryVO){
        if (companyCheckQueryVO.getPageSize() == 0 || companyCheckQueryVO.getCurrentPage() == 0){
            return CommonResult.getFaildResultData(PAGES_INFO_REQUIRED_PROMPT);
        }
        Page<ReviewCompanyDTO> uemCompanyPage =  uemCompanyHistoryService.queryByPage(companyCheckQueryVO);
        return CommonResult.getSuccessResultData(uemCompanyPage);
    }

    /**
     * @Author:chenxf
     * @Description: 企业审核接口
     * @Date: 17:51 2020/11/28
     * @Param: [uemCompany]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/reviewCompany")
    @ResponseBody
    @ApiOperation(value = "企业审核", notes = "企业审核")
    @ApiImplicitParam(name = "uemCompany", value = "企业历史信息表", required = true, dataType = "ReviewCompanyDTO", paramType = "reviewCompany")
    public ResultHelper<String> reviewCompany(@RequestBody ReviewCompanyDTO uemCompany) {
        uemCompanyHistoryService.reviewCompany(uemCompany);
        return CommonResult.getSuccessResultData(SUCCESS_OPERATION_PROMPT);
    }

    /**
     * @Author:chenxf
     * @Description: 获取实名认证审核列表接口
     * @Date: 17:51 2020/11/28
     * @Param: [userIdCardQueryVO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/queryUserIdCardByReview")
    @ResponseBody
    @ApiOperation(value = "获取实名认证审核列表", notes = "获取实名认证审核列表")
    @ApiImplicitParam(name = "userIdCardQueryVO", value = "获取实名认证审核列表入参", required = true, dataType = "UserIdCardQueryVO", paramType = "queryUserIdCardByReview")
    public ResultHelper<Page<QueryUserIdCardDTO>> queryUserIdCardByReview(@RequestBody UserIdCardQueryVO userIdCardQueryVO){
        if (userIdCardQueryVO.getPageSize() == 0 || userIdCardQueryVO.getCurrentPage() == 0){
            return CommonResult.getFaildResultData(PAGES_INFO_REQUIRED_PROMPT);
        }
        Page<QueryUserIdCardDTO> queryUserIdCardDtoPage =  uemIdCardService.queryByPage(userIdCardQueryVO);
        return CommonResult.getSuccessResultData(queryUserIdCardDtoPage);
    }
    
    /**
     * @Author:chenxf
     * @Description: 获取实名认证审核信息接口
     * @Date: 17:51 2020/11/28
     * @Param: [uemIdCardId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     * @return
     */
    @GetMapping("/queryUserIdCardInfo")
    @ResponseBody
    @ApiOperation(value = "获取实名认证审核信息", notes = "获取实名认证审核信息")
    @ApiImplicitParam(name = "uemIdCardId", value = "获取实名认证审核信息入参", required = true, dataType = "Long", paramType = "queryUserIdCardInfo")
    public ResultHelper<QueryUserIdCardDTO> queryUserIdCardInfo(@RequestParam(value = "uemIdCardId") Long uemIdCardId) {
        QueryUserIdCardDTO queryUserIdCardDTO = uemIdCardService.queryByUemIdCardId(uemIdCardId);
        return CommonResult.getSuccessResultData(queryUserIdCardDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 实名认证审核接口
     * @Date: 17:52 2020/11/28
     * @Param: [queryUserIdCardDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/reviewIdCard")
    @ResponseBody
    @ApiOperation(value = "实名认证审核", notes = "实名认证审核")
    @ApiImplicitParam(name = "queryUserIdCardDTO", value = "实名认证记录", required = true, dataType = "QueryUserIdCardDTO", paramType = "reviewIdCard")
    public ResultHelper<String> reviewIdCard(@RequestBody QueryUserIdCardDTO queryUserIdCardDTO) {
        uemIdCardService.reviewIdCard(queryUserIdCardDTO);
        return CommonResult.getSuccessResultData(SUCCESS_OPERATION_PROMPT);
    }

    /**
     * @Author:chenxf
     * @Description: 获取管理员申请审核列表接口
     * @Date: 17:52 2020/11/28
     * @Param: [companyManagerQueryVO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/queryCompanyManagerByReview")
    @ResponseBody
    @ApiOperation(value = "获取管理员申请审核列表", notes = "获取管理员申请审核列表")
    @ApiImplicitParam(name = "companyManagerQueryVO", value = "获取管理员申请审核列表入参", required = true, dataType = "CompanyManagerQueryVO", paramType = "queryCompanyManagerByReview")
    public ResultHelper<Page<QueryCompanyManagerDTO>> queryCompanyManagerByReview(@RequestBody CompanyManagerQueryVO companyManagerQueryVO){
        if (companyManagerQueryVO.getPageSize() == 0 || companyManagerQueryVO.getCurrentPage() == 0){
            return CommonResult.getFaildResultData(PAGES_INFO_REQUIRED_PROMPT);
        }
        Page<QueryCompanyManagerDTO> queryCompanyManagerDtoPage = uemCompanyManagerService.queryByPage(companyManagerQueryVO);
        return CommonResult.getSuccessResultData(queryCompanyManagerDtoPage);
    }

    /**
     * @Author:chenxf
     * @Description: 获取管理员申请审核信息接口
     * @Date: 17:52 2020/11/28
     * @Param: [uemCompanyManagerId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @GetMapping("/queryUserInfoForCompanyManger")
    @ResponseBody
    @ApiOperation(value = "获取管理员申请审核信息", notes = "获取管理员申请审核信息")
    @ApiImplicitParam(name = "uemCompanyManagerId", value = "获取管理员申请审核信息入参", required = true, dataType = "Long", paramType = "queryUserInfoForCompanyManger")
    public ResultHelper<QueryCompanyManagerDTO> queryUserInfoForCompanyManger(@RequestParam(value = "uemCompanyManagerId") Long uemCompanyManagerId) {
        QueryCompanyManagerDTO queryCompanyManagerDTO = uemCompanyManagerService.queryByUemCompanyManagerId(uemCompanyManagerId);
        return CommonResult.getSuccessResultData(queryCompanyManagerDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 企业管理员审核接口
     * @Date: 17:54 2020/11/28
     * @Param: [queryCompanyManagerDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/reviewCompanyManager")
    @ResponseBody
    @ApiOperation(value = "企业管理员审核", notes = "企业管理员审核")
    @ApiImplicitParam(name = "queryCompanyManagerDTO", value = "管理员申请记录", required = true, dataType = "QueryCompanyManagerDTO", paramType = "reviewCompanyManager")
    public ResultHelper<String> reviewCompanyManager(@RequestBody QueryCompanyManagerDTO queryCompanyManagerDTO) {
        uemCompanyManagerService.reviewCompanyManager(queryCompanyManagerDTO);
        return CommonResult.getSuccessResultData(SUCCESS_OPERATION_PROMPT);
    }
}
