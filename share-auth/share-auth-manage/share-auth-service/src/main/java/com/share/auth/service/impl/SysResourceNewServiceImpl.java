package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysDictCodeVO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.SysResource;
import com.share.auth.model.querymodels.QSysResource;
import com.share.auth.model.vo.SysResourceVO;
import com.share.auth.service.SysResourceNewService;
import com.share.support.result.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description应用接口实现类
 * @Author: zhuyp
 * @Date: 2021/12/7 17:09
 */
@Service
public class SysResourceNewServiceImpl implements SysResourceNewService {
    @Override
    public Page<SysResourceVO> selectSysResourcePage(SysResourceDTO sysResource) {
        Page<SysResourceVO> sysResourceVOPage = QSysResource.sysResource
                .select()
                .where(QSysResource.sysApplicationId.eq$(sysResource.getSysApplicationId())
                        .and(QSysResource.resourceTitle._like$_(sysResource.getResourceTitle()))
                        .and(QSysResource.isValid.eq$(sysResource.getIsValid()))
                        .and(QSysResource.sysApplicationId.notNull()))
                        .paging(Objects.isNull(sysResource.getCurrentPage()) ? CodeFinal.CURRENT_PAGE_DEFAULT : sysResource.getCurrentPage(),
                                Objects.isNull(sysResource.getPageSize()) ? CodeFinal.PAGE_SIZE_DEFAULT : sysResource.getPageSize())
                .mapperTo(SysResourceVO.class)
                .execute();
        return sysResourceVOPage;
    }

    @Override
    public SysResourceVO selectSysResourceById(Long sysResourceId) {
        SysResourceVO sysResourceVO = QSysResource.sysResource.selectOne()
                .mapperTo(SysResourceVO.class).byId(sysResourceId);
        if (sysResourceVO.getResourceType() == 2) {
            SysResourceVO parentSysResourceVo = QSysResource.sysResource
                    .selectOne(QSysResource.sysResourceId, QSysResource.resourcePid)
                    .mapperTo(SysResourceVO.class)
                    .byId(sysResourceVO.getResourcePid());
            sysResourceVO.setResourcePid(parentSysResourceVo.getResourcePid());
            sysResourceVO.setPageResourcePid(parentSysResourceVo.getSysResourceId());
        }
        return sysResourceVO;
    }

    @Override
    public List<SysResourceVO> selectSysResourceByResourcePid(Long resourcePid) {
        List<SysResourceVO> sysResourceVOList = QSysResource.sysResource.select()
                .where(QSysResource.resourcePid.eq$(resourcePid))
                .mapperTo(SysResourceVO.class)
                .execute();
        return sysResourceVOList;
    }

    @Override
    public Integer saveOrUpdate(SysResourceDTO sysResourceDto) {
        SysResource sysResource = new SysResource();
        BeanUtils.copyProperties(sysResourceDto,sysResource);
        if(Objects.isNull(sysResource.getSysResourceId())){
            sysResource.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        }else {
            sysResource.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        }
        sysResource.setIsValid(true);
        sysResource.setInvalidTime(new Date());
        if (sysResourceDto.getResourceType() == 2) {
            if (Objects.nonNull(sysResourceDto.getPageResourcePid())) {
                sysResource.setResourcePid(sysResourceDto.getPageResourcePid());
            } else {
                CommonResult.getFaildResultData("按钮资源必须指定父级页面");
            }
        }
        int updateCount = QSysResource.sysResource.save(sysResource);
        return updateCount;
    }

    @Override
    public Integer updateResource(SysResourceDTO sysResourceDto) {
        SysResource sysResource = QSysResource.sysResource.selectOne().byId(sysResourceDto.getSysResourceId());
        int updateCount = 0;
        if(Objects.nonNull(sysResource)){
            sysResource.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            sysResource.setIsValid(sysResourceDto.getIsValid());
            sysResource.setInvalidTime(new Date());
            if (sysResourceDto.getResourceType() == 2) {
                if (Objects.nonNull(sysResourceDto.getPageResourcePid())) {
                    sysResource.setResourcePid(sysResourceDto.getPageResourcePid());
                } else {
                    CommonResult.getFaildResultData("按钮资源必须指定父级页面");
                }
            }
            updateCount =  QSysResource.sysResource.save(sysResource);
        }
        return updateCount;
    }

