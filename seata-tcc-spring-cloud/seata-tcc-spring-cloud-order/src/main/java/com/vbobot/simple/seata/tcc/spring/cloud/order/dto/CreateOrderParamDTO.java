package com.vbobot.simple.seata.tcc.spring.cloud.order.dto;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Data
public class CreateOrderParamDTO {
    private Integer goodsId;
    private Integer accountId;
    private Integer amount;
}
