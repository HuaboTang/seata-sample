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
public interface TransferInAction {

    @TwoPhaseBusinessAction(name = "transferInAction", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepareAdd(@BusinessActionContextParameter(paramName = "to") int to,
            @BusinessActionContextParameter(paramName = "amount") int amount);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
