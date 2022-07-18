package com.gillion.remote;

import com.gillion.model.entity.WaybillInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author guosc
 * @Email guosc@gillion.com.cn
 * @date 2019/11/18
 * @time 17:28
 * @description:
 */
@FeignClient(value = "daoservice-quickstart-app2", path = "/producer-demo")
public interface FeignRemoteRouteService {

    @PostMapping(value = "/remoteRoute/calculateRouteInfos")
    void calculateRouteInfos(WaybillInfo waybillInfo);
}
