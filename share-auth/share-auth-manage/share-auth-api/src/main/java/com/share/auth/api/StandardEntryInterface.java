package com.share.auth.api;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysTechnicalTitleAndPostVO;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName StandardEntryInterface
 * @Author weiq
 * @Date 2022/8/1 10:30
 * @Version 1.0
 **/
@Component
@FeignClient(value = "${application.name.auth}")
public interface StandardEntryInterface {

    @PostMapping("/sysTechnicalTitle/queryByTechnicalTitleName")
    ResultHelper<Page<SysTechnicalTitleAndPostVO>> queryByTechnicalTitleName(@RequestBody SysTechnicalTitleAndPostVO sysTechnicalTitleAndPostVO);
}
