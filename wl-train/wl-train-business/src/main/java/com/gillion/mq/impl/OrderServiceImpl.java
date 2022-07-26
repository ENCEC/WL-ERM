//package com.gillion.mq.impl;
//
//import com.gillion.ec.scheduler.utils.ThreadUtils;
//import com.gillion.mq.Order;
//import com.gillion.mq.OrderService;
//import com.gillion.mq.ProducerService;
//import org.jooq.lambda.Seq;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//
///**
// * @author wengms
// * @date 2021/1/11 2:58 下午
// * @email wengms@gillion.com.cn
// */
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    @Autowired
//    private ProducerService producerService;
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void createOrder() {
//        for (int i=0; i<1; i++) {
//            ThreadUtils.sleep(1000);
//            Order order = Order
//                    .builder()
//                    .orderId(UUID.randomUUID().toString())
//                    .orderNo(i+"")
//                    .createUser("zengqw")
//                    .type("0")
//                    .build();
//            producerService.createOrder(order);
//        }
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void createOrders() {
//        List<Order> orders = Seq.range(0,100)
//                .map(integer -> Order
//                        .builder()
//                        .orderId(UUID.randomUUID().toString())
//                        .orderNo("gl192829392")
//                        .createUser("wengms")
//                        .build())
//                .toList();
//        producerService.createOrders(orders);
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void deliverOrder() {
//        Order order = Order
//                .builder()
//                .orderId(UUID.randomUUID().toString())
//                .orderNo("gl192829392")
//                .createUser("wengms")
//                .build();
//        producerService.deliverOrder(order);
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void consistencyCreateOrder() {
//        Order order = Order
//                .builder()
//                .orderId(UUID.randomUUID().toString())
//                .orderNo("gl192829392")
//                .createUser("wengms")
//                .build();
//        producerService.deliverOrder(order);
//        throw new RuntimeException("模拟异常发生。");
//    }
//
//
//}
