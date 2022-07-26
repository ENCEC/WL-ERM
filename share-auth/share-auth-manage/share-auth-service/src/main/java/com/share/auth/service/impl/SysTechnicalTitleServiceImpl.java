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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysTechnicalTitleServiceImpl
 * @Description TODO
 * @Author weiq
 * @Date 2022/7/25 15:29
 * @Version 1.0
 **/
@Service
@Slf4j
public class SysTechnicalTitleServiceImpl implements SysTechnicalTitleService {
    /**
     * 分页查询全部岗位职称
     * @param currentPage 第几页
     * @param pageSize  每页的数据条数
     * @return
     */
    @Override
    public Page<SysTechnicalTitleAndPostVO> queryByPageAll(Integer currentPage,Integer pageSize) {
        Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName, QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                .where(QSysTechnicalTitle.technicalTitleId.goe$(1L))
                .paging(currentPage,pageSize)
                .mapperTo(SysTechnicalTitleAndPostVO.class)
                .execute();
        return pages;
    }

    /**
     * 通过条件分页查询岗位职称
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(Integer currentPage, Integer pageSize,String name) {
        Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalName, QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                .where(QSysTechnicalTitle.technicalName._like$_(name).or(QSysTechnicalTitle.sysPost.chain(QSysPost.postName).eq$(name)))
                .paging(currentPage,pageSize)
                .mapperTo(SysTechnicalTitleAndPostVO.class)
                .execute();
        return pages;

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
     * @param sysTechnicalTitle
     */
    @Override
    public void updateSysTechnicalTitle(SysTechnicalTitle sysTechnicalTitle) {
//        SysPost sysPost = QSysPost.sysPost.selectOne(QSysPost.postId)
//                .where(QSysPost.postName.eq$(sysTechnicalTitleAndPostVO.getPostName()))
//                .execute();
//        SysTechnicalTitle sysTechnicalTitle1 = QSysTechnicalTitle.sysTechnicalTitle.selectOne(QSysTechnicalTitle.technicalTitleId)
//                .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()))
//                .execute();
//        SysTechnicalTitle sysTechnicalTitle = new SysTechnicalTitle();
//        sysTechnicalTitle.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//        sysTechnicalTitle.setTechnicalName(sysTechnicalTitleAndPostVO.getTechnicalName());
//        sysTechnicalTitle.setSeniority(sysTechnicalTitleAndPostVO.getSeniority());
//        sysTechnicalTitle.setPostId(sysPost.getPostId());
//        sysTechnicalTitle.setCreateBy("系统管理员");
//        sysTechnicalTitle.setStatus("0");
//        sysTechnicalTitle.setCreateTime(new DateTime());
//        int updateCount = QSysTechnicalTitle.sysTechnicalTitle.save(sysTechnicalTitle);
//        return updateCount;
//        QSysTechnicalTitle.sysTechnicalTitle.update(QSysTechnicalTitle.technicalName,QSysTechnicalTitle.seniority,QSysTechnicalTitle.postId)
//                .where(QSysTechnicalTitle.technicalTitleId.eq$(sysTechnicalTitleAndPostVO.getTechnicalTitleId()))
//                .execute(ImmutableMap.of("technicalName",sysTechnicalTitleAndPostVO.getTechnicalName(),"seniority",));
        QSysTechnicalTitle.sysTechnicalTitle
                .selective(QSysTechnicalTitle.technicalName, QSysTechnicalTitle.seniority,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"))
                .update(sysTechnicalTitle);
    }

    /**
     * 删除职称
     * @param technicalName
     */
    @Override
    public void deleteSysTechnicalTitle(String technicalName) {
        QSysTechnicalTitle.sysTechnicalTitle.delete().where(QSysTechnicalTitle.technicalName.eq$(technicalName)).execute();

    }

    /**
     * 禁用
     * @param sysTechnicalTitle
     */
    @Override
    public void updateStatus(SysTechnicalTitle sysTechnicalTitle) {
        if (sysTechnicalTitle.getStatus().equals("0")) {
            QSysTechnicalTitle.sysTechnicalTitle.update(QSysTechnicalTitle.status)
                    .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitle.getTechnicalName()))
                    .execute(ImmutableMap.of("status","1"));
        } else {
            QSysTechnicalTitle.sysTechnicalTitle.update(QSysTechnicalTitle.status)
                    .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitle.getTechnicalName()))
                    .execute(ImmutableMap.of("status", "0"));
        }


    }
}
