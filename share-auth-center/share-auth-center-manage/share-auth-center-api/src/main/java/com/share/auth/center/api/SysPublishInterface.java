package com.share.auth.center.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "${application.name.center}")
public interface SysPublishInterface {

    /**
     * 获取系统发布时间
     *
     * @return 系统发布时间
     * @author wangzicheng
     * @date 2021/04/15
     */
    @RequestMapping("/sysPublishConfig/getTimes")
    String getTimes();
}
