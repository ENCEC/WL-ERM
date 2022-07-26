package com.share.auth.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;


/**
 * @ClassName SysTechnicalTitleService
 * @Description TODO
 * @Author weiq
 * @Date 2022/7/25 15:28
 * @Version 1.0
 **/
public interface SysTechnicalTitleService {
    /**
     * 分页查询全部岗位职称
     * @param currentPage 第几页
     * @param pageSize  每页的数据条数
     * @return
     */
    Page<SysTechnicalTitleAndPostVO> queryByPageAll(Integer currentPage,Integer pageSize);

    /**
     * 通过条件分页查询岗位职称
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(Integer currentPage,Integer pageSize,String name);

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    void saveSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 编辑职称
     * @param sysTechnicalTitle
     */
    void updateSysTechnicalTitle(SysTechnicalTitle sysTechnicalTitle);

    /**
     * 删除职称
     * @param technicalName
     */
    void deleteSysTechnicalTitle(String technicalName);

    /**
     * 禁用
     * @param sysTechnicalTitle
     */
    void updateStatus(SysTechnicalTitle sysTechnicalTitle);
}
