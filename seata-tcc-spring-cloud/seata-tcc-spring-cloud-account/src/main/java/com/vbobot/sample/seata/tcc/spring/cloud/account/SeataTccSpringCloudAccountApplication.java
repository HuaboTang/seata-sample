package com.vbobot.sample.seata.tcc.spring.cloud.account;

import com.vbobot.sample.seata.tcc.spring.cloud.feign.AccountFeign;
import com.vbobot.sample.seata.tcc.spring.cloud.feign.DeductBalanceParamDTO;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Slf4j
@RestController
@EnableJpaRepositories
@SpringBootApplication
public class SeataTccSpringCloudAccountApplication implements AccountFeign {

    public static void main(String[] args) {
        SpringApplication.run(SeataTccSpringCloudAccountApplication.class, args);
    }

    @Resource DeductBalanceAccountAction deductBalanceAccountAction;

    @Override
    public void prepareDeductBalance(DeductBalanceParamDTO param) {
//        log.info("=====>PrepareDeductBalance, xid:{}", RootContext.getXID());
        deductBalanceAccountAction.prepareDeductBalance(null, param);

//        final BusinessActionContext businessActionContext = new BusinessActionContext();
//        final HashMap<String, Object> map = Maps.newHashMap();
//        map.put("deductBalanceParam", JSONObject.toJSON(param));
//        businessActionContext.setActionContext(map);
//        deductBalanceAccountAction.commit(businessActionContext);
    }
}
