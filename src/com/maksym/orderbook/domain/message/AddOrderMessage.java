package com.maksym.orderbook.domain.message;

import java.math.BigDecimal;

public class AddOrderMessage extends OrderMessage {
    char side;
    BigDecimal price;

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
