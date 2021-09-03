package com.vbobot.simple.seata.tcc.spring.cloud.order.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Repository
public interface TccOrderRepository extends CrudRepository<TccOrderDO, Integer> {
}
