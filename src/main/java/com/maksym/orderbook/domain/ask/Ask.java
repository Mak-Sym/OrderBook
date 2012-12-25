package com.maksym.orderbook.domain.ask;

import com.maksym.orderbook.domain.OrderBean;

import java.math.BigDecimal;

/**
 * @author mfedoryshyn
 */
public class Ask implements OrderBean {
    private String orderId;
    private BigDecimal price;
    private int size;

    public Ask(String orderId, BigDecimal price, int size) {
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

        Ask ask = (Ask) o;

        return orderId.equals(ask.orderId);

    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }
}
