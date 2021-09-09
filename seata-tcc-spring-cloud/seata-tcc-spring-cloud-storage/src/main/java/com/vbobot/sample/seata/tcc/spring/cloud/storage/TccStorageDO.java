package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Bobo
 * @date 2021/9/2
 */
@Data
@Entity
@Table(name = "tcc_storage")
public class TccStorageDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer goodsId;

    @Column
    private Integer stock;

    @Column
    private Integer freezeStock;
}
