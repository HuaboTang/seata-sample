package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import com.alibaba.fastjson.JSONObject;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductStorageParamDTO;
import com.vbobot.seata.sample.common.IdempotentUtils;
import io.seata.rm.tcc.api.BusinessActionContext;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        if (context == null || StringUtils.isBlank(context.getXid())) {
            return;
        }

        if (IdempotentUtils.handled(context.getXid())) {
            return;
        }

        tccStorageRepository.prepareReduce(param.getGoodsId(), param.getDeductStorage());
//        log.info("=====>prepareDeductStorage, param:{}, context:{}", param, context);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prepareDeductStorageRollback(BusinessActionContext context,
            DeductStorageParamDTO param) {
        final Optional<TccStorageDO> oStorage = tccStorageRepository
                .findFirstByGoodsId(param.getGoodsId());
        Assert.isTrue(oStorage.isPresent());
        final TccStorageDO storage = oStorage.get();
        tccStorageRepository.prepareReduce(storage.getGoodsId(), param.getDeductStorage());
//        log.info("=====>prepareDeductStorageRollback, param:{}, context:{}", param, context);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean commit(BusinessActionContext context) {
        if (context == null || StringUtils.isBlank(context.getXid())) {
            return true;
        }

        if (IdempotentUtils.handled(context.getXid())) {
            return true;
        }

        try {
            DeductStorageParamDTO param = ((JSONObject) context
                    .getActionContext("deductStorageParam"))
                    .toJavaObject(DeductStorageParamDTO.class);
            if (param == null) {
                return true;
            }

            tccStorageRepository.commitReduce(param.getGoodsId(), param.getDeductStorage());
//            log.info("=====>commitDeductStorage, param:{}, context:{}", param, context);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rollback(BusinessActionContext context) {
        if (context == null || StringUtils.isBlank(context.getXid())) {
            return true;
        }

        if (IdempotentUtils.handled(context.getXid())) {
            return true;
        }

        try {
            DeductStorageParamDTO param = ((JSONObject) context
                    .getActionContext("deductStorageParam"))
                    .toJavaObject(DeductStorageParamDTO.class);
            if (param == null) {
                return true;
            }
            tccStorageRepository.rollbackReduce(param.getGoodsId(), param.getDeductStorage());
//            log.info("=====>rollbackDeductStorage, param:{}, context:{}", param, context);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
}
