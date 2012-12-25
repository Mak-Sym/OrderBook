package com.maksym.orderbook.utils;

import com.maksym.orderbook.domain.ask.Ask;
import com.maksym.orderbook.domain.bid.Bid;
import com.maksym.orderbook.domain.message.AddOrderMessage;
import com.maksym.orderbook.domain.message.OrderMessage;

/**
 * @author mfedoryshyn
 */
public class OrderBookHelper {

    public static Bid convertToBid(OrderMessage message){
        if(message.getMessageType() == 'R'){
            return new Bid(message.getOrderId(), null, message.getSize());
        } else {
            AddOrderMessage addOrderMessage = (AddOrderMessage)message;
            if(addOrderMessage.getSide() != 'B'){
                throw new IllegalArgumentException("Wrong message side!");
            }
            return new Bid(addOrderMessage.getOrderId(), addOrderMessage.getPrice(), addOrderMessage.getSize());
        }
    }

    public static Ask convertToAsk(OrderMessage message){
        if(message.getMessageType() == 'R'){
            return new Ask(message.getOrderId(), null, message.getSize());
        } else {
            AddOrderMessage addOrderMessage = (AddOrderMessage)message;
            if(addOrderMessage.getSide() != 'S'){
                throw new IllegalArgumentException("Wrong message side!");
            }
            return new Ask(addOrderMessage.getOrderId(), addOrderMessage.getPrice(), addOrderMessage.getSize());
        }
    }
}
