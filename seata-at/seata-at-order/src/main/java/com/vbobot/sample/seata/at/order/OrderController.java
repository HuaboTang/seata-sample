package com.vbobot.sample.seata.at.order;

import com.vbobot.sample.seata.at.order.service.OrderService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@RestController
public class OrderController {
    @Resource
    OrderService orderService;

    @PostMapping("/order/create/commit")
    public String commitCreateOrder() {
        orderService.commitCreateOrder();
        return "commit";
    }

    @PostMapping("/order/create/rollback")
    public String rollbackCreateOrder() {
        orderService.rollbackCreateOrder();
        return "rollback";
    }

    @PostMapping("/order/create/rollback/at/last")
    public String rollbackCreateOrderAtLast() {
        orderService.rollbackAtLastCreateOrder();
        return "rollbackAtLast";
    }
}
