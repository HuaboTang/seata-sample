package com.vbobot.simple.seata.tcc.spring.cloud.order.action;

import com.vbobot.simple.seata.tcc.spring.cloud.order.dto.CreateOrderParamDTO;

/**
 * @author Bobo
 * @date 2021/8/2
 */
public interface CreateOrderAction {

    void prepareCreateOrder(CreateOrderParamDTO param);

}
