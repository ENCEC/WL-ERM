package com.share.auth.service;

import com.share.auth.domain.UemProjectDTO;
import com.share.support.result.ResultHelper;

/**
 * @author tanjp
 * @Date 2022/7/28 16:35
 */
public interface UemProjectService {
    
    /**
     *新增项目
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/28
     */
    ResultHelper<?> saveUemProject(UemProjectDTO uemProjectDTO);
}
