package com.share.auth.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;


/**
 * @ClassName SysTechnicalTitleService
 * @Description 岗位职称Service层
 * @Author weiq
 * @Date 2022/7/25 15:28
 * @Version 1.0
 **/
public interface SysTechnicalTitleService {
    /**
     * 分页查询全部岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    Page<SysTechnicalTitleAndPostVO> queryByPageAll(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 通过条件分页查询岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    void saveSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    void updateSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 删除职称
     * @param technicalTitleId
     */
    void deleteSysTechnicalTitle(Long technicalTitleId);

    /**
     * 启动/禁用
     * @param sysTechnicalTitle
     */
    void updateStatus(SysTechnicalTitle sysTechnicalTitle);
}
