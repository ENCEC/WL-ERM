package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysPostDTO;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.querymodels.QSysPost;
import com.share.auth.service.SysPostService;
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
 * @author tanjp
 * @Date 2022/7/26 9:27
 */
@Service("SysPostService")
@Slf4j
public class SysPostServiceImpl implements SysPostService {

    /**
     * 新增岗位信息
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> saveSysPost(SysPostDTO sysPostDTO) {

        //岗位名
        List<SysPost> sysPostList = QSysPost.sysPost
                .select(QSysPost.sysPost.fieldContainer())
                .where(QSysPost.postName.eq$(sysPostDTO.getPostName()))
                .execute();
        if (CollectionUtils.isNotEmpty(sysPostList)) {
            return CommonResult.getSuccessResultData("该岗位名已注册过！");
        }

        SysPost sysPost = new SysPost();
        BeanUtils.copyProperties(sysPostDTO,sysPost);
        sysPost.setStatus("0");
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int row = QSysPost.sysPost.save(sysPost);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }


    /**
     * 查询岗位信息
     *
     * @param sysPostDTO 岗位信息封装类
     * @return Page<SysPostDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<Page<SysPostDTO>> querySysPost(SysPostDTO sysPostDTO) {
        //岗位名称
        String postName = sysPostDTO.getPostName();
        if (!StringUtils.isEmpty(postName)) {
            sysPostDTO.setPostName("%" + postName + "%");
        }
        //page:当前页     size:每页显示的条数
        int currentPage = (sysPostDTO.getCurrentPage() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : sysPostDTO.getCurrentPage();
        int pageSize = (sysPostDTO.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : sysPostDTO.getPageSize();

        Page<SysPostDTO> sysPostDTOPage = QSysPost.sysPost.select(QSysPost.sysPost.fieldContainer())
                .where(
                        QSysPost.postName.like(":postName")
                                .and(QSysPost.status.eq(":status"))
                                .and(QSysPost.postId.goe$(1L))
                ).paging(currentPage,pageSize)
                .sorting(QSysPost.createTime.desc())
                .mapperTo(SysPostDTO.class)
                .execute(sysPostDTO);

        return CommonResult.getSuccessResultData(sysPostDTOPage);
    }


    public SysPost getSystemPostById(Long sysPostId) {
        List<SysPost> sysPostList = QSysPost.sysPost
                .select(QSysPost.sysPost.fieldContainer())
                .where(QSysPost.postId.eq$(sysPostId))
                .execute();
        if (sysPostList.size() == 1) {
            return sysPostList.get(0);
        }else {
            return null;
        }
    }

    /**
     * 岗位详细
     *
     * @param sysPostId 岗位ID
     * @return SysPostDTO
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<SysPostDTO> getSysPost(Long sysPostId) {
        SysPost sysPost = this.getSystemPostById(sysPostId);
        if (Objects.isNull(sysPost)) {
            return CommonResult.getFaildResultData("用户不存在");
        } else {
            SysPostDTO sysPostDTO = new SysPostDTO();
            BeanUtils.copyProperties(sysPost, sysPostDTO);
            return CommonResult.getSuccessResultData(sysPostDTO);
        }
    }

    /**
     * 启动禁止
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> sysPostStartStop(SysPostDTO sysPostDTO) {
        //岗位表id
        Long sysPostId = sysPostDTO.getPostId();
        //(0禁用,1启)
        String status = sysPostDTO.getStatus();
        //检查岗位是否存在
        if (Objects.isNull(sysPostId)) {
            return CommonResult.getFaildResultData("岗位ID不能为空");
        }
        SysPost sysPost = this.getSystemPostById(sysPostId);
        if (Objects.isNull(sysPost)) {
            return CommonResult.getFaildResultData("岗位信息不存在");
        }
        sysPost.setPostId(sysPostId);
        sysPost.setStatus(status);
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateStatus = QSysPost.sysPost.selective(QSysPost.status).execute(sysPost);

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
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> updatePostStartStop(SysPostDTO sysPostDTO) {
        //根据主键查询岗位信息
        Long sysPostID = sysPostDTO.getPostId();
        if (Objects.isNull(sysPostID)) {
            return CommonResult.getFaildResultData("id不允许为空");
        }
        SysPost sysPost = this.getSystemPostById(sysPostID);
        if (Objects.isNull(sysPost)) {
            return CommonResult.getFaildResultData("不存在");
        }
        //更新信息
        BeanUtils.copyProperties(sysPostDTO, sysPost);
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int row = QSysPost.sysPost.save(sysPost);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("用户修改成功");
        } else {
            return CommonResult.getFaildResultData("用户修改失败");
        }
    }

    /**
     *删除
     *
     * @param sysPostId 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> deletePostById(Long sysPostId) {
        //获取用户
        if (Objects.isNull(sysPostId)) {
            return CommonResult.getFaildResultData("信息主键不能为空");
        }
        SysPost sysPost = this.getSystemPostById(sysPostId);
        if (Objects.isNull(sysPost)) {
            return CommonResult.getFaildResultData("岗位信息不存在");
        }
        //删除
        sysPost.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        int row = QSysPost.sysPost.deleteById(sysPostId);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("删除成功");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }
    }

}
