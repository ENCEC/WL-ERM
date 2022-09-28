package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysTagDTO;
import com.share.support.result.ResultHelper;

/**
 * @author tanjp
 * @Date 2022/7/26 9:27
 */
public interface SysTagService {

    /**
     * 新增标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return Page<SysTagDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> saveSysTag(SysTagDTO sysTagDTO);


    /**
     * 查询标签信息
     *
     * @param sysTagDTO 标签信息封装类
     * @return Page<SysTagDTO>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<Page<SysTagDTO>> querySysTag(SysTagDTO sysTagDTO);

    /**
     * 标签详细
     *
     * @param sysTagId 标签ID
     * @return SysTagDTO
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<SysTagDTO> getSysTag(Long sysTagId);

    /**
     * 启动禁止
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> sysTagStartStop(SysTagDTO sysTagDTO);

    /**
     *修改
     *
     * @param sysTagDTO 标签信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> updateSysTag(SysTagDTO sysTagDTO);

    /**
     *删除
     *
     * @param sysTagId 标签信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> deleteTagById(Long sysTagId);
}
