package com.vbobot.simple.seata.at.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@FeignClient(name = "seata-at-storage", url = "http://127.0.0.1:8082")
public interface StorageFeign {
    @PostMapping("/storage/deduct/storage/commit")
    String commitDeductStorage(@RequestBody DeductStorageParamDTO param);

    @PostMapping("/storage/deduct/storage/rollback")
    String rollbackDeductStorage(@RequestBody DeductStorageParamDTO param);
}
