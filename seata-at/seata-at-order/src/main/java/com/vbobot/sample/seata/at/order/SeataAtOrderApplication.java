package com.vbobot.sample.seata.at.order;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@EnableFeignClients(basePackages = "com.vbobot.sample.seata.at.feign")
@SpringBootApplication
public class SeataAtOrderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SeataAtOrderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
