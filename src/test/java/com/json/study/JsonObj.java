package com.json.study;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * @author xuedui.zhao
 * @create 2018-11-28
 */
public class JsonObj {

    @JSONField(ordinal = 1)
    private String orderNo;
    @JSONField(ordinal = 4)
    private BigDecimal amount;
    @JSONField(ordinal = 3)
    private String operator;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
