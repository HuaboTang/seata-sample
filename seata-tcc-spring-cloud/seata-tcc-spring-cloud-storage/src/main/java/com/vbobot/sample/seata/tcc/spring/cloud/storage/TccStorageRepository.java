package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Repository
public interface TccStorageRepository extends CrudRepository<TccStorageDO, Integer> {

    Optional<TccStorageDO> findFirstByGoodsId(Integer goodsId);
}
