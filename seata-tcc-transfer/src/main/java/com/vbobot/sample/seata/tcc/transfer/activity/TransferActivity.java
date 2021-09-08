package com.vbobot.sample.seata.tcc.transfer.activity;

import com.vbobot.sample.seata.tcc.transfer.action.TransferInAction;
import com.vbobot.sample.seata.tcc.transfer.action.TransferOutAction;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@Slf4j
@Service
public class TransferActivity {

    @Resource
    TransferOutAction outAction;
    @Resource
    TransferInAction transferInAction;

    @GlobalTransactional
    public boolean transfer() {
        final String xid = RootContext.getXID();
        log.info("Transfer, xid:{}", xid);
        final int amount = 100;
        final int outAccountId = 1;
        final int to = 2;
        boolean prepareResult = outAction.prepareMinus(outAccountId, amount);
        boolean inPrepareResult = transferInAction.prepareAdd(to, amount);
        log.info("Transfer-prepare, out:{}, in:{}", prepareResult, inPrepareResult);
        return prepareResult && inPrepareResult;
    }

    @GlobalTransactional
    public boolean rollbackTransfer() {
        final String xid = RootContext.getXID();
        log.info("Transfer, rollback, xid:{}", xid);

        final int amount = 100;
        final int outAccountId = 1;
        final int to = 2;
        boolean prepareResult = outAction.prepareMinus(outAccountId, amount);
        boolean inPrepareResult = transferInAction.prepareAdd(to, amount);
        log.info("Transfer-prepare, out:{}, in:{}", prepareResult, inPrepareResult);
        throw new RuntimeException("rollback");
    }
}
