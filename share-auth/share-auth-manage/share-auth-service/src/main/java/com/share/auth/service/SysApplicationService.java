package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.QueryApplicationDTO;
import com.share.auth.domain.SysApplicationDto;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.UemUser;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * @Author:chenxf
 * @Description: 应用服务层
 * @Date: 14:55 2020/11/3
 * @Param:
 * @Return:
 *
 */
public interface SysApplicationService {
    /**
     *  查询应用接口
     * @Author:chenxf
     * @Description: 查询应用接口
     * @Date: 14:55 2020/11/3
     * @param opType: [opType]
     * @return :java.util.List<com.share.auth.domain.QueryApplicationDTO>
     *
     */
    List<QueryApplicationDTO> queryApplicationForOpType(String opType);

    /**
     * 应用管理
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return Page<SysApplication>
     * @author xrp
     */
    Page<SysApplicationDto> querySysApplication(SysApplicationDto sysApplicationDto);

    /**
     * 保存应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    ResultHelper<Object> saveSysApplication(SysApplicationDto sysApplicationDto);
    /**
     * 更新应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    ResultHelper<Object> updateSysApplication(SysApplicationDto sysApplicationDto);

    /**应用管理  应用详情
     * @param sysApplicationId 应用表的ID
     * @return
     * @author xrp
     * */
    List<SysApplication> getSysApplication(String sysApplicationId);

    /**
     * 权限分配应用下拉框查询接口
     *
     * @return :com.share.support.result.ResultHelper
     * @Author:chenxf
     * @Description: 权限分配应用下拉框查询接口
     * @Date: 18:58 2021/1/30
     * @Param: []
     */
    ResultHelper<List<SysApplication>> queryApplicationForCompanyRole();

    /**
     * 权限分配应用下拉框查询接口-用户管理
     *
     * @return :com.share.support.result.ResultHelper
     * @Author: cjh
     * @Description: 权限分配应用下拉框查询接口-用户管理
     * @Date: 18:58 2021/11/30
     * @Param: []
     */
    ResultHelper<List<SysApplication>> queryApplicationForCompanyRoleByUser(Long uemUserId);

    /**
     * 根据clientId查询应用
     * @param clientId 应用代码
     * @return 应用
     */
    ResultHelper<SysApplication> getSysApplicationByClientId(String clientId);

}
