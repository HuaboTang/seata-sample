package com.vbobot.simple.seata.tcc.spring.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@FeignClient(name = "seata-tcc-spring-cloud-account", url = "http://127.0.0.1:8081")
public interface AccountFeign {
    @PostMapping("/prepare/deduct/balance")
    void prepareDeductBalance(@RequestBody DeductBalanceParamDTO param);
}
