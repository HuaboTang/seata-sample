package com.vbobot.sample.seata.tcc.spring.cloud.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.util.Assert;

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

    public void prepareDeduct(Integer deductStock) {
        Assert.isTrue(this.stock > deductStock);
        this.stock -= deductStock;
        this.freezeStock += deductStock;
    }

    public void commitDeduct(Integer deductStock) {
        Assert.isTrue(this.freezeStock >= deductStock, "freezeStock less than deductStock");
        this.freezeStock -= deductStock;
    }

    public void rollbackDeduct(Integer deductStock) {
        Assert.isTrue(this.freezeStock >= deductStock, "freezeStock less than deductStock");
        this.freezeStock -= deductStock;
        this.stock += deductStock;
    }
}
