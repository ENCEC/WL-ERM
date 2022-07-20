package com.share.auth.service;


import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.auth.domain.platform.UemCompanyDTO;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.vo.*;
import com.share.auth.model.vo.UemUserVO;
import com.share.support.result.ResultHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:chenxf
 * @Description: 企业服务层
 * @Date: 15:04 2020/11/3
 * @Param:
 * @Return:
 *
 */
public interface UemCompanyService {
    /**
     * 企业管理树形表格数据查询接口
     * @Author:chenxf
     * @Description: 企业管理树形表格数据查询接口
     * @Date: 14:59 2020/11/3
     * @param companyTreeTableQueryVO : [companyTreeTableQueryVO] 条件
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.vo.QueryCompanyTreeTableDTO>
     *
     */
    Page<QueryCompanyTreeTableDTO> queryByTreeTable(CompanyTreeTableQueryVO companyTreeTableQueryVO);

    /**
     * 管理员新增企业
     *
     * @param queryCompanyTreeTableDTO : [queryCompanyTreeTableDTO] 查询条件VO
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 管理员新增修改企业保存
     * @Date: 18:41 2020/11/28
     */
    ResultHelper<Object> adminSaveCompany(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO);

    /**
     * 管理员修改企业
     *
     * @param queryCompanyTreeTableDTO : [queryCompanyTreeTableDTO] 查询条件VO
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 管理员新增修改企业保存
     * @Date: 18:41 2020/11/28
     */
    ResultHelper<Object> adminUpdateCompany(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO);

    /**
     * 查询企业信息
     * @Author:chenxf
     * @Description: 查询企业信息
     * @Date: 18:41 2020/11/28
     * @param uemCompanyId : [uemCompanyId] 公司id
     * @return :com.share.auth.domain.ReviewCompanyDTO
     *
     */
    ReviewCompanyDTO queryCompanyById(Long uemCompanyId);

    /**
     * 保存疫苗企业信息接口
     * @Author:chenxf
     * @Description: 保存疫苗企业信息接口
     * @Date: 17:49 2021/1/20
     * @param vaccineCompanyDTO: [vaccineCompanyDTO]
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    Map<String, Object> saveCompanyForVaccineCompany(VaccineCompanyDTO vaccineCompanyDTO);

    /**
     * 疫苗承运商企业查询接口
     * @Author:chenxf
     * @Description: 疫苗承运商企业查询接口
     * @Date: 17:08 2021/2/7
     * @param companyNameCn: [companyNameCn] 公司中文名称
     * @return :java.util.List<com.share.auth.domain.VaccineCompanyDTO>
     *
     */
    List<VaccineCompanyDTO> queryVaccineCompany(String companyNameCn);

    /**
     * 上传文件接口
     *
     * @param file:    文件
     * @param fileType 文件类型
     * @return :java.lang.String
     * @Author:chenxf
     * @Description: 上传文件接口
     * @Date: 21:01 2021/1/21
     */
    ResultHelper<Object> uploadCompanyFile(MultipartFile file, String fileType);

    /**
     * 疫苗企业根据名称查询接口
     * @Author:cxq
     * @Description: 疫苗企业根据名称查询接口
     * @Date: 18:15 2021/1/30
     * @param companyNameCn : 公司中文名称
     * @return :List<VaccineCompanyDTO>
     *
     */
    List<VaccineCompanyDTO> getVaccineCompanyByCompanyNameCn(String companyNameCn);

    /**
     * 根据统一社会信用代码查询疫苗承运商企业
     * @Author:chenxf
     * @Description: 根据统一社会信用代码查询疫苗承运商企业
     * @Date: 9:58 2021/2/4
     * @param organizationCode: 统一社会信用代码
     * @return :com.share.auth.domain.VaccineCompanyDTO
     *
     */
    VaccineCompanyDTO getVaccineCompanyByOrgCode(String organizationCode);

    /**
     * 国家综合交通运输信息平台增删改组织机构
     * @param uemCompanyOperateVO 组织信息
     * @return 返回操作结果
     */
    OperateResultVO operateUemCompany(UemCompanyOperateVO uemCompanyOperateVO);


    /**
     * 根据企业id、企业类型获取下级企业id集合
     * @param companyId 企业id
     * @param selectedItemCodes 企业类型选中项（S7-港口，M3-地市单位，R5-粮企）
     * @return 下级企业id集合
     */
    List<Long> querySubordinateCompanyIds(Long companyId, List<String> selectedItemCodes);

    /**
     * 获取所有企业（非机关单位）的管理人信息列表
     * @Author: cjh
     * @return UemUser
     */
    List<UemUserVO> queryAdministratorList(Long companyId);

    /**
     * 获取企业可配置的权限
     * @return 企业可配置的权限
     */
   List<UemCompanyConfigPermissionVO> queryCompanyConfigurablePermissions();

    /**
     * 新增企业权限配置
     * @param uemCompanyRoleVoList 企业权限配置列表
     * @param companyId 企业id
     */
    void addUemCompanyRole(List<UemCompanyRoleVO> uemCompanyRoleVoList, Long companyId);

    /**
     * 根据登录用户获取企业信息
     * @return CompanyBasicInfoVO 企业信息封装类
     */
    CompanyBasicInfoVO queryCompanyBasicInfoByUid();

    /**
     * 根据公司ID查询
     * @return CompanyBasicInfoVO 企业信息封装类
     */
    CompanyBasicInfoVO queryCompanyBasicInfoByCompany(Long companyId);

    /**
     * 联想控件查询企业名称
     * @Author: cjh
     * @return UemCompanyNameVO 企业名称信息封装类
     */
    Page<UemCompanyNameVO> queryCompanyNameList(UemCompanyNameDTO uemCompanyNameDTO);

    List<UemCompanyVO> selectAuthCompany(List<Long> ids);

    List<UemCompanyVO> queryComName(String companyNameCn);

    List<UemCompanyVO> queryOrgCode(String code);

    void updateCompanyValid(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO);

}
