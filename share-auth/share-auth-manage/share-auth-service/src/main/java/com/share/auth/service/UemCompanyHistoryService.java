package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.ReviewCompanyDTO;
import com.share.auth.model.vo.CompanyCheckQueryVO;

/**
 * @Author:chenxf
 * @Description: 企业历史记录服务层
 * @Date: 15:55 2020/10/29
 * @Param: 
 * @Return:
 *
 */
public interface UemCompanyHistoryService {
    /**
     * 分页查询企业历史审核记录表审核记录
     * @Author:chenxf
     * @Description: 分页查询企业历史审核记录表审核记录
     * @Date: 15:55 2020/10/29
     * @param companyCheckQueryVO : [companyCheckQueryVO] 条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.entity.UemCompany>
     *
     */
    Page<ReviewCompanyDTO> queryByPage(CompanyCheckQueryVO companyCheckQueryVO);

    /**
     * 企业资质审核操作接口
     * @Author:chenxf
     * @Description: 企业资质审核操作接口
     * @Date: 15:55 2020/10/29
     * @param uemCompany: [ReviewCompanyDTO]
     * @Return:void
     *
     */
    void reviewCompany(ReviewCompanyDTO uemCompany);
}
