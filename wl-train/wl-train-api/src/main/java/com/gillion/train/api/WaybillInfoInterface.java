package com.gillion.train.api;

import com.gillion.train.api.model.vo.WaybillInfoVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author guosc
 * @Email guosc@gillion.com.cn
 * @date 2019/12/19
 * @time 15:27
 * @description:
 */
//@FeignClient
public interface WaybillInfoInterface {
    /**
     * 保存运单信息
     * @param waybillInfo
     * @return
     */
    Map<String, Object> saveWaybillInfo(@RequestBody WaybillInfoVo waybillInfo);

    /**
     * 事务验证测试
     * @return
     */
    Map<String, Object> transactionTest();
}
