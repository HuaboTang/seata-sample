package com.vbobot.sample.seata.at.account;

import com.vbobot.sample.seata.at.account.service.AccountService;
import com.vbobot.sample.seata.at.feign.AccountFeign;
import com.vbobot.sample.seata.at.feign.DeductBalanceParamDTO;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Slf4j
@RestController
public class AccountController implements AccountFeign {

    @Resource
    AccountService accountService;

    @Override
    public String commitDeductBalance(@RequestBody DeductBalanceParamDTO param) {
        accountService.commitDeductBalance(param);
        return "commit";
    }

    @Override
    public String rollbackDeductBalance(@RequestBody DeductBalanceParamDTO param) {
        accountService.rollbackDeductBalance(param);
        return "rollback";
    }
}
