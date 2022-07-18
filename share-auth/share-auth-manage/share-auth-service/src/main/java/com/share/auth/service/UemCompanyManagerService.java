package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.QueryCompanyManagerDTO;
import com.share.auth.model.vo.CompanyManagerQueryVO;
/**
 * @Author:chenxf
 * @Description: 企业管理员服务层
 * @Date: 14:52 2020/11/3
 * @Param: 
 * @Return:
 *
 */
public interface UemCompanyManagerService {
    /**
     * 管理员申请审核列表分页查询
     * @Author:chenxf
     * @Description: 管理员申请审核列表分页查询
     * @Date: 14:52 2020/11/3
     * @param companyManagerQueryVO: [companyManagerQueryVO] 条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.QueryCompanyManagerDTO>
     *
     */
    Page<QueryCompanyManagerDTO> queryByPage(CompanyManagerQueryVO companyManagerQueryVO);

    /**
     * 根据企业管理员表id查询管理员申请信息
     * @Author:chenxf
     * @Description: 根据企业管理员表id查询管理员申请信息
     * @Date: 14:53 2020/11/3
     * @param uemCompanyManagerId: [uemCompanyManagerId] 管理员id
     * @return :com.share.auth.domain.QueryCompanyManagerDTO
     *
     */
    QueryCompanyManagerDTO queryByUemCompanyManagerId(Long uemCompanyManagerId);

    /**
     * 企业管理员申请审核
     * @Author:chenxf
     * @Description: 企业管理员申请审核
     * @Date: 14:54 2020/11/3
     * @param queryCompanyManagerDTO : [queryCompanyManagerDTO]
     * @Return:void
     *
     */
    void reviewCompanyManager(QueryCompanyManagerDTO queryCompanyManagerDTO);
}
