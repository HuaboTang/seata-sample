package com.vbobot.sample.seata.at.order.dao;

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
@Entity
@Table(name = "orders")
@Data
public class OrderDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer accountUserId;

    private Integer goodsId;

    private Integer number;

    private Integer amount;

    private Date createTime;
}
