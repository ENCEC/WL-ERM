package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.SysPostDTO;
import com.share.auth.domain.SysTechnicalTitleAndPostVO;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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





}
