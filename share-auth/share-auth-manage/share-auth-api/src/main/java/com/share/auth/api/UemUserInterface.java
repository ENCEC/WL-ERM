package com.share.auth.api;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
@FeignClient(value = "${application.name.auth}")
public interface UemUserInterface {

    /**
     * 获取用户信息
     *
     * @date 2022-07-25
     */
    @GetMapping("/uemUserManage/getUemUser")
    ResultHelper<UemUserDto> getUemUser(@RequestParam(name = "uemUserId") Long uemUserId);

    /**
     * 根据用户名、姓名或启禁用状态查询用户信息
     *
     * @date 2022-07-25
     */
    @PostMapping("/uemUserManage/queryUemUser")
    ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto);

    /**
     * 根据用户名、姓名查询所有在职用户列表
     *
     * @date 2022-07-25
     */
    @PostMapping("/uemUserManage/queryAllWorkUserList")
    ResultHelper<Page<UemUserDto>> queryAllWorkUserList(@RequestBody UemUserDto uemUserDto);
}
