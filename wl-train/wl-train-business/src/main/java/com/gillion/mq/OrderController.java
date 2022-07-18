package com.gillion.mq;

import com.gillion.ec.core.utils.ResultUtils;
import com.gillion.ec.scheduler.utils.ThreadUtils;
import org.jooq.lambda.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wengms
 * @date 2020/2/6 5:51 下午
 * @email wengms@gillion.com.cn
 */
@RequestMapping("/client/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/single")
    public Map<String, Object> createOrder() {
        orderService.createOrder();
        return ResultUtils.getSuccessResultData();
    }

    @GetMapping("/batch")
    public Map<String, Object> createBatchOrder() {
        orderService.createOrders();
        return ResultUtils.getSuccessResultData();
    }

    @GetMapping("/deliver")
    public Map<String, Object> deliverOrder() {
        orderService.deliverOrder();
        return ResultUtils.getSuccessResultData();
    }

    @GetMapping("/consistency")
    public Map<String, Object> consistencyCreateOrder() {
        orderService.consistencyCreateOrder();
        return ResultUtils.getSuccessResultData();
    }
}
