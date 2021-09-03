package com.vbobot.simple.seata.tcc.spring.cloud.order.action.impl;

import com.vbobot.simple.seata.tcc.spring.cloud.order.action.CreateOrderAction;
import com.vbobot.simple.seata.tcc.spring.cloud.order.dao.TccOrderDO;
import com.vbobot.simple.seata.tcc.spring.cloud.order.dao.TccOrderRepository;
import com.vbobot.simple.seata.tcc.spring.cloud.order.dto.CreateOrderParamDTO;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Slf4j
@Service
public class CreateOrderActionImpl implements CreateOrderAction {

    @Resource TccOrderRepository tccOrderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prepareCreateOrder(CreateOrderParamDTO param) {
        AtomicReference<Integer> rOrderId = new AtomicReference<>();
        final TccOrderDO order = new TccOrderDO(param);
        final TccOrderDO save = tccOrderRepository.save(order);
        final Integer id = save.getId();
        rOrderId.set(id);

        log.info("=====> Prepare create order, orderId:{}, param:{}", rOrderId.get(), param);
    }
}
