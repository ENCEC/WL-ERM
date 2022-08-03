package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.Lists;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.entity.SysResource;
import com.share.auth.model.entity.SysRole;
import com.share.auth.model.entity.SysRoleResource;
import com.share.auth.model.querymodels.QSysResource;
import com.share.auth.model.querymodels.QSysRole;
import com.share.auth.model.querymodels.QSysRoleResource;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.model.workflow.RoleVO;
import com.share.auth.service.SysRoleService;
import com.share.auth.user.DefaultUserService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chenxf
 * @date 2020-11-16 14:25
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {

    /**
     * 应用id(数据服务查询占位符)
     */
    private static final String SYS_APPLICATION_ID_PLACEHOLDER = ":sysApplicationId";
    /**
     * 是否禁用(数据服务查询占位符)
     */
    private static final String IS_VALID_PLACEHOLDER = ":isValid";
    /**
     * 角色名称(数据服务查询占位符)
     */
    private static final String ROLE_NAME_PLACEHOLDER = ":roleName";

    @Value("${sso.applicationId.supply-chain}")
    private Long applicationIdSupplyChain;

    @Autowired
    private DefaultUserService userService;

    /**
     * @Author:chenxf
     * @Description: 分页查询应用的角色
     * @Date: 14:26 2020/11/16
     * @Param: [sysRoleQueryVo]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.entity.SysRole>
     */
    @Override
    public Page<SysRoleDTO> queryByPage(SysRoleQueryVO sysRoleQueryVo) {
        if (StringUtils.isNotEmpty(sysRoleQueryVo.getRoleName())) {
            sysRoleQueryVo.setRoleName("%" + sysRoleQueryVo.getRoleName() + "%");
        }
        Page<SysRoleDTO> sysRolePage;
        if (StringUtils.isNotEmpty(sysRoleQueryVo.getRoleName())) {
            List<SysRole> sysRoleList = QSysRole.sysRole.select(
                    QSysRole.sysRole.fieldContainer()
            ).where(
                    QSysRole.sysApplicationId.eq(SYS_APPLICATION_ID_PLACEHOLDER)
                            .and(QSysRole.roleName.like(ROLE_NAME_PLACEHOLDER)
                                    .and(QSysRole.isValid.eq(IS_VALID_PLACEHOLDER)))

            ).execute(sysRoleQueryVo);
            Set<Long> sysRoleIdSet = new HashSet<>();
            for (SysRole sysRole : sysRoleList) {
                if (Objects.nonNull(sysRole.getTopRoleId()) && Objects.nonNull(sysRole.getRolePid())) {
                    sysRoleIdSet.add(sysRole.getTopRoleId());
                } else {
                    sysRoleIdSet.add(sysRole.getSysRoleId());
                }
            }
            if (CollectionUtils.isNotEmpty(sysRoleIdSet)) {
                sysRolePage = QSysRole.sysRole.select(
                                QSysRole.sysRole.fieldContainer()
                        ).where(
                                QSysRole.sysRoleId.in$(sysRoleIdSet)
                                        .and(QSysRole.isValid.eq(IS_VALID_PLACEHOLDER))
                        ).paging(sysRoleQueryVo.getCurrentPage(), sysRoleQueryVo.getPageSize())
                        .mapperTo(SysRoleDTO.class)
                        .execute(sysRoleQueryVo);
            } else {
                sysRolePage = new Page<>();
            }
        } else {
            sysRolePage = QSysRole.sysRole.select(
                            QSysRole.sysRole.fieldContainer()
                    ).where(
                            QSysRole.sysApplicationId.eq(SYS_APPLICATION_ID_PLACEHOLDER)
                                    .and(QSysRole.roleName.like(ROLE_NAME_PLACEHOLDER))
                                    .and(QSysRole.isValid.eq(IS_VALID_PLACEHOLDER))
                                    .and(QSysRole.rolePid.isNull())
                    ).paging(sysRoleQueryVo.getCurrentPage(), sysRoleQueryVo.getPageSize())
                    .mapperTo(SysRoleDTO.class)
                    .execute(sysRoleQueryVo);
        }
        for (SysRoleDTO sysRoleDTO : sysRolePage.getRecords()) {
            sysRoleQueryVo.setPid(sysRoleDTO.getSysRoleId());
            dealWithSysRole(sysRoleDTO, sysRoleQueryVo);
        }
        return sysRolePage;
    }


    /**
     * @Author:chenxf
     * @Description: 新增更新保存角色接口
     * @Date: 10:29 2020/11/17
     * @Param: [sysRoleDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
 /*   @Override
    public ResultHelper<Object> saveSysRole(SysRoleDTO sysRoleDTO) {
        log.info("平台客服新增角色，置空角色id");
        sysRoleDTO.setSysRoleId(null);
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        // 新增
        sysRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        if (Objects.nonNull(sysRole.getRolePid())) {
            SysRole pRole = QSysRole.sysRole.selectOne().byId(sysRole.getRolePid());
            sysRole.setTopRoleId(pRole.getTopRoleId() == null ? pRole.getSysRoleId() : pRole.getTopRoleId());
        }
        // 保存角色
        log.info("平台客服新增角色信息：{}", sysRole);
        QSysRole.sysRole.save(sysRole);
        // 分配的资源集合
        List<SysRoleResource> sysRoleResourceList = Lists.newArrayList();
        SysRoleResource sysRoleResource;
        for (Long sysResourceId : sysRoleDTO.getSysResourceIdList()) {
            sysRoleResource = new SysRoleResource();
            sysRoleResource.setSysRoleId(sysRole.getSysRoleId());
            sysRoleResource.setSysResourceId(sysResourceId);
            sysRoleResource.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            sysRoleResourceList.add(sysRoleResource);
        }
        // 保存给该角色分配的资源
        log.info("保存角色的菜单资源");
        QSysRoleResource.sysRoleResource.save(sysRoleResourceList);
        return CommonResult.getSuccessResultData("保存成功!");
    }*/

    /**
     * @Author:chenxf
     * @Description: 新增更新保存角色接口
     * @Date: 10:29 2020/11/17
     * @Param: [sysRoleDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    // @Override
    // public ResultHelper<Object> updateSysRole(SysRoleDTO sysRoleDTO) {
        /*log.info("平台客服修改角色信息");
        if (Objects.isNull(sysRoleDTO.getSysRoleId())) {
            log.error("修改角色，传入角色id不能为空");
            return CommonResult.getFaildResultData("修改失败，角色id不能为空!");
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        // 更新
        sysRole.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        // 先删掉之前分配的菜单资源
        List<SysRoleResource> sysRoleResourceList = QSysRoleResource.sysRoleResource.select().where(
                QSysRoleResource.sysRoleId.eq$(sysRoleDTO.getSysRoleId())
                        .and(QSysRoleResource.sysRoleResource.chain(
                                QSysResource.resourceType).eq$(1).or(QSysResource.resourceType.eq$(2)
                        ))
        ).execute();
        if (CollectionUtils.isNotEmpty(sysRoleResourceList)) {
            log.info("删除角色的菜单资源");
            QSysRoleResource.sysRoleResource.delete(sysRoleResourceList);
        }

        if (Objects.nonNull(sysRole.getRolePid())) {
            SysRole pRole = QSysRole.sysRole.selectOne().byId(sysRole.getRolePid());
            sysRole.setTopRoleId(pRole.getTopRoleId() == null ? pRole.getSysRoleId() : pRole.getTopRoleId());
        }
        // 修改角色
        log.info("修改的角色信息：{}", sysRole);
        QSysRole.sysRole.save(sysRole);
        // 分配的资源集合
        sysRoleResourceList = Lists.newArrayList();
        SysRoleResource sysRoleResource;
        for (Long sysResourceId : sysRoleDTO.getSysResourceIdList()) {
            sysRoleResource = new SysRoleResource();
            sysRoleResource.setSysRoleId(sysRole.getSysRoleId());
            sysRoleResource.setSysResourceId(sysResourceId);
            sysRoleResource.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            sysRoleResourceList.add(sysRoleResource);
        }
        // 保存给该角色分配的资源
        log.info("保存角色的菜单资源");
        QSysRoleResource.sysRoleResource.save(sysRoleResourceList);*/
    /*    return CommonResult.getSuccessResultData("保存成功!");
    }*/

    /**
     * @Author:chenxf
     * @Description: 权限分配角色联想控件查询接口
     * @Date: 16:29 2021/1/29
     * @Param: [sysRoleQueryVo]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     */
    @Override
    public List<SysRoleDTO> querySysRoleByAppAndCompany(SysRoleQueryVO sysRoleQueryVo) {
//        AuthUserInfoModel userInfoModel = (AuthUserInfoModel)userService.getCurrentLoginUser();
//        if (Objects.isNull(userInfoModel)){
//            log.error("获取登录信息为空");
//            throw new BusinessRuntimeException("获取登录信息失败");
//        }
//        if (userInfoModel.getUemUserId() == null){
//            log.error("获取登录信息的用户id为空");
//            throw new BusinessRuntimeException("获取登录信息失败");
//        }
//        if (Objects.isNull(userInfoModel.getBlindCompanny())){
//            log.error("获取登录信息，用户未绑定企业");
//            throw new BusinessRuntimeException("用户未绑定企业，请确认！");
//        }
//        if (Objects.isNull(sysRoleQueryVo)|| Objects.isNull(sysRoleQueryVo.getSysApplicationId())){
//            log.error("入参错误，应用id不存在");
//            throw new BusinessRuntimeException("入参错误，请确认");
//        }
//        // 查询企业可分配权限的角色
//        return QSysRole.sysRole.select(
//                QSysRole.sysRole.fieldContainer()
//        ).where(
//                QSysRole.isValid.eq$(Boolean.TRUE)
//                        .and(QSysRole.sysApplicationId.eq$(sysRoleQueryVo.getSysApplicationId()))
//                        .and(QSysRole.sysRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
//                        .and(QSysRole.uemCompanyRole.chain(QUemCompanyRole.uemCompanyId).eq$(userInfoModel.getBlindCompanny()))
//        ).mapperTo(SysRoleDTO.class)
//                .execute();
        return null;
    }

    /**
     * @Author: cjh
     * @Description: 权限分配角色联想控件查询接口-用户管理
     * @Date: 19:29 2021/11/30
     * @Param: [sysRoleQueryVo]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     */
    @Override
    public List<SysRoleDTO> querySysRoleByAppAndCompanyByUser(SysRoleQueryVO sysRoleQueryVo) {
//        UemUser uemUserTemp = QUemUser.uemUser.selectOne().byId(sysRoleQueryVo.getUemUserId());
//
//        if (Objects.isNull(uemUserTemp.getBlindCompanny())){
//            log.error("用户未绑定企业");
//            throw new BusinessRuntimeException("用户未绑定企业，请确认！");
//        }
//        if (Objects.isNull(sysRoleQueryVo.getSysApplicationId())){
//            log.error("入参错误，应用id不存在");
//            throw new BusinessRuntimeException("入参错误，请确认！");
//        }
//        // 查询企业可分配权限的角色
//        return QSysRole.sysRole.select(
//                QSysRole.sysRole.fieldContainer()
//        ).where(
//                QSysRole.isValid.eq$(Boolean.TRUE)
//                        .and(QSysRole.sysApplicationId.eq$(sysRoleQueryVo.getSysApplicationId()))
//                        .and(QSysRole.sysRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
//                        .and(QSysRole.uemCompanyRole.chain(QUemCompanyRole.uemCompanyId).eq$(uemUserTemp.getBlindCompanny()))
//        ).mapperTo(SysRoleDTO.class)
//                .execute();
        return null;
    }

    /**
     * @Author:chenxf
     * @Description: 递归查询，查询当前角色的所有下属角色
     * @Date: 15:55 2020/11/16
     * @Param: [sysRoleDTO, sysRoleQueryVo]
     * @Return:void
     */
    private void dealWithSysRole(SysRoleDTO sysRoleDTO, SysRoleQueryVO sysRoleQueryVo) {
        List<SysRoleDTO> sysRoleDTOList = QSysRole.sysRole.select(
                        QSysRole.sysRole.fieldContainer()
                ).where(
                        QSysRole.sysApplicationId.eq(SYS_APPLICATION_ID_PLACEHOLDER)
                                .and(QSysRole.rolePid.eq(":pid"))
                                .and(QSysRole.roleName.like(ROLE_NAME_PLACEHOLDER))
                                .and(QSysRole.isValid.eq(IS_VALID_PLACEHOLDER))
                ).mapperTo(SysRoleDTO.class)
                .execute(sysRoleQueryVo);
        if (CollectionUtils.isNotEmpty(sysRoleDTOList)) {
            for (SysRoleDTO sysRole : sysRoleDTOList) {
                sysRoleQueryVo.setPid(sysRole.getSysRoleId());
                dealWithSysRole(sysRole, sysRoleQueryVo);
            }
            sysRoleDTO.setChildrenSysRoleList(sysRoleDTOList);
        }
    }

    /**
     * 根据角色获取用户列表
     *
     * @param roleCode 角色代码
     * @return 用户列表
     */
    @Override
    public List<RoleVO> getRoleByRoleCode(String roleCode) {
        List<RoleVO> list = new ArrayList<>();
        // 判空
        if (StringUtils.isNotEmpty(roleCode)) {
            // 获取调度系统角色
            List<SysRole> sysRoleList = QSysRole.sysRole.select()
                    .where(QSysRole.roleCode.eq$(roleCode).and(QSysRole.sysApplicationId.eq$(applicationIdSupplyChain)))
                    .execute();
            // 判空
            if (CollectionUtils.isNotEmpty(sysRoleList)) {
                RoleVO roleVO = new RoleVO();
                roleVO.setName(sysRoleList.get(0).getRoleName());
                roleVO.setAssignerId(sysRoleList.get(0).getRoleCode());
                list.add(roleVO);
            }
        }
        return list;
    }

    /**
     * 获取所有未禁用角色
     *
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.support.model.Role>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    public ResultHelper<List<SysRoleDTO>> queryAllValidRole() {
        List<SysRoleDTO> sysRoleDTOList = QSysRole.sysRole
                .select(QSysRole.sysRole.fieldContainer())
                .where(QSysRole.isValid.eq$(true).and(QSysRole.isDeleted.eq$(false)))
                .mapperTo(SysRoleDTO.class)
                .execute();
        return CommonResult.getSuccessResultData(sysRoleDTOList);
    }

    /**
     * 增加角色
     *
     * @author wzr
     * @date 2022-07-29
     */
    @Override
    public ResultHelper<Object> saveSysRole(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = new SysRole();
        String roleName = sysRoleDTO.getRoleName();
        String remark = sysRoleDTO.getRemark();
        sysRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysRole.setRoleName(roleName);
        sysRole.setSysApplicationId(1L);
        sysRole.setRemark(remark);
        sysRole.setIsValid(true);
        QSysRole.sysRole.save(sysRole);
        System.out.println(sysRole);
        //查询角色的id插入中间表
        Long sysRoleId = sysRole.getSysRoleId();
        // 判断是角色id否存在
        if (sysRoleId != null) {
            //获取前端勾选的权限id
            List<String> sysResourceIdList = sysRoleDTO.getSysResourceIdList();
            //临时变量i
            int i = 0;
            //逐个存入中间表中
            for (String s : sysResourceIdList) {
                //中间表对象
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                s = sysResourceIdList.get(i);
                i = ++i;
                sysRoleResource.setSysRoleId(sysRoleId);
                sysRoleResource.setSysResourceId(Long.valueOf(s));
                //插入中间表
                QSysRoleResource.sysRoleResource.save(sysRoleResource);
            }
        } else {
            return CommonResult.getFaildResultData("新增失败！");
        }
        return CommonResult.getSuccessResultData("新增成功!");
    }


    /**
     * 逻辑删除菜单信息
     *
     * @author wzr
     * @date 2022-07-29
     */
    @Override
    public ResultHelper<Object> deleteRole(Long sysRoleId) {
        QSysRole.sysRole.deleteById(sysRoleId);
        return CommonResult.getSuccessResultData("删除成功");
    }

    /**
     * 分页待条件查询角色信息
     *
     * @author wzr
     * @date 2022-07-29
     */
    @Override
    public Page<SysRoleDTO> queryRoleByPage(SysRoleDTO sysRoleDTO) {
        Integer currentPage = sysRoleDTO.getCurrentPage();
        Integer pageSize = sysRoleDTO.getPageSize();
        return QSysRole.sysRole.select(
                        QSysRole.roleName,
                        QSysRole.sysRoleId,
                        QSysRole.remark,
                        QSysRole.creatorName,
                        QSysRole.createTime,
                        QSysRole.isValid).
                where(QSysRole.roleName._like$_(sysRoleDTO.getRoleName())
                        .and(QSysRole.isValid.eq(":isValid")))
                .paging((currentPage == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : currentPage, (pageSize == null)
                        ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize).mapperTo(SysRoleDTO.class).sorting(QSysRole.sysRoleId.asc())
                .execute();
    }

    @Override
    public List<SysRoleDTO> queryRoleById(Long sysRoleId) {
        Map<String, Long> map = new HashMap<>(1);
        map.put("sysRoleId", sysRoleId);
        return DSContext.customization("WL-ERM_queryRoleAndResource")
                .select().mapperTo(SysRoleDTO.class).execute(map);
    }

    /**
     * 更新角色信息
     *
     * @author wzr
     * @date 2022-07-29
     */
    @Override
    public ResultHelper<Object> updateSysRole(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = new SysRole();
        Long sysRoleId = sysRoleDTO.getSysRoleId();
        String roleName = sysRoleDTO.getRoleName();
        String remark = sysRoleDTO.getRemark();
        sysRole.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysRole.setRoleName(roleName);
        sysRole.setRemark(remark);
        sysRole.setSysRoleId(sysRoleId);
        sysRole.setSysApplicationId(1L);
        QSysRole.sysRole.save(sysRole);
        // 判断是角色id否存在
        if (sysRoleId != null) {
            //获取前端勾选的权限id
            List<String> sysResourceIdList = sysRoleDTO.getSysResourceIdList();
            List<String> sysRoleRourceIdList = sysRoleDTO.getSysRoleResourceIdList();
            //逐个存入中间表中
            for (int i = 0; i < sysResourceIdList.size(); i++) {
                //中间表对象
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                String sysResourceId = sysResourceIdList.get(i);
                //中间表主键为list---同一个角色拥有多种权限，但编辑时中间表主键id为多个,故存为List
                String sysRoleResourceId = sysRoleRourceIdList.get(i);
                sysRoleResource.setSysRoleId(sysRoleId);
                sysRoleResource.setSysResourceId(Long.valueOf(sysResourceId));
                sysRoleResource.setSysRoleResourceId(Long.valueOf(sysRoleResourceId));
                //更新中间表
                QSysRoleResource.sysRoleResource.save(sysRoleResource);
            }
        } else {
            return CommonResult.getFaildResultData("更新失败！");
        }
        return CommonResult.getSuccessResultData("更新成功!");
    }

    /**
     * 菜单是否禁用状态
     *
     * @author wzr
     * @date 2022-07-29
     */
    @Override
    public ResultHelper<Object> updateRoleStatus(SysRoleDTO sysRoleDTO) {
        Long sysRoleId = sysRoleDTO.getSysRoleId();
        //是否禁用（0禁用，1启用）
        Boolean isValid = sysRoleDTO.getIsValid();

        int updateCount = QSysRole.sysRole
                .update(
                        QSysRole.isValid,
                        QSysRole.invalidTime
                )
                .where(QSysRole.sysRoleId.eq$(sysRoleId))
                .execute(isValid, new Date(), sysRoleId);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("禁用成功");
        } else {
            return CommonResult.getFaildResultData("禁用失败");
        }
    }

}
