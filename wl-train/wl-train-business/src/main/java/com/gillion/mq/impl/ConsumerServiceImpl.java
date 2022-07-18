package com.gillion.mq.impl;

import com.gillion.ec.distributed.annotations.Lockable;
import com.gillion.ec.mq.annotation.ConsumerConfig;
import com.gillion.ec.mq.annotation.MQConsumer;
import com.gillion.ec.mq.config.MessageMode;
import com.gillion.ec.scheduler.utils.ThreadUtils;
import com.gillion.mq.ConsumerService;
import com.gillion.mq.Order;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Seq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author wengms
 * @date 2020/2/6 7:15 下午
 * @email wengms@gillion.com.cn
 */
@MQConsumer
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private int count = 0;

    @Transactional(rollbackFor = Exception.class)
    //@ConsumerConfig(topic = "order", group = "handlerOrder")
    @Override
    public void handlerOrder(Order order) {
        log.info("收到订单：{}", order.getOrderId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ConsumerConfig(topic = "order", group = "batchCreateOrders", messageMode = MessageMode.Batch)
    public void handlerOrders(List<Order> orders) {
        if (count++ % 3 != 0) {
            Seq.seq(orders)
                    .forEach(order -> {
                        log.info("收到订单：{}信息", order.getOrderId());
                    });
        } else {
            throw new RuntimeException("消费出现异常");
        }
        ThreadUtils.sleep(3000);
    }


}
