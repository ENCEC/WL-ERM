package com.share.message.service;

import com.share.auth.domain.QueryApplicationDTO;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * @author tujx
 * @description 系统切换接口
 * @date 2020/11/19
 */
public interface SysApplicationService {

    /**
     * 获取系统列表
     *
     * @param
     * @return ResultHelper<List<QueryApplicationDTO>>
     * @throws
     * @author tujx
     */
    ResultHelper<List<QueryApplicationDTO>> getSystemList();

    /**
     * 切换选定的系统
     *
     * @param applicationCode 系统code
     * @return ResultHelper
     * @author tujx
     */
    ResultHelper<Object> updateApplication(String applicationCode);

    /**
     * 查询当前的系统编码
     *
     * @return String 系统编码
     * @author tujx
     */
    String getCurrentApplicationCode();
}
