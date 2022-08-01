package com.share.auth.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysTechnicalTitleAndPostVO;
import com.share.support.result.ResultHelper;


/**
 * @ClassName SysTechnicalTitleService
 * @Description 岗位职称Service层
 * @Author weiq
 * @Date 2022/7/25 15:28
 * @Version 1.0
 **/
public interface SysTechnicalTitleService {
    /**
     * 查询岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    ResultHelper<?> saveSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    ResultHelper<?> updateSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 删除职称
     * @param technicalTitleId
     */
    void deleteSysTechnicalTitle(Long technicalTitleId);

    /**
     * 启动/禁用
     * @param sysTechnicalTitleAndPostVO
     */
    void updateStatus(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);
}
