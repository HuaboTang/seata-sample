package com.vbobot.sample.seata.at.storage.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Entity
@Table(name = "storage")
@Data
public class StorageDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer goodsId;

    private Integer storage;
}
