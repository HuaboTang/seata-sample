package com.vbobot.simple.seata.at.storage.dao;

import java.util.Date;
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
@Data
@Entity
@Table(name = "storage_record")
public class StorageRecordDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer changeStorage;
    private Date recordTime;
    private Integer goodsId;
}
