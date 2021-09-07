package com.vbobot.sample.seata.tcc.spring.cloud.order.service.impl;

import com.vbobot.sample.seata.tcc.spring.cloud.feign.AccountFeign;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductBalanceParamDTO;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductStorageParamDTO;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.StorageFeign;
import com.vbobot.sample.seata.tcc.spring.cloud.order.action.CreateOrderAction;
import com.vbobot.sample.seata.tcc.spring.cloud.order.dto.CreateOrderParamDTO;
import com.vbobot.sample.seata.tcc.spring.cloud.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource AccountFeign accountFeign;
    @Resource StorageFeign storageFeign;

    @Resource CreateOrderAction createOrderAction;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void createOrder() {
        final CreateOrderParamDTO param = new CreateOrderParamDTO();
        final int accountUserId = 100;
        param.setAccountId(accountUserId);
        param.setGoodsId(200);
        param.setAmount(100);
        createOrderAction.prepareCreateOrder(param);
        final DeductBalanceParamDTO deductParam = new DeductBalanceParamDTO();
        deductParam.setAccountUserId(accountUserId);
        deductParam.setDeductValue(100);
        accountFeign.prepareDeductBalance(deductParam);

        final DeductStorageParamDTO deductStorageParam = new DeductStorageParamDTO();
        deductStorageParam.setGoodsId(200);
        deductStorageParam.setAccountUserId(100);
        deductStorageParam.setDeductStorage(1);
        storageFeign.prepareDeductStorageForCommit(deductStorageParam);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void createOrderRollback() {
        final CreateOrderParamDTO param = new CreateOrderParamDTO();
        final int accountUserId = 100;
        param.setAccountId(accountUserId);
        param.setGoodsId(200);
        param.setAmount(100);
        createOrderAction.prepareCreateOrder(param);
        final DeductBalanceParamDTO deductParam = new DeductBalanceParamDTO();
        deductParam.setAccountUserId(accountUserId);
        deductParam.setDeductValue(100);
        accountFeign.prepareDeductBalance(deductParam);

        final DeductStorageParamDTO deductStorageParam = new DeductStorageParamDTO();
        deductStorageParam.setGoodsId(200);
        deductStorageParam.setAccountUserId(100);
        deductStorageParam.setDeductStorage(1);
        storageFeign.prepareDeductStorageForRollback(deductStorageParam);
    }
}
