package com.chryl.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Chr.yl on 2021/4/30.
 *
 * @author Chr.yl
 */
@Data
public class GoodsPo implements Serializable {
    private static final long serialVersionUID = -4027895522053775138L;
    private Long goodsId;

    private String goodsName;

    private BigDecimal goodsPrice;

    private int num;

    public GoodsPo() {
    }

    public GoodsPo(Long goodsId, String goodsName, BigDecimal goodsPrice, int num) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.num = num;
    }

    public GoodsPo(Long goodsId, String goodsName) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
    }
}
