package com.share.auth.controller;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ec.core.utils.ResultUtils;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.vo.SysTechnicalTitleAndPostVO;
import com.share.auth.service.SysTechnicalTitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName SySTechnicalTitleController
 * @Description TODO
 * @Author weiq
 * @Date 2022/7/25 16:38
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/sysTechnicalTitle")
public class SysTechnicalTitleController {

    @Autowired
    private SysTechnicalTitleService sysTechnicalTitleService;

    /**
     * 分页查询全部岗位职称
     * @param currentPage 第几页
     * @param pageSize 每页的数据条数
     * @return
     */
    @GetMapping ("/queryByPageAll")
    public Page<SysTechnicalTitleAndPostVO> queryByPageAll(Integer currentPage, Integer pageSize) {
       return sysTechnicalTitleService.queryByPageAll(currentPage,pageSize);
    }

    /**
     * 通过条件分页查询岗位职称
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping ("/queryByTechnicalTitleName")
    public Page<SysTechnicalTitleAndPostVO> queryByTechnicalTitleName(Integer currentPage, Integer pageSize,String name) {
        return sysTechnicalTitleService.queryByTechnicalTitleName(currentPage,pageSize,name);
    }

    /**
     * 新增职称
     * @param sysTechnicalTitleAndPostVO
     * @return
     */
    @PostMapping("/saveSysTechnicalTitle")
    public Map<String,Object> saveSysTechnicalTitle(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO ) {
        sysTechnicalTitleService.saveSysTechnicalTitle(sysTechnicalTitleAndPostVO);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 编辑职称
     * @param sysTechnicalTitle
     * @return
     */
    @PostMapping("/updateSysTechnicalTitle")
    public Map<String,Object> updateSysTechnicalTitle(@RequestBody SysTechnicalTitle sysTechnicalTitle) {
        sysTechnicalTitleService.updateSysTechnicalTitle(sysTechnicalTitle);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 删除职称
     * @param technicalName
     * @return
     */
    @DeleteMapping("/deleteSysTechnicalTitle")
    public Map<String,Object> deleteSysTechnicalTitle(String technicalName) {
        sysTechnicalTitleService.deleteSysTechnicalTitle(technicalName);
        return ResultUtils.getSuccessResultData(true);
    }

    /**
     * 禁用
     * @param sysTechnicalTitle
     * @return
     */
    @PostMapping("/updateStatus")
    public Map<String,Object> updateStatus(SysTechnicalTitle sysTechnicalTitle) {
        sysTechnicalTitleService.updateStatus(sysTechnicalTitle);
        return ResultUtils.getSuccessResultData(true);
    }

}
