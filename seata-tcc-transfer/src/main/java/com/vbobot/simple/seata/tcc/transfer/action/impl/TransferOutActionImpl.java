package com.vbobot.sample.seata.tcc.transfer.action.impl;

import com.vbobot.sample.seata.tcc.transfer.action.TransferOutAction;
import com.vbobot.sample.seata.tcc.transfer.dao.AccountDO;
import com.vbobot.sample.seata.tcc.transfer.dao.AccountRepository;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@Slf4j
@Service
public class TransferOutActionImpl implements TransferOutAction {

    @Resource TransactionTemplate transactionTemplate;

    @Resource AccountRepository accountRepository;

    @Override
    public boolean prepareMinus(int outAccountId, int amount) {
        final String xid = RootContext.getXID();

        final Boolean result = transactionTemplate.execute(status -> {
            final Optional<AccountDO> oAccount = accountRepository.findById(outAccountId);
            if (!oAccount.isPresent()) {
                throw new RuntimeException("Account not exists");
            }

            final AccountDO account = oAccount.get();
            if (account.getBalance() < amount) {
                throw new RuntimeException("用户余额不足");
            }

            account.setFreezedAmount(
                    Optional.ofNullable(account.getFreezedAmount()).orElse(0) + amount);
            accountRepository.save(account);
            log.info("Prepare-minus, account:{}, amount:{}, xid:{}", outAccountId, amount, xid);
            return true;
        });
        return Optional.ofNullable(result).orElse(false);
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        final String xid = businessActionContext.getXid();

        final Integer accountId = NumberUtils.toInt(String.valueOf(businessActionContext.getActionContext("outAccountId")));
        final int amount = NumberUtils.toInt(String.valueOf(businessActionContext.getActionContext("amount")));

        final Optional<AccountDO> oAccount = accountRepository.findById(accountId);
        if (!oAccount.isPresent()) {
            throw new RuntimeException("Account not exists");
        }

        final AccountDO account = oAccount.get();
        if (account.getBalance() < amount) {
            throw new RuntimeException("用户余额不足");
        }
        account.setBalance(account.getBalance() - amount);
        account.setFreezedAmount(account.getFreezedAmount() - amount);
        accountRepository.save(account);

        log.info("Transfer-minus, commit, xid:{}", xid);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        final String xid = businessActionContext.getXid();

        final Integer accountId = NumberUtils.toInt(String.valueOf(businessActionContext.getActionContext("outAccountId")));
        final int amount = NumberUtils.toInt(String.valueOf(businessActionContext.getActionContext("amount")));

        final Optional<AccountDO> oAccount = accountRepository.findById(accountId);
        if (!oAccount.isPresent()) {
            throw new RuntimeException("Account not exists");
        }

        final AccountDO account = oAccount.get();
        account.setFreezedAmount(account.getFreezedAmount() - amount);
        accountRepository.save(account);

        log.info("Transfer-minus, rollback, xid:{}, account:{}", xid, accountId);
        return true;
    }
}
