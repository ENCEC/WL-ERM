package com.share.auth.api;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 15100
 * @title: UemCompanyInterface
 * @projectName support
 * @date 2020/11/1017:18
 */
@FeignClient(value = "${application.name.auth}")
public interface UemCompanyInterface {

    /**
     * 查询承运商企业/提供商企业
     * @Author:chenxf
     * @Description: 查询承运商企业/提供商企业
     * @Date: 11:31 2020/10/26
     * @param opType : [opType] 查询企业类型,"cys":承运商;
     * @return  :java.lang.String
     *
     */
    @PostMapping(value = "/company/queryCompanyForCustomerType")
    Map<String,Object> queryCompanyForCustomerType(@RequestParam(value = "opType") String opType);


    /**
     * 保存疫苗企业信息接口
     * @Author:chenxf
     * @Description: 保存疫苗企业信息接口
     * @Date: 17:49 2021/1/20
     * @param vaccineCompanyDTO: [vaccineCompanyDTO] 企业信息数据
     * @return :java.util.Map<java.lang.String,java.lang.Object> 返回保存结果
     *
     */
    @PostMapping(value = "/company/saveCompanyForVaccineCompany")
    Map<String,Object> saveCompanyForVaccineCompany(@RequestBody VaccineCompanyDTO vaccineCompanyDTO);

    /**
     * 调用账号权限服务上传文件接口
     *
     * @param file:    [file, fileType]文件，文件类型
     * @param fileType 文件类型
     * @return :java.lang.String
     * @Author:chenxf
     * @Description: 调用账号权限服务上传文件接口
     * @Date: 21:00 2021/1/21
     */
    @RequestMapping(value = "/company/uploadCompanyFile", consumes = "multipart/form-data")
    ResultHelper<String> uploadCompanyFile(@RequestPart("file") MultipartFile file, @RequestParam(value = "fileType") String fileType);

    /**
     * 疫苗承运商企业查询接口
     *
     * @param companyNameCn 公司中文名称
     * @return :java.util.List<com.share.auth.domain.VaccineCompanyDTO>
     * @Author:chenxf
     * @Description: 疫苗承运商企业查询接口
     * @Date: 17:08 2021/2/7
     */
    @GetMapping(value = "/company/queryVaccineCompany")
    List<VaccineCompanyDTO> queryVaccineCompany(@RequestParam(value = "companyNameCn") String companyNameCn);
    /**
     * 根据企业名称查询疫苗承运商企业
     * @Author:chenxf
     * @Description: 根据企业名称查询疫苗承运商企业
     * @Date: 9:54 2021/2/4
     * @param companyNameCn 公司中文名称
     * @return :java.util.List<com.share.auth.domain.VaccineCompanyDTO>
     *
     */
    @GetMapping(value ="/company/getVaccineCompanyByCompanyNameCn")
    List<VaccineCompanyDTO> getVaccineCompanyByCompanyNameCn(@RequestParam(value = "companyNameCn") String companyNameCn);

    /**
     * 根据统一社会信用代码查询疫苗承运商企业
     * @Author:chenxf
     * @Description: 根据统一社会信用代码查询疫苗承运商企业
     * @Date: 9:54 2021/2/4
     * @param organizationCode 统一社会信用代码
     * @return :com.share.auth.domain.VaccineCompanyDTO
     *
     */
    @GetMapping(value ="/company/getVaccineCompanyByOrgCode")
    VaccineCompanyDTO getVaccineCompanyByOrgCode(@RequestParam(value = "organizationCode") String organizationCode);

    /**
     * 根据企业类型分页查询企业
     * @param queryUemCompanyConditionVO 查询条件
     * @return 分页
     */
    @PostMapping(value ="/company/queryUemCompanyByCompanyType")
    Page<UemCompanyVO> queryUemCompanyByCompanyType(@RequestBody QueryUemCompanyConditionVO queryUemCompanyConditionVO);

    /**
     * 获取所有企业（非机关单位）的管理人信息列表
     * @Author: cjh
     * @return UemUser
     */
    @GetMapping(value ="/company/queryAdministratorList")
    List<UemUserVO> queryAdministratorList(@RequestParam(value = "companyId") Long companyId);

    /**
     * 根据当前登录用户获取企业信息
     * @return void
     */
    @GetMapping(value = "/company/queryCompanyBasicInfoByUid")
    CompanyBasicInfoVO queryCompanyBasicInfoByUid();

    /**
     * 根据当前登录用户获取企业信息
     * @return void
     */
    @GetMapping(value = "/company/queryUserCompany")
    CompanyBasicInfoVO queryUserCompany();

    /**
     * 根据当前登录用户获取企业信息
     * @return void
     */
    @GetMapping(value = "/company/selectAuthCompany")
    List<UemCompanyVO> selectAuthCompany(List<Long> ids);
}
