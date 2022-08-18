package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.service.StandardEntryService;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.*;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName StandardEntryController
 * @Author weiq
 * @Date 2022/8/1 10:51
 * @Version 1.0
 **/
@Api("规则目录")
@RestController
@Slf4j
@RequestMapping("/standardEntry")
public class StandardEntryController {
    @Autowired
    private StandardEntryInterface standardEntryInterface;

    @Autowired
    private StandardEntryService standardEntryService;

    /**
     * 获取岗位职称信息
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @PostMapping("/sysTechnicalTitle/queryByTechnicalTitleName")
   public ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO){
        return standardEntryInterface.queryByTechnicalTitleName(sysTechnicalTitleAndPostVO);
    }
    /**
     * 获取岗位信息
     * @param sysPostDTO
     * @return
     */
    @PostMapping("/sysPost/querySysPost")
    ResultHelper<Page<SysPostDTO>> querySysPost(@RequestBody SysPostDTO sysPostDTO) {
        return  standardEntryInterface.querySysPost(sysPostDTO);
    }
    /**
     * 获取用户信息
     * @param uemUserDto
     * @return
     */
    @PostMapping("/uemUserManage/queryUemUser")
    ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        return standardEntryInterface.queryUemUser(uemUserDto);
    }

    /**
     * 获取角色信息
     * @param sysRoleDTO
     * @return
     */
    @PostMapping("/sysRole/queryRoleByPage")
    ResultHelper<Page<SysRoleDTO>> queryRoleByPage(@RequestBody SysRoleDTO sysRoleDTO) {
        return standardEntryInterface.queryRoleByPage(sysRoleDTO);
    }
    /**
     * 获取条目类型信息
     * @param sysDictTypeDto
     * @return
     */
    @PostMapping("/sysDictType/querySysDictCodeByDictType")
    public ResultHelper<List<SysDictCodeDTO>> querySysDictCodeByDictType(@RequestBody SysDictTypeDto sysDictTypeDto) {
        return standardEntryInterface.querySysDictCodeByDictType(sysDictTypeDto);
    }

    /**
     * 获取规范条目信息
     * @param standardEntryDTO
     * @return
     */
    @PostMapping("/queryStandardEntry")
    public ResultHelper<Page<StandardEntryDTO>> queryStandardEntry(@RequestBody StandardEntryDTO standardEntryDTO) {
        return standardEntryService.queryStandardEntry(standardEntryDTO);
    }

    /**
     * 新增条目
     * @param standardEntryDTO
     * @return
     */
    @PostMapping("/saveStandardEntry")
    public ResultHelper<?> saveStandardEntry(@RequestBody StandardEntryDTO standardEntryDTO) {
        return standardEntryService.saveStandardEntry(standardEntryDTO);
    }

    /**
     * 查看一个条目信息
     * @param standardEntryId
     * @return
     */
    @GetMapping("/queryByStandardEntryId")
    public ResultHelper<?> queryByStandardEntryId(Long standardEntryId) {
        return standardEntryService.queryByStandardEntryId(standardEntryId);
    }

    /**
     * 启动/禁用
     * @param standardEntryDTO
     * @return
     */
    @GetMapping("/updateStatus")
    public ResultHelper<?> updateStatus(StandardEntryDTO standardEntryDTO) {
        return standardEntryService.updateStatus(standardEntryDTO);
    }

    /**
     * 删除条目
     * @param standardEntryId
     * @return
     */
    @GetMapping("/deleteStandardEntry")
    public ResultHelper<?> deleteStandardEntry(Long standardEntryId) {
        return standardEntryService.deleteStandardEntry(standardEntryId);
    }

    /**
     * 编辑条目
     * @param standardEntryDTO
     * @return
     */
    @PostMapping("/updateStandardEntry")
    public ResultHelper<?> updateStandardEntry(@RequestBody StandardEntryDTO standardEntryDTO) {
        return standardEntryService.updateStandardEntry(standardEntryDTO);
    }

}
