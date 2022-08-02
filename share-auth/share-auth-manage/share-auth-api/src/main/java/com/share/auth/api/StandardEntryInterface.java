package com.share.auth.api;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName StandardEntryInterface
 * @Author weiq
 * @Date 2022/8/1 10:30
 * @Version 1.0
 **/
@Component
@FeignClient(value = "${application.name.auth}")
public interface StandardEntryInterface {
    /**
     * 获取岗位职称信息1
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @PostMapping("/sysTechnicalTitle/queryByTechnicalTitleName")
    ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);

    /**
     * 获取岗位信息
     * @param sysPostDTO
     * @return
     */
    @PostMapping("/sysPost/querySysPost")
    ResultHelper<Page<SysPostDTO>> querySysPost(@RequestBody SysPostDTO sysPostDTO);

    /**
     * 获取用户信息
     * @param uemUserDto
     * @return
     */
    @PostMapping("/uemUserManage/queryUemUser")
    ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto);

    /**
     * 获取角色信息
     * @param sysRoleDTO
     * @return
     */
    @PostMapping("/sysRole/queryRoleByPage")
     ResultHelper<Page<SysRoleDTO>> queryRoleByPage(@RequestBody SysRoleDTO sysRoleDTO);

    /**
     * 获取条目类型信息
     * @param sysDictTypeDto
     * @return
     */
    @PostMapping("/sysDictType/querySysDictType")
    @ResponseBody
    ResultHelper<Page<SysDictTypeDto>> querySysDictType(@RequestBody SysDictTypeDto sysDictTypeDto);
}
