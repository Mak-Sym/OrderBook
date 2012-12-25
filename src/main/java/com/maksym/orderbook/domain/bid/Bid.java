/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.domain.bid;

import com.maksym.orderbook.domain.OrderBean;

import java.math.BigDecimal;

/**
 * @author mfedoryshyn
 */
public class Bid implements OrderBean {
    private String orderId;
    private BigDecimal price;
    private int size;

    public Bid(String orderId, BigDecimal price, int size) {
        this.orderId = orderId;
        this.price = price;
        this.size = size;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Bid bid = (Bid) o;

        return orderId.equals(bid.orderId);

    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }
}
