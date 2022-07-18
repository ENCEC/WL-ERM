package com.gillion.mq;

import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 2:58 下午
 * @email wengms@gillion.com.cn
 */
public interface OrderService {
    /**
     * 单消息生产
     */
    void createOrder();

    /**
     * 批量消息生产
     */
    void createOrders();


    /**
     * 订单发货
     */
    void deliverOrder();


    /**
     * 一致性发送验证
     */
    void consistencyCreateOrder();
}
