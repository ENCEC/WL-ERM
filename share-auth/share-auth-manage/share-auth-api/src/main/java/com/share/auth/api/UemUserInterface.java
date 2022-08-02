package com.share.auth.api;

import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
@FeignClient(value = "${application.name.auth}")
public interface UemUserInterface {

    /**
     * 获取用户信息
     * @date 2022-07-25
     */
    @GetMapping("/getUemUser")
    ResultHelper<UemUserDto> getUemUser(@RequestParam Long uemUserId);
}
