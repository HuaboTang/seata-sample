package com.vbobot.sample.seata.tcc.transfer.action.impl;

import com.vbobot.sample.seata.tcc.transfer.action.TransferInAction;
import com.vbobot.sample.seata.tcc.transfer.dao.AccountDO;
import com.vbobot.sample.seata.tcc.transfer.dao.AccountRepository;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@Slf4j
@Service
public class TransferInActionImpl implements TransferInAction {

    @Resource AccountRepository accountRepository;

    @Resource TransactionTemplate transactionTemplate;

    @Override
    public boolean prepareAdd(int to, int amount) {
        final String xid = RootContext.getXID();

        final Boolean result = transactionTemplate.execute(status -> {
            final Optional<AccountDO> oAccount = accountRepository.findById(to);
            if (!oAccount.isPresent()) {
                throw new RuntimeException("Account not exists");
            }

            final AccountDO account = oAccount.get();
            account.setFreezedAmount(account.getFreezedAmount() + amount);
            accountRepository.save(account);

            log.info("Transfer-add, prepare, account:{}, amount:{}, xid:{}", account, amount, xid);

            return true;
        });

        return Optional.ofNullable(result).orElse(false);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        Integer to = (Integer) businessActionContext.getActionContext("to");
        Integer amount = (Integer) businessActionContext.getActionContext("amount");
        final String xid = businessActionContext.getXid();

        final Boolean result = transactionTemplate.execute(status -> {
            try {
                final Optional<AccountDO> oAccount = accountRepository.findById(to);
                Assert.isTrue(oAccount.isPresent());
                final AccountDO account = oAccount.get();
                Assert.isTrue(account.getFreezedAmount() >= amount);
                account.setFreezedAmount(account.getFreezedAmount() - amount);
                account.setBalance(account.getBalance() + amount);
                accountRepository.save(account);
                log.info("Transfer-add, commit, account:{}, amount:{}, xid:{}", account, amount,
                        xid);
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                status.setRollbackOnly();
                return false;
            }
        });

        return Optional.ofNullable(result).orElse(false);
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        Integer to = (Integer) businessActionContext.getActionContext("to");
        Integer amount = (Integer) businessActionContext.getActionContext("amount");
        final String xid = businessActionContext.getXid();

        final Boolean result = transactionTemplate.execute(status -> {
            try {
                final Optional<AccountDO> oAccount = accountRepository.findById(to);
                Assert.isTrue(oAccount.isPresent());
                final AccountDO account = oAccount.get();
                Assert.isTrue(account.getFreezedAmount() >= amount);
                account.setFreezedAmount(account.getFreezedAmount() - amount);
                accountRepository.save(account);
                log.info("Transfer-add, rollback, account:{}, amount:{}, xid:{}", account, amount,
                        xid);
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                status.setRollbackOnly();
                return false;
            }
        });

        return Optional.ofNullable(result).orElse(false);
    }
}
