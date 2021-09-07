package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductStorageParamDTO;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.StorageFeign;
import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@RestController
@EnableJpaRepositories
@SpringBootApplication
public class SeataTccSpringCloudStorageApplication implements StorageFeign {
    @Resource DeductStorageAction storageAction;

    public static void main(String[] args) {
        SpringApplication.run(SeataTccSpringCloudStorageApplication.class, args);
    }

    @Override
    public String prepareDeductStorageForCommit(DeductStorageParamDTO param) {
        storageAction.prepareDeductStorage(null, param);
        return "prepare";
    }

    @Override
    public String prepareDeductStorageForRollback(DeductStorageParamDTO param) {
        storageAction.prepareDeductStorageRollback(null, param);
        throw new RuntimeException();
    }
}
