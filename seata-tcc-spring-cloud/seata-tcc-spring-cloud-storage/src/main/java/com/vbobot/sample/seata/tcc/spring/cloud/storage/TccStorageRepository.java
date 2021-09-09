package com.vbobot.sample.seata.tcc.spring.cloud.storage;

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
public interface TccStorageRepository extends CrudRepository<TccStorageDO, Integer> {

    Optional<TccStorageDO> findFirstByGoodsId(Integer goodsId);

    @Modifying
    @Query("update TccStorageDO set freezeStock = freezeStock + :reduceAmount, stock = stock - :reduceAmount where goodsId = :goodsId")
    void prepareReduce(@Param("goodsId") Integer goodsId, @Param("reduceAmount") Integer reduceAmount);

    @Modifying
    @Query("update TccStorageDO set freezeStock = freezeStock - :reduceAmount where goodsId = :goodsId")
    void commitReduce(@Param("goodsId") Integer goodsId, @Param("reduceAmount") Integer reduceAmount);

    @Modifying
    @Query("update TccStorageDO set freezeStock = freezeStock - :reduceAmount, stock = stock + :reduceAmount where goodsId = :goodsId")
    void rollbackReduce(@Param("goodsId") Integer goodsId, @Param("reduceAmount") Integer reduceAmount);
}
