package com.maksym.orderbook.domain.message;

public class ReduceOrderMessage extends OrderMessage {
    @Override
    public String toString(){
        return "ReduceOrderMessage: " + super.toString();
    }
}
