package com.vbobot.simple.seata.tcc.spring.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@EnableFeignClients(basePackages = "com.vbobot.simple.seata.tcc.spring.cloud")
@EnableJpaRepositories
@SpringBootApplication
public class SeataTccSpringCloudOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataTccSpringCloudOrderApplication.class, args);
    }
}
