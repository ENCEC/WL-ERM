package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
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
import java.util.Objects;


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
        Page<SysTechnicalTitleAndPostVO> pages = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.technicalTitleId,QSysTechnicalTitle.technicalName,QSysTechnicalTitle.postId,QSysTechnicalTitle.sysPost.chain(QSysPost.postName).as("postName"), QSysTechnicalTitle.seniority, QSysTechnicalTitle.creatorName, QSysTechnicalTitle.createTime, QSysTechnicalTitle.status)
                .where(
                        QSysTechnicalTitle.technicalTitleId.goe$(1L).and(QSysTechnicalTitle.technicalName._like$_(sysTechnicalTitleAndPostVO.getTechnicalName())).and(QSysTechnicalTitle.postId.eq$(sysTechnicalTitleAndPostVO.getPostId())).and(QSysTechnicalTitle.status.eq$(sysTechnicalTitleAndPostVO.getStatus())))
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
        if (StrUtil.isEmpty(sysTechnicalTitleAndPostVO.getTechnicalName())) {
            return CommonResult.getFaildResultData("职称名称不能为空");
        }
        if (StrUtil.isEmpty(sysTechnicalTitleAndPostVO.getSeniority())) {
            return CommonResult.getFaildResultData("工作年限不能为空");
        }
        if (Objects.isNull(sysTechnicalTitleAndPostVO.getPostId())) {
            return CommonResult.getFaildResultData("岗位名称不能为空");
        }
        List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle.sysTechnicalTitle.select(QSysTechnicalTitle.sysTechnicalTitle.fieldContainer())
                .where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()).and(QSysTechnicalTitle.postId.eq$(sysTechnicalTitleAndPostVO.getPostId())))
                .execute();
        if (CollectionUtils.isNotEmpty(sysTechnicalTitles)) {
            return CommonResult.getFaildResultData("该岗位职称已经存在");
        }
        SysTechnicalTitle sysTechnicalTitle = new SysTechnicalTitle();
        sysTechnicalTitle.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysTechnicalTitle.setTechnicalName(sysTechnicalTitleAndPostVO.getTechnicalName());
        sysTechnicalTitle.setSeniority(sysTechnicalTitleAndPostVO.getSeniority());
        sysTechnicalTitle.setPostId(sysTechnicalTitleAndPostVO.getPostId());
        sysTechnicalTitle.setStatus("0");
        int save = QSysTechnicalTitle.sysTechnicalTitle.save(sysTechnicalTitle);
        if (save > 0) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

    /**
     * 编辑职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @Override
    public ResultHelper<?> updateSysTechnicalTitle(SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO) {
        if (StrUtil.isEmpty(sysTechnicalTitleAndPostVO.getTechnicalName())) {
            return CommonResult.getFaildResultData("职称名称不能为空");
        }
        if (StrUtil.isEmpty(sysTechnicalTitleAndPostVO.getSeniority())) {
            return CommonResult.getFaildResultData("工作年限不能为空");
        }
        if (Objects.isNull(sysTechnicalTitleAndPostVO.getPostId())) {
            return CommonResult.getFaildResultData("岗位名称不能为空");
        }
        SysTechnicalTitle sysTechnicalTitle = QSysTechnicalTitle.sysTechnicalTitle.selectOne(QSysTechnicalTitle.sysTechnicalTitle.fieldContainer())
                .where(QSysTechnicalTitle.technicalTitleId.eq$(sysTechnicalTitleAndPostVO.getTechnicalTitleId()))
                .execute();
            if (sysTechnicalTitle.getPostId().equals(sysTechnicalTitleAndPostVO.getPostId()) == false) {
                List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle.sysTechnicalTitle.select().where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()).and(QSysTechnicalTitle.postId.eq$(sysTechnicalTitleAndPostVO.getPostId()))).execute();
                if (CollectionUtils.isNotEmpty(sysTechnicalTitles)) {
                    return CommonResult.getFaildResultData("已存在");
                   }
            }
            if (sysTechnicalTitle.getTechnicalName().equals(sysTechnicalTitleAndPostVO.getTechnicalName()) == false) {
                List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle.sysTechnicalTitle.select().where(QSysTechnicalTitle.technicalName.eq$(sysTechnicalTitleAndPostVO.getTechnicalName()).and(QSysTechnicalTitle.postId.eq$(sysTechnicalTitleAndPostVO.getPostId()))).execute();
                if (CollectionUtils.isNotEmpty(sysTechnicalTitles)) {
                    return CommonResult.getFaildResultData("已存在");
                }
            }

        sysTechnicalTitle.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysTechnicalTitle.setTechnicalName(sysTechnicalTitleAndPostVO.getTechnicalName());
        sysTechnicalTitle.setSeniority(sysTechnicalTitleAndPostVO.getSeniority());
        sysTechnicalTitle.setPostId(sysTechnicalTitleAndPostVO.getPostId());
        int save = QSysTechnicalTitle.sysTechnicalTitle.save(sysTechnicalTitle);
        if (save > 0) {
            return CommonResult.getSuccessResultData("更新成功");
        } else {
            return CommonResult.getFaildResultData("更新失败");
        }
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
