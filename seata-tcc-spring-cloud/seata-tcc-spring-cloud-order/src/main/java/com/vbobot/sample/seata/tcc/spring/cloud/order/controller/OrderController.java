package com.vbobot.sample.seata.tcc.spring.cloud.order.controller;

import com.vbobot.sample.seata.tcc.spring.cloud.order.service.OrderService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Slf4j
@RestController
public class OrderController {

    @Resource OrderService orderService;

    @GetMapping("/order/create/commit")
    public String createOrderCommit() {
        orderService.createOrder();
        return "create-order-commit";
    }

    @GetMapping("/order/create/rollback")
    public String createOrderRollback() {
        try {
            orderService.createOrderRollback();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "create-order-rollback";
    }
}
