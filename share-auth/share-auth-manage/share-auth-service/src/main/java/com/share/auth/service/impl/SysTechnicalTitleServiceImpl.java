package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.share.auth.domain.SysPostDTO;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.querymodels.QSysPost;
import com.share.auth.model.querymodels.QSysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;
import com.share.auth.service.SysTechnicalTitleService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
     *
     * 查询岗位职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.createBy, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                .where(
                        QSysTechnicalTitle.technicalTitleId.goe$(1L).and(QSysTechnicalTitle.technicalName._like$_(sysTechnicalTitleAndPostVO.getTechnicalName())).and(QSysTechnicalTitle.sysPost.chain(QSysPost.postName).eq$(sysTechnicalTitleAndPostVO.getPostName())).and(QSysTechnicalTitle.status.eq$(sysTechnicalTitleAndPostVO.getStatus())))
                .paging(sysTechnicalTitleAndPostVO.getCurrentPage(), sysTechnicalTitleAndPostVO.getPageSize())
                .sorting(QSysTechnicalTitle.createTime.desc())
                .mapperTo(SysTechnicalTitleAndPostVO.class)
                .execute();

        return CommonResult.getSuccessResultData(pages);
    }

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public ResultHelper<?> saveSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.sysTechnicalTitle.fieldContainer())
                .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()))
                .execute();
        if (CollectionUtils.isNotEmpty(sysTechnicalTitles)) {
            return CommonResult.getFaildResultData("该岗位职称已经存在");
        }
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
        return CommonResult.getSuccessResultData("新增成功");
    }

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public ResultHelper<?> updateSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.sysTechnicalTitle.fieldContainer())
                .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()))
                .execute();
        if (CollectionUtils.isNotEmpty(sysTechnicalTitles)) {
            return CommonResult.getFaildResultData("该岗位职称已经存在");
        }
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
        return CommonResult.getSuccessResultData("更新成功");
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
     * @param sysTechnicalTitleAndPostVO
     */
    @Override
    public void updateStatus(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        QSysTechnicalTitle.sysTechnicalTitle.update(QSysTechnicalTitle.status)
                .where(QSysTechnicalTitle.technicalTitleId.eq$(sysTechnicalTitleAndPostVO.getTechnicalTitleId()))
                .execute(ImmutableMap.of("status", sysTechnicalTitleAndPostVO.getStatus()));

    }
}
