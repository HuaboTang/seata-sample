package com.vbobot.sample.seata.at.storage;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.vbobot.sample.seata.at")
public class SeataAtStorageApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SeataAtStorageApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
