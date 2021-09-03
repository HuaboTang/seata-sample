package com.vbobot.simple.seata.tcc.spring.cloud.storage;

import com.alibaba.fastjson.JSONObject;
import com.vbobot.simple.seata.tcc.spring.cloud.feign.DeductStorageParamDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Slf4j
@Service
public class DeductStorageActionImpl implements DeductStorageAction {

    @Resource TccStorageRepository tccStorageRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prepareDeductStorage(BusinessActionContext context, DeductStorageParamDTO param) {
        final Optional<TccStorageDO> oStorage = tccStorageRepository
                .findFirstByGoodsId(param.getGoodsId());
        Assert.isTrue(oStorage.isPresent());
        final TccStorageDO storage = oStorage.get();
        storage.prepareDeduct(param.getDeductStorage());
        tccStorageRepository.save(storage);
        log.info("=====>prepareDeductStorage, param:{}, context:{}", param, context);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prepareDeductStorageRollback(BusinessActionContext context,
            DeductStorageParamDTO param) {
        final Optional<TccStorageDO> oStorage = tccStorageRepository
                .findFirstByGoodsId(param.getGoodsId());
        Assert.isTrue(oStorage.isPresent());
        final TccStorageDO storage = oStorage.get();
        storage.prepareDeduct(param.getDeductStorage());
        tccStorageRepository.save(storage);
        log.info("=====>prepareDeductStorageRollback, param:{}, context:{}", param, context);
        throw new RuntimeException();
    }

    @Override
    public Boolean commit(BusinessActionContext context) {
        DeductStorageParamDTO param = ((JSONObject) context
                .getActionContext("deductStorageParam")).toJavaObject(DeductStorageParamDTO.class);
        final Optional<TccStorageDO> oStorage = tccStorageRepository
                .findFirstByGoodsId(param.getGoodsId());
        Assert.isTrue(oStorage.isPresent());
        final TccStorageDO storage = oStorage.get();
        storage.commitDeduct(param.getDeductStorage());
        tccStorageRepository.save(storage);
        log.info("=====>commitDeductStorage, param:{}, context:{}", param, context);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rollback(BusinessActionContext context) {
        DeductStorageParamDTO param = ((JSONObject) context
                .getActionContext("deductStorageParam")).toJavaObject(DeductStorageParamDTO.class);
        final Optional<TccStorageDO> oStorage = tccStorageRepository
                .findFirstByGoodsId(param.getGoodsId());
        Assert.isTrue(oStorage.isPresent());
        final TccStorageDO storage = oStorage.get();
        storage.rollbackDeduct(param.getDeductStorage());
        tccStorageRepository.save(storage);
        log.info("=====>rollbackDeductStorage, param:{}, context:{}", param, context);
        return true;
    }
}
