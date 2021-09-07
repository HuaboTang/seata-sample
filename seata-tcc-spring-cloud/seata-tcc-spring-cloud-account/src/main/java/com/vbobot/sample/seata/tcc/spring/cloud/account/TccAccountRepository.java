package com.vbobot.sample.seata.tcc.spring.cloud.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Repository
public interface TccAccountRepository extends CrudRepository<TccAccountDO, Integer> {

    Optional<TccAccountDO> findFirstByAccountUserId(Integer accountUSerId);

    @Modifying
    @Query("update TccAccountDO set amount = amount - :accountUserId, freezeAmount = freezeAmount + :deductAmount "
            + "where accountUserId = :accountUserId")
    void prepareDeductAccount(@Param("accountUserId") Integer accountUserId,
            @Param("deductAmount") Integer deductAmount);
}
