package com.vbobot.sample.seata.tcc.spring.cloud.order.dao;

import com.vbobot.sample.seata.tcc.spring.cloud.order.dto.CreateOrderParamDTO;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Bobo
 * @date 2021/8/2
 */
@Data
@Entity
@Table(name = "tcc_order")
public class TccOrderDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer goodsId;
    private Integer amount;
    private Integer accountId;
    private Date createTime;

    /**
     * 状态：1=prepare; 2=commit; 3=rollback
     */
    private Integer status;

    public TccOrderDO() {
    }

    public TccOrderDO(CreateOrderParamDTO param) {
        this.goodsId = param.getGoodsId();
        this.amount = param.getAmount();
        this.accountId = param.getAccountId();
        this.status = 1;
        this.createTime = new Date();
    }
}
