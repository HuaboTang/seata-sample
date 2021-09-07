package com.vbobot.sample.seata.at.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@FeignClient(name = "seata-at-account", url = "http://127.0.0.1:8081")
public interface AccountFeign {
    @PostMapping("/account/deduct/balance/commit")
    String commitDeductBalance(@RequestBody DeductBalanceParamDTO param);

    @PostMapping("/account/deduct/balance/rollback")
    String rollbackDeductBalance(@RequestBody DeductBalanceParamDTO param);
}
