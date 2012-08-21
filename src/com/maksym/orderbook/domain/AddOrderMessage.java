package com.maksym.orderbook.domain;

public class AddOrderMessage extends Message {
    char side;
    double price;

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
