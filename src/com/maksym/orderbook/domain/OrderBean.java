package com.maksym.orderbook.domain;

import java.math.BigDecimal;

/**
 * @author mfedoryshyn
 */
public interface OrderBean {

    public String getOrderId();

    public BigDecimal getPrice();

    public int getSize();

    public void setSize(int size);
}
