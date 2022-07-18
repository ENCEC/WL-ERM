package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.auth.domain.QueryUemCompanyConditionVO;
import com.share.auth.domain.daoService.CargoTypeDTO;
import com.share.auth.domain.daoService.CargoTypeVO;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemIdCard;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.vo.CompanyTreeTableQueryVO;
import com.share.auth.model.vo.QueryCompanyTreeTableDTO;
import com.share.auth.model.vo.UemCompanyNameVO;
import com.share.auth.model.vo.UemUserVO;
import com.share.auth.service.CargoTypeService;
import com.share.auth.service.UemCompanyService;
import com.share.auth.util.QueryResultUtils;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author chenxf
 * @date 2020-10-26 10:14
 */
@Api("企业信息相关控制器")
@RestController
@Slf4j
public class UemCompanyController {

    @Autowired
    private UemCompanyService uemCompanyService;

    @Autowired
    private CargoTypeService cargoTypeService;
    /**
     * @Author:chenxf
     * @Description: 查询承运商企业/提供商企业
     * @Date: 11:31 2020/10/26
     * @Param: [opType] 查询企业类型,"cys":承运商;
     * @Return:java.lang.String
     *
     */
    @PostMapping(value = "/company/queryCompanyForCustomerType")
    @ApiOperation(value = "查询承运商企业/提供商企业",notes = "返回承运商/提供商企业信息")
    @ApiImplicitParam(name = "opType", value = "查询企业类型", required = true, dataType = "String", paramType = "queryCompanyForCustomerType")
    public Map<String,Object> queryCompanyForCustomerType(@RequestParam(value = "opType") String opType){
        if (StringUtils.isEmpty(opType)){
            return QueryResultUtils.getFailData("查询企业类型为空");
        }
        return uemCompanyService.queryUemCompanyForCustomerType(opType);
    }

