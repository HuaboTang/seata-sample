package com.vbobot.sample.seata.at.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/7/29
 */
@Slf4j
public class RespExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String onException(Exception e) {
        log.error(e.getMessage(), e);
        return "onError";
    }
}
