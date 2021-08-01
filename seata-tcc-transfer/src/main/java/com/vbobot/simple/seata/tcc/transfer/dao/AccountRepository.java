package com.vbobot.simple.seata.tcc.transfer.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountDO, Integer> {
}
