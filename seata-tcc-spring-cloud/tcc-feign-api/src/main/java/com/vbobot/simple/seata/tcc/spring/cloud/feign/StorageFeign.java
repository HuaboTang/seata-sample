package com.vbobot.simple.seata.tcc.spring.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@FeignClient(name = "seata-tcc-spring-cloud-storage", url = "http://127.0.0.1:8082")
public interface StorageFeign {
    @PostMapping("/storage/prepare/deduct/storage/commit")
    String prepareDeductStorageForCommit(@RequestBody DeductStorageParamDTO param);

    @PostMapping("/storage/prepare/deduct/storage/rollback")
    String prepareDeductStorageForRollback(@RequestBody DeductStorageParamDTO param);
}
