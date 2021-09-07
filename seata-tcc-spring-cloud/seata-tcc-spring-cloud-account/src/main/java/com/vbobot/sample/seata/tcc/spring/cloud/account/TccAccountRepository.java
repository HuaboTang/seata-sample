package com.vbobot.sample.seata.tcc.spring.cloud.account;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Repository
public interface TccAccountRepository extends CrudRepository<TccAccountDO, Integer> {

    Optional<TccAccountDO> findFirstByAccountUserId(Integer accountUSerId);
}
