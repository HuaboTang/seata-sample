package com.vbobot.simple.seata.at.account.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountDO, Integer> {

    Optional<AccountDO> findByAccountUserId(Integer accountUserId);
}
