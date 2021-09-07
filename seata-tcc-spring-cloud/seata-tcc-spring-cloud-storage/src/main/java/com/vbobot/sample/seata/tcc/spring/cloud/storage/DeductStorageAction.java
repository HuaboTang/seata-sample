package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductStorageParamDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@LocalTCC
public interface DeductStorageAction {

    @TwoPhaseBusinessAction(name = "deductStorage", commitMethod = "commit", rollbackMethod = "rollback")
    void prepareDeductStorage(BusinessActionContext context,
            @BusinessActionContextParameter(paramName = "deductStorageParam") DeductStorageParamDTO param);

    @TwoPhaseBusinessAction(name = "deductStorageRollback", commitMethod = "commit", rollbackMethod = "rollback")
    void prepareDeductStorageRollback(BusinessActionContext context,
            @BusinessActionContextParameter(paramName = "deductStorageParam") DeductStorageParamDTO param);

    Boolean commit(BusinessActionContext context);

    Boolean rollback(BusinessActionContext context);
}
