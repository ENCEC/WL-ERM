package com.gillion.service;

import com.gillion.model.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author guosc
 * @Email guosc@gillion.com.cn
 * @date 2019/12/19
 * @time 8:57
 * @description:
 */
@FeignClient(value = "daoservice-quickstart", path = "/ec-demo")
public interface FeignTransactionTest {

    @PostMapping(value = "/updateEmployee")
    public Map<String, Object> updateEmployee(@RequestBody Employee employee);
}
