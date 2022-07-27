package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.querymodels.QSysPost;
import com.share.auth.model.querymodels.QSysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;
import com.share.auth.service.SysTechnicalTitleService;
import com.share.support.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @ClassName SysTechnicalTitleServiceImpl
 * @Description 岗位职称Service的实现类
 * @Author weiq
 * @Date 2022/7/25 15:29
 * @Version 1.0
 **/
@Service
@Slf4j
public class SysTechnicalTitleServiceImpl implements SysTechnicalTitleService {
    /**
     * 分页查询全部岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public Page<SysTechnicalTitleAndPostVO> queryByPageAll(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                .where(QSysTechnicalTitle.technicalTitleId.goe$(1L))
                .paging(sysTechnicalTitleAndPostVO.getCurrentPage(), sysTechnicalTitleAndPostVO.getPageSize())
                .mapperTo(SysTechnicalTitleAndPostVO.class)
                .execute();
        return pages;
    }

    /**
     *
     * 通过条件分页查询岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        if (!sysTechnicalTitleAndPostVO.getStatus().isEmpty() && !sysTechnicalTitleAndPostVO.getTechnicalName().isEmpty() && !sysTechnicalTitleAndPostVO.getPostName().isEmpty()) {
            Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                    .where(QSysTechnicalTitle.technicalName._like$_(sysTechnicalTitleAndPostVO.getTechnicalName()).and(QSysTechnicalTitle.sysPost.chain(QSysPost.postName).eq$(sysTechnicalTitleAndPostVO.getPostName())).and(QSysTechnicalTitle.status.eq$(sysTechnicalTitleAndPostVO.getStatus())))
                    .paging(sysTechnicalTitleAndPostVO.getCurrentPage(), sysTechnicalTitleAndPostVO.getPageSize())
                    .mapperTo(SysTechnicalTitleAndPostVO.class)
                    .execute();
            return pages;
        } else if (sysTechnicalTitleAndPostVO.getStatus().isEmpty() && sysTechnicalTitleAndPostVO.getTechnicalName().isEmpty() && sysTechnicalTitleAndPostVO.getPostName().isEmpty()) {
            Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                    .where(QSysTechnicalTitle.technicalTitleId.goe$(1L))
                    .paging(sysTechnicalTitleAndPostVO.getCurrentPage(), sysTechnicalTitleAndPostVO.getPageSize())
                    .mapperTo(SysTechnicalTitleAndPostVO.class)
                    .execute();
            return pages;
        } else {
            Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                    .where(QSysTechnicalTitle.technicalName._like$_(sysTechnicalTitleAndPostVO.getTechnicalName()).or(QSysTechnicalTitle.sysPost.chain(QSysPost.postName).eq$(sysTechnicalTitleAndPostVO.getPostName())).or(QSysTechnicalTitle.status.eq$(sysTechnicalTitleAndPostVO.getStatus())))
                    .paging(sysTechnicalTitleAndPostVO.getCurrentPage(), sysTechnicalTitleAndPostVO.getPageSize())
                    .mapperTo(SysTechnicalTitleAndPostVO.class)
                    .execute();
            return pages;
        }
    }

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public void saveSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
       SysPost sysPost = QSysPost.sysPost.selectOne(QSysPost.postId)
                .where(QSysPost.postName.eq$(sysTechnicalTitleAndPostVO.getPostName()))
                .execute();
        SysTechnicalTitle sysTechnicalTitle = new SysTechnicalTitle();
        sysTechnicalTitle.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysTechnicalTitle.setTechnicalName(sysTechnicalTitleAndPostVO.getTechnicalName());
        sysTechnicalTitle.setSeniority(sysTechnicalTitleAndPostVO.getSeniority());
        sysTechnicalTitle.setPostId(sysPost.getPostId());
        sysTechnicalTitle.setCreateBy("系统管理员");
        sysTechnicalTitle.setStatus("0");
        sysTechnicalTitle.setCreateTime(new DateTime());
        QSysTechnicalTitle.sysTechnicalTitle.save(sysTechnicalTitle);

    }

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public void updateSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        SysPost sysPost = QSysPost.sysPost.selectOne(QSysPost.postId)
                .where(QSysPost.postName.eq$(sysTechnicalTitleAndPostVO.getPostName()))
                .execute();
        SysTechnicalTitle sysTechnicalTitle = new SysTechnicalTitle();
        sysTechnicalTitle.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysTechnicalTitle.setTechnicalTitleId(sysTechnicalTitleAndPostVO.getTechnicalTitleId());
        sysTechnicalTitle.setTechnicalName(sysTechnicalTitleAndPostVO.getTechnicalName());
        sysTechnicalTitle.setSeniority(sysTechnicalTitleAndPostVO.getSeniority());
        sysTechnicalTitle.setPostId(sysPost.getPostId());
        sysTechnicalTitle.setCreateBy("系统管理员");
        sysTechnicalTitle.setStatus("0");
        QSysTechnicalTitle.sysTechnicalTitle.save(sysTechnicalTitle);
    }

    /**
     * 删除职称
     * @param technicalTitleId
     */
    @Override
    public void deleteSysTechnicalTitle(Long technicalTitleId) {
        QSysTechnicalTitle.sysTechnicalTitle.delete().where(QSysTechnicalTitle.technicalTitleId.eq$(technicalTitleId)).execute();

    }

    /**
     * 启动/禁用
     * @param sysTechnicalTitle
     */
    @Override
    public void updateStatus(SysTechnicalTitle sysTechnicalTitle) {
        int status = ("0".equals(sysTechnicalTitle.getStatus())) ? 1 : 0;
        QSysTechnicalTitle.sysTechnicalTitle.update(QSysTechnicalTitle.status)
                .where(QSysTechnicalTitle.technicalTitleId.eq$(sysTechnicalTitle.getTechnicalTitleId()))
                .execute(ImmutableMap.of("status", status));

    }
}
