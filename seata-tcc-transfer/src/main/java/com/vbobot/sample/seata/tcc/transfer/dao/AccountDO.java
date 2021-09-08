package com.vbobot.sample.seata.tcc.transfer.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Bobo
 * @date 2021/7/30
 */
@Data
@Entity
@Table(name = "tcc_account")
public class AccountDO {

    @Id
    private Integer id;
    private Integer balance;
    private Integer freezedAmount;
}
