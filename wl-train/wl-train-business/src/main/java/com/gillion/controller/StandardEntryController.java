package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.SysTechnicalTitleAndPostVO;
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


    @PostMapping("/sysTechnicalTitle/queryByTechnicalTitleName")
   public ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO){
        return standardEntryInterface.queryByTechnicalTitleName(sysTechnicalTitleAndPostVO);
    }






}
