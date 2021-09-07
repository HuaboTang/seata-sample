package com.vbobot.sample.seata.at.storage;

import com.vbobot.sample.seata.at.feign.DeductStorageParamDTO;
import com.vbobot.sample.seata.at.feign.StorageFeign;
import com.vbobot.sample.seata.at.storage.service.StorageService;
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
public class StorageController implements StorageFeign {

    @Resource
    StorageService storageService;

    @Override
    public String commitDeductStorage(@RequestBody DeductStorageParamDTO param) {
        storageService.commitDeductStorage(param);
        return "commit";
    }

    @Override
    public String rollbackDeductStorage(@RequestBody DeductStorageParamDTO param) {
        storageService.rollbackDeductStorage(param);
        return "rollback";
    }
}
