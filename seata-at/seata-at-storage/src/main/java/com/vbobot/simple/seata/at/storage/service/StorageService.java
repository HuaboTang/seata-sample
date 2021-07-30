package com.vbobot.simple.seata.at.storage.service;

import com.vbobot.simple.seata.at.feign.DeductStorageParamDTO;
import com.vbobot.simple.seata.at.storage.dao.StorageDO;
import com.vbobot.simple.seata.at.storage.dao.StorageRecordDO;
import com.vbobot.simple.seata.at.storage.dao.StorageRecordRepository;
import com.vbobot.simple.seata.at.storage.dao.StorageRepository;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.Date;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Slf4j
@Service
public class StorageService {

    @Resource
    StorageRepository storageRepository;

    @Resource
    StorageRecordRepository storageRecordRepository;

    @Transactional(rollbackFor = Exception.class)
    public void commitDeductStorage(DeductStorageParamDTO param) {
        deduct(param);
    }

    private void deduct(DeductStorageParamDTO param) {
        final Integer goodsId = param.getGoodsId();
        final Optional<StorageDO> oStorage = storageRepository.findByGoodsId(goodsId);

        final StorageDO storage;
        if (!oStorage.isPresent()) {
            storage = new StorageDO();
            storage.setGoodsId(goodsId);
            storage.setStorage(100);
        } else {
            storage = oStorage.get();
        }

        storage.setStorage(storage.getStorage() - param.getDeductStorage());

        storageRepository.save(storage);

        final StorageRecordDO storageRecord = new StorageRecordDO();
        storageRecord.setGoodsId(goodsId);
        storageRecord.setChangeStorage(param.getDeductStorage());
        storageRecord.setRecordTime(new Date());

        storageRecordRepository.save(storageRecord);
        log.info("=====>Storage:{}, record:{}, param:{}", storage, storageRecord, param);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void rollbackDeductStorage(DeductStorageParamDTO param) {
        deduct(param);
        throw new RuntimeException("1");
    }
}
