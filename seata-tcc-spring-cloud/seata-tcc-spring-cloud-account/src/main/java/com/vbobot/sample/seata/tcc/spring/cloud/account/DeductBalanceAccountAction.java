package com.vbobot.sample.seata.tcc.spring.cloud.account;

import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductBalanceParamDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@LocalTCC
public interface DeductBalanceAccountAction {

    @TwoPhaseBusinessAction(name = "deductBalanceAccountAction",
            rollbackMethod = "rollback",
            commitMethod = "commit")
    void prepareDeductBalance(BusinessActionContext businessActionContext,
            @BusinessActionContextParameter(paramName = "deductBalanceParam") DeductBalanceParamDTO param);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
