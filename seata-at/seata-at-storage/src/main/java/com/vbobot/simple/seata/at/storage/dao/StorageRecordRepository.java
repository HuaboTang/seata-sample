package com.vbobot.simple.seata.at.storage.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Repository
public interface StorageRecordRepository extends CrudRepository<StorageRecordDO, Integer> {
}
