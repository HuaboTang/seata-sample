package com.vbobot.sample.seata.tcc.transfer.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@LocalTCC
public interface TransferOutAction {

    @TwoPhaseBusinessAction(name = "transferOutAction", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepareMinus(@BusinessActionContextParameter(paramName = "outAccountId") int outAccountId,
            @BusinessActionContextParameter(paramName = "amount") int amount);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
