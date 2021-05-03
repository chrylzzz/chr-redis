package com.chryl.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Chr.yl on 2021/4/30.
 *
 * @author Chr.yl
 */
@Data
public class OrderPo implements Serializable {
    private static final long serialVersionUID = -3994040451667998404L;

    private Long orderId;

    private BigDecimal orderPrice;

    private Long userId;

    private List<GoodsPo> goodsPoList;

    public OrderPo() {
    }

    public OrderPo(Long orderId, BigDecimal orderPrice, Long userId, List<GoodsPo> goodsPoList) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.userId = userId;
        this.goodsPoList = goodsPoList;
    }
}
