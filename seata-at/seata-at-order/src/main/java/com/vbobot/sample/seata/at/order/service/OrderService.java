package com.vbobot.sample.seata.at.order.service;

import com.vbobot.sample.seata.at.feign.AccountFeign;
import com.vbobot.sample.seata.at.feign.DeductBalanceParamDTO;
import com.vbobot.sample.seata.at.feign.DeductStorageParamDTO;
import com.vbobot.sample.seata.at.feign.StorageFeign;
import com.vbobot.sample.seata.at.order.dao.OrderDO;
import com.vbobot.sample.seata.at.order.dao.OrderRepository;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Slf4j
@Service
public class OrderService {
    @Resource
    OrderRepository orderRepository;

    @Resource
    AccountFeign accountFeign;

    @Resource
    StorageFeign storageFeign;

    @GlobalTransactional(rollbackFor = Exception.class)
    public void commitCreateOrder() {
        final int accountUserId = 2021;
        final int goodsId = 728;

        final DeductBalanceParamDTO deductBalanceParam = new DeductBalanceParamDTO();
        deductBalanceParam.setAccountUserId(accountUserId);
        deductBalanceParam.setDeductValue(500);
        accountFeign.commitDeductBalance(deductBalanceParam);

        createOrder(accountUserId, goodsId);

        final DeductStorageParamDTO deductStorageParam = new DeductStorageParamDTO();
        deductStorageParam.setGoodsId(goodsId);
        deductStorageParam.setAccountUserId(accountUserId);
        deductStorageParam.setDeductStorage(10);
        storageFeign.commitDeductStorage(deductStorageParam);
    }

    private void createOrder(int accountUserId, int goodsId) {
        final OrderDO order = new OrderDO();
        order.setAccountUserId(accountUserId);
        order.setGoodsId(goodsId);
        order.setNumber(10);
        order.setAmount(500);
        order.setCreateTime(new Date());
        final OrderDO saveResult = orderRepository.save(order);
        log.info("=====>Create-order:{}", saveResult);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void rollbackCreateOrder() {
        final int accountUserId = 2021;
        final int goodsId = 728;

        createOrder(accountUserId, goodsId);

        final DeductBalanceParamDTO deductBalanceParam = new DeductBalanceParamDTO();
        deductBalanceParam.setAccountUserId(accountUserId);
        deductBalanceParam.setDeductValue(500);

        // 这里必须想办法抛出异常，如果accountFeign处理了异常，只是返回状态码，那不会触发全局rollback
        accountFeign.rollbackDeductBalance(deductBalanceParam);

        final DeductStorageParamDTO deductStorageParam = new DeductStorageParamDTO();
        deductStorageParam.setGoodsId(goodsId);
        deductStorageParam.setAccountUserId(accountUserId);
        deductStorageParam.setDeductStorage(10);
        storageFeign.commitDeductStorage(deductStorageParam);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void rollbackAtLastCreateOrder() {
        final int accountUserId = 2021;
        final int goodsId = 728;

        createOrder(accountUserId, goodsId);

        final DeductBalanceParamDTO deductBalanceParam = new DeductBalanceParamDTO();
        deductBalanceParam.setAccountUserId(accountUserId);
        deductBalanceParam.setDeductValue(500);
        accountFeign.commitDeductBalance(deductBalanceParam);

        final DeductStorageParamDTO deductStorageParam = new DeductStorageParamDTO();
        deductStorageParam.setGoodsId(goodsId);
        deductStorageParam.setAccountUserId(accountUserId);
        deductStorageParam.setDeductStorage(10);
        storageFeign.rollbackDeductStorage(deductStorageParam);
    }
}
