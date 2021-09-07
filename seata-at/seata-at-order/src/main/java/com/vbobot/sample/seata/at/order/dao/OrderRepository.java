package com.vbobot.sample.seata.at.order.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDO, Integer> {
}
