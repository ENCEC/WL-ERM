package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysTagDTO;
import com.share.auth.model.entity.SysTag;
import com.share.auth.model.querymodels.QSysTag;
import com.share.auth.service.SysTagService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author cec
 * @Date 2022/7/26 9:27
 */
@Service("SysTagService")
@Slf4j
public class SysTagServiceImpl implements SysTagService {

    /**
     * 新增标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> saveSysTag(SysTagDTO sysTagDTO) {

        //标签名
        List<SysTag> sysTagList = QSysTag.sysTag
                .select(QSysTag.sysTag.fieldContainer())
                .where(QSysTag.tagName.eq$(sysTagDTO.getTagName()).and(QSysTag.status.eq$(sysTagDTO.getStatus())))
                .execute();
        if (CollectionUtils.isNotEmpty(sysTagList)) {
            return CommonResult.getFaildResultData("该标签名已注册过！");
        }

        SysTag sysTag = new SysTag();
        BeanUtils.copyProperties(sysTagDTO,sysTag);
        sysTag.setStatus(true);
        sysTag.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int row = QSysTag.sysTag.save(sysTag);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }


    /**
     * 查询标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return Page<SysTagDTO>
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<Page<SysTagDTO>> querySysTag(SysTagDTO sysTagDTO) {
        //标签名称
        String tagName = sysTagDTO.getTagName();
        if (!StringUtils.isEmpty(tagName)) {
            sysTagDTO.setTagName("%" + tagName + "%");
        }
        //page:当前页     size:每页显示的条数
        int currentPage = (sysTagDTO.getCurrentPage() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : sysTagDTO.getCurrentPage();
        int pageSize = (sysTagDTO.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : sysTagDTO.getPageSize();

        Page<SysTagDTO> sysTagDTOPage = QSysTag.sysTag.select(QSysTag.sysTag.fieldContainer())
                .where(
                        QSysTag.tagName.like(":tagName")
                                .and(QSysTag.status.eq(":status"))
                                .and(QSysTag.isDeleted.eq$(false))
                ).paging(currentPage,pageSize)
                .sorting(QSysTag.createTime.desc())
                .mapperTo(SysTagDTO.class)
                .execute(sysTagDTO);

        return CommonResult.getSuccessResultData(sysTagDTOPage);
    }


    public SysTag getSystemTagById(Long sysTagId) {
        List<SysTag> sysTagList = QSysTag.sysTag
                .select(QSysTag.sysTag.fieldContainer())
                .where(QSysTag.sysTagId.eq$(sysTagId))
                .execute();
        if (sysTagList.size() == 1) {
            return sysTagList.get(0);
        }else {
            return null;
        }
    }

    /**
     * 标签详细
     *
     * @param sysTagId 标签ID
     * @return SysTagDTO
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<SysTagDTO> getSysTag(Long sysTagId) {
        SysTag sysTag = this.getSystemTagById(sysTagId);
        if (Objects.isNull(sysTag)) {
            return CommonResult.getFaildResultData("标签不存在");
        } else {
            SysTagDTO sysTagDTO = new SysTagDTO();
            BeanUtils.copyProperties(sysTag, sysTagDTO);
            return CommonResult.getSuccessResultData(sysTagDTO);
        }
    }

    /**
     * 启动禁止
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> sysTagStartStop(SysTagDTO sysTagDTO) {
        //标签表id
        Long sysTagId = sysTagDTO.getSysTagId();

        Boolean status = sysTagDTO.getStatus();
        //检查标签是否存在
        if (Objects.isNull(sysTagId)) {
            return CommonResult.getFaildResultData("标签ID不能为空");
        }
        SysTag sysTag = this.getSystemTagById(sysTagId);
        if (Objects.isNull(sysTag)) {
            return CommonResult.getFaildResultData("标签信息不存在");
        }
        sysTag.setSysTagId(sysTagId);
        sysTag.setStatus(status);
        sysTag.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateStatus = QSysTag.sysTag.selective(QSysTag.status).execute(sysTag);

        //判断是否成功
        if (updateStatus > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }
    }

    /**
     *修改
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> updateSysTag(SysTagDTO sysTagDTO) {
        //根据主键查询标签信息
        Long sysTagID = sysTagDTO.getSysTagId();
        if (Objects.isNull(sysTagID)) {
            return CommonResult.getFaildResultData("id不允许为空");
        }
        SysTag sysTag = this.getSystemTagById(sysTagID);
        if (Objects.isNull(sysTag)) {
            return CommonResult.getFaildResultData("不存在");
        }
        //判断标签名称是否改变
        if (sysTag.getTagName().equals(sysTagDTO.getTagName()) == false) {
            List<SysTag> sysTags = QSysTag.sysTag.select().where(QSysTag.tagName.eq$(sysTagDTO.getTagName())).execute();
            if (CollectionUtils.isNotEmpty(sysTags)) {
                return CommonResult.getFaildResultData("该标签已存在");
            }
        }
        //更新信息
        BeanUtils.copyProperties(sysTagDTO, sysTag);
        sysTag.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int row = QSysTag.sysTag.save(sysTag);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("标签修改成功");
        } else {
            return CommonResult.getFaildResultData("标签修改失败");
        }
    }

    /**
     *删除
     *
     * @param sysTagId 标签信息封装类
     * @return ResultHelper<?>
     * @author cec
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> deleteTagById(Long sysTagId) {
        //获取用户
        if (Objects.isNull(sysTagId)) {
            return CommonResult.getFaildResultData("主键不能为空");
        }
        SysTag sysTag = this.getSystemTagById(sysTagId);
        if (Objects.isNull(sysTag)) {
            return CommonResult.getFaildResultData("标签信息不存在");
        }
        //删除
        sysTag.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        int row = QSysTag.sysTag.deleteById(sysTagId);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("删除成功");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }
    }

}
