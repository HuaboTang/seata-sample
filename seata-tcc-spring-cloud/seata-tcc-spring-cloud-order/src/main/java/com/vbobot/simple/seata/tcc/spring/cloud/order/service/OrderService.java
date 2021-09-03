package com.vbobot.simple.seata.tcc.spring.cloud.order.service;

/**
 * @author Bobo
 * @date 2021/8/2
 */
public interface OrderService {

    void createOrder();

    void createOrderRollback();
}
