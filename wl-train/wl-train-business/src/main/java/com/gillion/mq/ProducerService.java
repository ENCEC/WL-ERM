package com.gillion.mq;

import com.gillion.ec.mq.annotation.MQProducer;
import com.gillion.ec.mq.annotation.ProducerConfig;
import com.gillion.ec.mq.config.MessageMode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 2:19 下午
 * @email wengms@gillion.com.cn
 */
@MQProducer
public interface ProducerService {
    /**
     * 单消息生产 #p0.orderId
     *          message.body.orderId
     * @param order
     */
    @Transactional(rollbackFor = Exception.class)
    @ProducerConfig(topic = "order",key = "#message.body.orderId",tags = "create",messageMode = MessageMode.Single,transaction = true)
    void createOrder(Order order);

    /**
     * 批量消息生产
     * @param orders
     */
    @Transactional(rollbackFor = Exception.class)
    @ProducerConfig(topic = "order", messageMode = MessageMode.Batch,tags = "create")
    void createOrders(List<Order> orders);


    /**
     * 订单发货
     * @param orderId
     */
    @Transactional(rollbackFor = Exception.class)
    @ProducerConfig(topic = "order",key = "#message.body.orderId",tags = "deliver",messageMode = MessageMode.Single,transaction = true)
    void deliverOrder(Order order);
}
