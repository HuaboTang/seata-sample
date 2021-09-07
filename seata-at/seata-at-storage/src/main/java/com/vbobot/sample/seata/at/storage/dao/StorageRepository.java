package com.vbobot.sample.seata.at.storage.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Repository
public interface StorageRepository extends CrudRepository<StorageDO, Integer> {

    Optional<StorageDO> findByGoodsId(Integer goodsId);
}
