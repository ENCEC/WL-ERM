package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysPostDTO;
import com.share.support.result.ResultHelper;

/**
 * @author tanjp
 * @Date 2022/7/26 9:27
 */
public interface SysPostService {

    /**
     * 新增岗位信息
     *
     * @param sysPostDTO 岗位信息封装类
     * @return Page<SysPostDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> saveSysPost(SysPostDTO sysPostDTO);


    /**
     * 查询岗位信息
     *
     * @param sysPostDTO 岗位信息封装类
     * @return Page<SysPostDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<Page<SysPostDTO>> querySysPost(SysPostDTO sysPostDTO);

    /**
     * 岗位详细
     *
     * @param sysPostId 岗位ID
     * @return SysPostDTO
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<SysPostDTO> getSysPost(Long sysPostId);

    /**
     * 启动禁止
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> sysPostStartStop(SysPostDTO sysPostDTO);

    /**
     *修改
     *
     * @param sysPostDTO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> updatePostStartStop(SysPostDTO sysPostDTO);

    /**
     *删除
     *
     * @param sysPostId 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> deletePostById(Long sysPostId);
}
