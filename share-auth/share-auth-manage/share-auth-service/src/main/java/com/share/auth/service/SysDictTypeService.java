package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.PageDto;
import com.share.auth.domain.SysDictCodeDto;
import com.share.auth.domain.SysDictTypeDto;
import com.share.auth.domain.daoService.SysDictCodeDTO;
import com.share.auth.model.entity.SysDictCode;
import com.share.auth.model.entity.SysDictType;
import com.share.support.result.ResultHelper;

import java.util.List;
import java.util.Map;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
public interface SysDictTypeService {


    /**
     * 基础管理 数据字典 按条件分页查询
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @param pageDto        分页映射
     * @return Page<SysDictType>
     * @author xrp
     */
    Page<SysDictTypeDto> queryByPage(SysDictTypeDto sysDictTypeDto, PageDto pageDto);


    /**
     * 基础管理 数据字典 新增字典
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     */
    ResultHelper<Object> saveSysDictType(SysDictTypeDto sysDictTypeDto);

    /**
     * 基础管理 数据字典 修改字典
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     */
    ResultHelper<Object> updateSysDictType(SysDictTypeDto sysDictTypeDto);

    /**
     * 基础管理 数据字典 详情
     *
     * @param sysDictTypeId 数据字典表ID
     * @return List<SysDictType>
     * @author xrp
     */
    List<SysDictType> getSysDictType(String sysDictTypeId);


    /**
     * 基础管理 数据字典 启停
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> updateDictTypeStatus(SysDictTypeDto sysDictTypeDto);


    /**
     * 基础管理 数据字典 配置项分页查询
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @param pageDto        分页
     * @return Page<SysDictCodeDto>
     * @author xrp
     */
    Page<SysDictCodeDto> queryByDictTypeId(SysDictCodeDto sysDictCodeDto, PageDto pageDto);


    /**
     * 基础管理 数据字典 配置项新增
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> saveSysDictCode(SysDictCodeDto sysDictCodeDto);


    /**
     * 基础管理 数据字典 配置项者修改
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> updateSysDictCode(SysDictCodeDto sysDictCodeDto);

    /**
     * 基础管理 数据字典 配置项详情
     *
     * @param sysDictCodeId 数据字典配置项ID
     * @return List<SysDictCode>
     * @author xrp
     */
    List<SysDictCode> getSysDictCode(String sysDictCodeId);


    /**
     * 基础管理 数据字典 配置项 启停
     *
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> updateDictCodeStatus(SysDictCodeDto sysDictCodeDto);

    /**
     * @Author Linja
     * @param codeList
     * @return
     */
    Map<String,List<SysDictCodeDTO>> selectSysDictCodeMapToCodeList(List<String> codeList);

    /**
     * 查询数据字典
     * @param sysDictTypeDto
     * @return
     */
    ResultHelper<Page<SysDictTypeDto>> querySysDictType(SysDictTypeDto sysDictTypeDto);


}
