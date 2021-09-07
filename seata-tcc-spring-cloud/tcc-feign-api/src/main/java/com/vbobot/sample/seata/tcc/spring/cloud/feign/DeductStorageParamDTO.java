package com.vbobot.sample.seata.tcc.spring.cloud.feign;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Data
public class DeductStorageParamDTO {
    private Integer goodsId;
    private Integer accountUserId;
    private Integer deductStorage;
}
