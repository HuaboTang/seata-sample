package com.vbobot.sample.seata.at.account;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@EnableJpaRepositories
@SpringBootApplication
public class SeataAtAccountApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SeataAtAccountApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