    /**
     * @Author:chenxf
     * @Description: 企业管理树形表格查询接口
     * @Date: 17:53 2020/11/28
     * @Param: [companyTreeTableQueryVO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/company/queryTreeTableCompany")
    @ResponseBody
    @ApiOperation(value = "企业管理树形表格查询接口", notes = "企业管理树形表格查询接口")
    @ApiImplicitParam(name = "companyTreeTableQueryVO", value = "获取管理员申请审核列表入参", required = true, dataType = "CompanyTreeTableQueryVO", paramType = "queryTreeTableCompany")
    public ResultHelper<Page<QueryCompanyTreeTableDTO>> queryTreeTableCompany(@RequestBody CompanyTreeTableQueryVO companyTreeTableQueryVO) {
        if (companyTreeTableQueryVO.getPageSize() == 0 || companyTreeTableQueryVO.getCurrentPage() == 0) {
            return CommonResult.getFaildResultData("列表接口需传入当前页数、每页限制行数参数");
        }


        Page<QueryCompanyTreeTableDTO> queryCompanyTreeTableDtoPage = uemCompanyService.queryByTreeTable(companyTreeTableQueryVO);
        return CommonResult.getSuccessResultData(queryCompanyTreeTableDtoPage);
    }

    /**
     * @Author:chenxf
     * @Description: 管理员新增企业保存
     * @Date: 17:53 2020/11/28
     * @Param: [queryCompanyTreeTableDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/company/adminSaveCompany")
    @ResponseBody
    @ApiOperation(value = "管理员新增企业保存", notes = "管理员新增企业保存")
    @ApiImplicitParam(name = "queryCompanyTreeTableDTO", value = "企业", required = true, dataType = "QueryCompanyTreeTableDTO", paramType = "adminSaveCompany")
    public ResultHelper<Object> adminSaveCompany(@RequestBody QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        return uemCompanyService.adminSaveCompany(queryCompanyTreeTableDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 管理员修改企业保存
     * @Date: 17:53 2020/11/28
     * @Param: [queryCompanyTreeTableDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/company/adminUpdateCompany")
    @ResponseBody
    @ApiOperation(value = "管理员修改企业保存", notes = "管理员修改企业保存")
    @ApiImplicitParam(name = "queryCompanyTreeTableDTO", value = "企业", required = true, dataType = "QueryCompanyTreeTableDTO", paramType = "adminUpdateCompany")
    public ResultHelper<Object> adminUpdateCompany(@RequestBody QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        return uemCompanyService.adminUpdateCompany(queryCompanyTreeTableDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 获取企业信息查询接口
     * @Date: 18:01 2020/11/28
     * @Param: [uemCompanyId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @GetMapping("/company/queryCompanyById")
    @ResponseBody
    @ApiOperation(value = "获取企业信息查询接口", notes = "获取企业信息查询接口")
    @ApiImplicitParam(name = "uemCompanyId", value = "企业id", required = true, dataType = "Long", paramType = "queryCompanyById")
    public ResultHelper<ReviewCompanyDTO> queryCompanyById(@RequestParam(value = "uemCompanyId") Long uemCompanyId) {
        ReviewCompanyDTO reviewCompanyDTO = uemCompanyService.queryCompanyById(uemCompanyId);
        return CommonResult.getSuccessResultData(reviewCompanyDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 保存疫苗企业信息接口
     * @Date: 17:49 2021/1/20
     * @Param: [vaccineCompanyDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @ApiOperation("保存疫苗企业信息接口")
    @ApiImplicitParam(name = "vaccineCompanyDTO", value = "企业信息封装类", required = true, dataType = "VaccineCompanyDTO", paramType = "saveCompanyForVaccineCompany")
    @PostMapping("/company/saveCompanyForVaccineCompany")
    @ResponseBody
    public Map<String,Object> saveCompanyForVaccineCompany(@RequestBody VaccineCompanyDTO vaccineCompanyDTO){
        return uemCompanyService.saveCompanyForVaccineCompany(vaccineCompanyDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 调用账号权限服务上传文件接口
     * @Date: 21:01 2021/1/21
     * @Param: [file, fileType]
     * @Return:java.lang.String
     *
     */
    @ApiOperation("账号权限服务上传文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "企业信息封装类", required = true, dataType = "MultipartFile", paramType = "uploadCompanyFile"),
            @ApiImplicitParam(name = "fileType", value = "企业信息封装类", required = true, dataType = "String", paramType = "uploadCompanyFile")
    })
    @PostMapping(value = "/company/uploadCompanyFile", consumes = "multipart/form-data")
    @ResponseBody
    public ResultHelper<Object> uploadCompanyFile(@RequestPart("file") MultipartFile file, @RequestParam(value = "fileType") String fileType) {
        return uemCompanyService.uploadCompanyFile(file, fileType);
    }

    /**
     * @Author:chenxf
     * @Description: 疫苗承运商企业查询接口
     * @Date: 17:08 2021/2/7
     * @Param: [companyNameCn]
     * @Return:java.util.List<com.share.auth.domain.VaccineCompanyDTO>
     */
    @ApiOperation("疫苗承运商企业查询接口")
    @ApiImplicitParam(name = "companyNameCn", value = "企业名称", required = true, dataType = "String", paramType = "queryVaccineCompany")
    @GetMapping("/company/queryVaccineCompany")
    @ResponseBody
    public List<VaccineCompanyDTO> queryVaccineCompany(@RequestParam(value = "companyNameCn") String companyNameCn){
        return uemCompanyService.queryVaccineCompany(companyNameCn);
    }
    /**
     * @Author:cxq
     * @Description: 疫苗企业根据名称查询接口
     * @Date: 18:15 2021/1/30
     * @Param: companyNameCn
     * @Return:List<VaccineCompanyDTO>
     *
     */
    @ApiOperation("疫苗企业根据名称查询接口")
    @ApiImplicitParam(name = "companyNameCn", value = "企业名称", required = true, dataType = "String", paramType = "query")
    @GetMapping("/company/getVaccineCompanyByCompanyNameCn")
    @ResponseBody
    public List<VaccineCompanyDTO> getVaccineCompanyByCompanyNameCn(@RequestParam(value = "companyNameCn") String companyNameCn){
        return uemCompanyService.getVaccineCompanyByCompanyNameCn(companyNameCn);
    }
    /**
     * @Author:chenxf
     * @Description: 根据统一社会信用代码查询疫苗承运商企业
     * @Date: 9:57 2021/2/4
     * @Param: [organizationCode]
     * @Return:com.share.auth.domain.VaccineCompanyDTO
     *
     */
    @ApiOperation("根据统一社会信用代码查询疫苗承运商企业")
    @ApiImplicitParam(name = "organizationCode", value = "企业名称", required = true, dataType = "String", paramType = "getVaccineCompanyByOrgCode")
    @GetMapping(value ="/company/getVaccineCompanyByOrgCode")
    @ResponseBody
    public VaccineCompanyDTO getVaccineCompanyByOrgCode(@RequestParam(value = "organizationCode") String organizationCode){
        return uemCompanyService.getVaccineCompanyByOrgCode(organizationCode);
    }

    /**
     * 根据规则查询企业
     * @param companyTypeCode 企业类型代码
     * @param isMatch 是否匹配，默认false
     * @param itemCodes 企业类型选中代码
     * @return 根据规则匹配的企业
     */
    @ApiOperation("根据规则查询企业")
    @GetMapping(value = "/company/queryCompanyByRule")
    public List<UemCompanyVO> queryCompanyByRule(@RequestParam(value = "companyTypeCode") String companyTypeCode,
                                                 @RequestParam(value = "isMatch") Boolean isMatch,
                                                 @RequestParam(value = "itemCodes") List<String> itemCodes) {
        return uemCompanyService.queryCompanyByRule(companyTypeCode, isMatch, itemCodes);
    }


    /**
     * 根据企业类型分页查询企业
     * @param queryUemCompanyConditionVO 查询条件
     * @return 分页
     */
    @ApiOperation("根据企业类型分页查询企业")
    @PostMapping(value ="/company/queryUemCompanyByCompanyType")
    @ResponseBody
    public Page<UemCompanyVO> queryUemCompanyByCompanyType(@RequestBody QueryUemCompanyConditionVO queryUemCompanyConditionVO) {
        return uemCompanyService.queryUemCompanyByCompanyType(queryUemCompanyConditionVO);
    }

    /**
     * 获取所有企业（非机关单位）的管理人信息列表
     * @Author: cjh
     * @return UemUser
     */
    @ApiOperation("获取所有企业（非机关单位）的管理人信息列表")
    @GetMapping(value ="/company/queryAdministratorList")
    public List<UemUserVO> queryAdministratorList(@RequestParam(value = "companyId") Long companyId) {
        return uemCompanyService.queryAdministratorList(companyId);
    }

    /**
     * 根据企业id、企业类型获取下级企业id集合
     * @param companyId 企业id
     * @param selectedItemCodes 企业类型选中项（S7-港口，M3-地市单位，R5-粮企）
     * @return 下级企业id集合
     */
    @ApiOperation("根据企业id、企业类型获取下级企业id集合")
    @GetMapping(value = "/querySubordinateCompanyIds")
    @ResponseBody
    public List<Long> querySubordinateCompanyIds(@RequestParam(value = "companyId") Long companyId,
                                                 @RequestParam(value = "selectedItemCodes") List<String> selectedItemCodes) {
        return uemCompanyService.querySubordinateCompanyIds(companyId, selectedItemCodes);
    }

    /**
     * 根据企业id获取上级省厅企业省份
     * @param companyIds 企业id
     * @return 企业id-上级省厅企业省份对应关系
     */
    @ApiOperation("根据企业id获取上级省厅企业省份")
    @GetMapping(value = "/querySuperiorProvinceCompanyProvince")
    @ResponseBody
    public Map<Long, String> querySuperiorProvinceCompanyProvince(@RequestParam(value = "companyIds") List<Long> companyIds) {
        return uemCompanyService.querySuperiorProvinceCompanyProvince(companyIds);
    }

    /**
     * 根据企业id获取下级省厅企业省份、粮企企业省份
     * @param companyId 企业id
     * @return 企业id的下级省厅企业省份、粮企企业省份
     */
    @ApiOperation("根据企业id获取下级省厅企业的省份信息、粮企企业省份")
    @GetMapping(value = "/querySubordinateProvinceCompanyProvince")
    @ResponseBody
    public List<String> querySubordinateProvinceCompanyProvince(@RequestParam(value = "companyId") Long companyId) {
        return uemCompanyService.querySubordinateProvinceCompanyProvince(companyId);
    }

    /**
     * 获取企业可配置的权限
     * @return 企业可配置的权限
     */
    @ApiOperation("查询企业可配置的权限列表")
    @ResponseBody
    @GetMapping(value = "/company/queryCompanyConfigurablePermissions")
    public ResultHelper<List<UemCompanyConfigPermissionVO>> queryCompanyConfigurablePermissions() {
        List<UemCompanyConfigPermissionVO> permissionList = uemCompanyService.queryCompanyConfigurablePermissions();
        return CommonResult.getSuccessResultData(permissionList);
    }

    /**
     * 根据企业id获取下级省厅企业省份、粮企企业省份
     * @param companyId 企业id
     * @return 企业id的下级省厅企业省份、粮企企业省份
     */
    @ApiOperation("根据公司ID查询公司信息")
    @GetMapping(value = "/company/queryCompanyBasicInfoByCompanyId")
    @ResponseBody
    public ResultHelper<CompanyBasicInfoVO> queryCompanyBasicInfoByCompanyId(@RequestParam(value = "companyId") Long companyId){
        return CommonResult.getSuccessResultData(uemCompanyService.queryCompanyBasicInfoByCompany(companyId));
    }

    /**
     * 根据当前登录用户获取企业信息
     * @return void
     */
    @ApiOperation("根据当前登录用户获取企业信息")
    @ResponseBody
    @GetMapping(value = "/company/queryCompanyBasicInfoByUid")
    public ResultHelper<CompanyBasicInfoVO> queryCompanyBasicInfoByUid(){
        return CommonResult.getSuccessResultData(uemCompanyService.queryCompanyBasicInfoByUid());
    }

    /**
     * 根据当前登录用户获取企业信息
     * @return void
     */
    @ApiOperation("根据当前登录用户获取企业信息")
    @ResponseBody
    @GetMapping(value = "/company/queryUserCompany")
    public CompanyBasicInfoVO queryUserCompany(){
        return uemCompanyService.queryCompanyBasicInfoByUid();
    }

    /**
     * 联想控件查询企业名称
     * @Author: cjh
     * @param uemCompanyNameDTO 查询条件
     * @return UemCompanyNameVO 企业名称信息封装类
     */
    @PostMapping("/company/queryCompanyNameList")
    @ResponseBody
    @ApiOperation(value = "联想控件查询企业名称", notes = "联想控件查询企业名称")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "UemCompanyNameDTO",type = "query")
    public ResultHelper<Page<UemCompanyNameVO>> queryCompanyNameList(@RequestBody UemCompanyNameDTO uemCompanyNameDTO){
        Page<UemCompanyNameVO> uemCompanyNameVOPage = uemCompanyService.queryCompanyNameList(uemCompanyNameDTO);
        return CommonResult.getSuccessResultData(uemCompanyNameVOPage);
    }

    @PostMapping("/company/selectAuthCompany")
    @ResponseBody
    @ApiOperation(value = "根据ID数组查询企业信息", notes = "根据ID数组查询企业信息")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public List<UemCompanyVO> selectAuthCompany(@RequestBody List<Long> ids){
         List<UemCompanyVO> uemCompanies = uemCompanyService.selectAuthCompany(ids);
        return uemCompanies;
    }

    /**
     * 企业名称唯一性
     * @Author: Linja
     * @param name 查询条件
     * @return UemCompanyVO 企业信息封装类
     */
    @PostMapping("/company/queryComName")
    @ResponseBody
    @ApiOperation(value = "根据ID数组查询企业信息", notes = "根据ID数组查询企业信息")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public List<UemCompanyVO> queryComName(@RequestBody String name){
        List<UemCompanyVO> uemCompanies = uemCompanyService.queryComName(name);
        return uemCompanies;
    }

    /**
     * 社会信用代码唯一性
     * @Author: Linja
     * @param name 查询条件
     * @return UemCompanyVO 企业信息封装类
     */
    @PostMapping("/company/queryOrgCode")
    @ResponseBody
    @ApiOperation(value = "根据ID数组查询企业信息", notes = "根据ID数组查询企业信息")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public List<UemCompanyVO> queryOrgCode(@RequestBody String name){
        List<UemCompanyVO> uemCompanies = uemCompanyService.queryOrgCode(name);
        return uemCompanies;
    }

    /**
     * 选中的商品属性
     * @Author: Linja
     * @param cargoTypeDTO 查询条件
     * @return UemCompanyVO 企业信息封装类
     */
    @PostMapping("/company/queryCargoType")
    @ResponseBody
    @ApiOperation(value = "根据ID数组查询企业信息", notes = "根据ID数组查询企业信息")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public List<CargoTypeVO> queryCargoType(@RequestBody CargoTypeDTO cargoTypeDTO){
        List<CargoTypeVO> uemCompanies = cargoTypeService.queryChosenGoods(cargoTypeDTO);
        return uemCompanies;
    }

    /**
     * 公司启用
     * @Author: Linja
     * @param queryCompanyTreeTableDTO 查询条件
     * @return UemCompanyVO 企业信息封装类
     */
    @PostMapping("/company/updateComValid")
    @ResponseBody
    @ApiOperation(value = "根据ID数组查询企业信息", notes = "根据ID数组查询企业信息")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public ResultHelper<Object> updateComValid(@RequestBody QueryCompanyTreeTableDTO queryCompanyTreeTableDTO){
        uemCompanyService.updateCompanyValid(queryCompanyTreeTableDTO);
        return CommonResult.getSuccessResultData("成功");
    }
}