    @Override
    public Map<String, List<SysDictCodeVO>> selectSysParentsResourceList() {
        List<SysDictCodeVO> sysDictCodeDTOList = DSContext.customization("CZT_selectSysParentResource_auth").select()
                .mapperTo(SysDictCodeVO.class)
                .execute();
        Map<String, List<SysDictCodeVO>> sysParentsResourceListMap =sysDictCodeDTOList.stream().collect((Collectors.groupingBy(SysDictCodeVO::getDictTypeCode)));
        return sysParentsResourceListMap;
    }

    @Override
    public Map<String, List<SysDictCodeVO>> selectSysApplicationList() {

        List<SysDictCodeVO> sysDictCodeDTOList = DSContext.customization("CZT_selectSysApplication_auth").select()
                .mapperTo(SysDictCodeVO.class)
                .execute();
        Map<String, List<SysDictCodeVO>> sysParentsResourceListMap = new HashMap<>(16);
        sysParentsResourceListMap.put("SYS_APPLICATION", sysDictCodeDTOList);
        return sysParentsResourceListMap;
    }

    @Override
    public Map<String, SysDictCodeVO> selectSysResourceList(Long sysResourceId) {
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("sysResourceId",sysResourceId);
        List<SysDictCodeVO> sysDictCodeDTOList = DSContext.customization("CZT_selectsysResource_auth").select()
                .mapperTo(SysDictCodeVO.class)
                .execute(paramMap);
        Map<String,SysDictCodeVO> sysResourceListMap = new HashMap<>(16);
        if(!CollectionUtils.isEmpty(sysDictCodeDTOList)){
            sysResourceListMap.put("SYS_RESOURCE", sysDictCodeDTOList.get(0));
        }
        return sysResourceListMap;
    }

    @Override
    public Boolean uniqueValidate(SysResourceDTO sysResource) {
        List<SysResource> sysResources = QSysResource.sysResource.select()
                .where(QSysResource.sysApplicationId.eq$(sysResource.getSysApplicationId())
                .and(QSysResource.resourceTitle.eq$(sysResource.getResourceTitle())))
                .execute();
        if(Objects.isNull(sysResource.getSysApplicationId())){
            if(sysResources.size()>0){
                return true;
            }
        }else{
            for (SysResource resource : sysResources) {
                if(!resource.getSysResourceId().equals(sysResource.getSysResourceId())){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Boolean forbiddenValidate(Long resourceId) {
        SysResource sysResource = QSysResource.sysResource.selectOne().byId(resourceId);
        //pid为空，则为父菜单
        if(Objects.isNull(sysResource.getResourcePid())){
            List<SysResource> sysResourceList = QSysResource.sysResource.select()
                    .where(QSysResource.resourcePid.eq$(resourceId)).execute();
            //如果子菜单存在启用状态的子菜单，不能禁用
            for (SysResource resource : sysResourceList) {
                if(resource.getIsValid()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean openValidate(Long resourceId) {
        SysResource sysResource = QSysResource.sysResource.selectOne().byId(resourceId);
        //pid不为空，则为子菜单
        if(Objects.nonNull(sysResource.getResourcePid())){
            SysResource sysResource1 = QSysResource.sysResource.selectOne().byId(sysResource.getResourcePid());
            //果其父菜单为禁用状态，不能启用
            if(!sysResource1.getIsValid()){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean existSonResource(Long pid) {
        List<SysResource> sysResources = QSysResource.sysResource.select()
                .where(QSysResource.resourcePid.eq$(pid)).execute();
        if(sysResources.size()>0){
            return true;
        }else{
            return false;
        }
    }
}
