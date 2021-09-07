package com.vbobot.sample.seata.at.feign;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Data
public class DeductBalanceParamDTO {
    private Integer accountUserId;
    private Integer deductValue;
}
