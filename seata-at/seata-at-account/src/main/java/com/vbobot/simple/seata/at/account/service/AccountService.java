package com.vbobot.simple.seata.at.account.service;

import com.vbobot.simple.seata.at.account.dao.AccountDO;
import com.vbobot.simple.seata.at.account.dao.AccountRepository;
import com.vbobot.simple.seata.at.feign.DeductBalanceParamDTO;
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
public class AccountService {

    @Resource
    AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public void commitDeductBalance(DeductBalanceParamDTO param) {
        final Integer accountUserId = param.getAccountUserId();
        final Optional<AccountDO> oAccount = accountRepository.findByAccountUserId(accountUserId);
        final AccountDO account;
        if (oAccount.isPresent()) {
            account = oAccount.get();
            account.setBalance(account.getBalance() - param.getDeductValue());
        } else {
            account = new AccountDO();
            account.setAccountUserId(accountUserId);
            account.setBalance(1000 - param.getDeductValue());
        }

        final AccountDO save = accountRepository.save(account);
        log.info("=====>Deduct-account-balance, param:{}, account:{}", param, save);
    }

    @Transactional(rollbackFor = Exception.class)
    public void rollbackDeductBalance(DeductBalanceParamDTO param) {
        final Integer accountUserId = param.getAccountUserId();
        final Optional<AccountDO> oAccount = accountRepository.findById(accountUserId);
        final AccountDO account;
        if (oAccount.isPresent()) {
            account = oAccount.get();
            account.setBalance(account.getBalance() - param.getDeductValue());
        } else {
            account = new AccountDO();
            account.setId(param.getAccountUserId());
            account.setBalance(1000 - param.getDeductValue());
        }

        final AccountDO save = accountRepository.save(account);
        log.info("=====>Rollback-account-balance, param:{}, account:{}", param, save);
        throw new RuntimeException("1");
    }
}
