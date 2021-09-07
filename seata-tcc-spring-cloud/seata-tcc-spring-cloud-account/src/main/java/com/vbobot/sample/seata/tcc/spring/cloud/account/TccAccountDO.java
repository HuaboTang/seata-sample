package com.vbobot.sample.seata.tcc.spring.cloud.account;

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
@Table(name = "tcc_account")
public class TccAccountDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer accountUserId;

    @Column
    private Integer amount;

    @Column
    private Integer freezeAmount;

    public void prepareDeduct(Integer deductAmount) {
        Assert.isTrue(getAmount() > deductAmount, "用户余额不足");
        this.amount -= deductAmount;
        this.freezeAmount += deductAmount;
    }

    public void commitDeduct(Integer deductAmount) {
        Assert.isTrue(this.freezeAmount >= deductAmount, "回滚失败，冻结金额小于待回滚金额");
        this.freezeAmount -= deductAmount;
    }

    public void rollbackDeduct(Integer deductAmount) {
        Assert.isTrue(this.freezeAmount >= deductAmount, "回滚失败，冻结金额小于待回滚金额");
        this.freezeAmount -= deductAmount;
        this.amount += deductAmount;
    }
}
