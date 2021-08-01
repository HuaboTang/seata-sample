package com.vbobot.simple.seata.tcc.transfer;

import com.vbobot.simple.seata.tcc.transfer.activity.TransferActivity;
import javax.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@EnableJpaRepositories
@RestController
@SpringBootApplication
public class SeataTccTransferApplication {
    @Resource TransferActivity transferActivity;

    public static void main(String[] args) {
        SpringApplication.run(SeataTccTransferApplication.class, args);
    }

    @GetMapping("/transfer")
    public String transfer() {
        transferActivity.transfer();
        return "success";
    }

    @GetMapping("/transfer/rollbak")
    public String rollbackTransfer() {
        transferActivity.rollbackTransfer();
        return "rollback";
    }
}
