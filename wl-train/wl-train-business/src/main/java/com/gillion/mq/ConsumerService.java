package com.gillion.mq;

import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 2:25 下午
 * @email wengms@gillion.com.cn
 */
public interface ConsumerService {
    /**
     * 单消息生产
     * @param order
     */
    void handlerOrder(Order order);

    /**
     * 批量消息生产
     * @param orders
     */
    void handlerOrders(List<Order> orders);




}
