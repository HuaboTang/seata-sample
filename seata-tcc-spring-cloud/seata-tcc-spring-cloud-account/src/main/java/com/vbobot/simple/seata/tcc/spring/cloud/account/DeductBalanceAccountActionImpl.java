package com.vbobot.simple.seata.tcc.spring.cloud.account;

import com.alibaba.fastjson.JSONObject;
import com.vbobot.simple.seata.tcc.spring.cloud.feign.DeductBalanceParamDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Slf4j
@Service
public class DeductBalanceAccountActionImpl implements DeductBalanceAccountAction {

    @Resource TccAccountRepository tccAccountRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prepareDeductBalance(BusinessActionContext businessActionContext,
            DeductBalanceParamDTO param) {
        final Optional<TccAccountDO> oAccount = tccAccountRepository.findFirstByAccountUserId(
                param.getAccountUserId());
        assert oAccount.isPresent();
        final TccAccountDO account = oAccount.get();
        account.prepareDeduct(param.getDeductValue());
        tccAccountRepository.save(account);
        log.info("======>prepare deduct balance, account:{}, param:{}, actionContext:{}", account,
                param, businessActionContext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext businessActionContext) {
        final DeductBalanceParamDTO param = ((JSONObject) businessActionContext
                .getActionContext("deductBalanceParam"))
                .toJavaObject(DeductBalanceParamDTO.class);
        final Optional<TccAccountDO> oAccount = tccAccountRepository.findFirstByAccountUserId(
                param.getAccountUserId());
        assert oAccount.isPresent();
        final TccAccountDO account = oAccount.get();
        account.commitDeduct(param.getDeductValue());
        tccAccountRepository.save(account);
        log.info("======>commit deduct balance, account:{}, param:{}, actionContext:{}", account,
                param, businessActionContext);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext businessActionContext) {
        final DeductBalanceParamDTO param = ((JSONObject) businessActionContext
                .getActionContext("deductBalanceParam"))
                .toJavaObject(DeductBalanceParamDTO.class);
        final Optional<TccAccountDO> oAccount = tccAccountRepository.findFirstByAccountUserId(
                param.getAccountUserId());
        assert oAccount.isPresent();
        final TccAccountDO account = oAccount.get();
        account.rollbackDeduct(param.getDeductValue());
        tccAccountRepository.save(account);
        log.info("======>rollback deduct balance, account:{}, param:{}, actionContext:{}", account,
                param, businessActionContext);
        return true;
    }
}
