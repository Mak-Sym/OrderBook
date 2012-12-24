package com.maksym.orderbook.domain.message;

import java.math.BigDecimal;

public abstract class OrderMessage {
    protected String timestamp;
    protected char messageType;
    protected String orderId;
    protected int size;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public char getMessageType() {
        return messageType;
    }

    public void setMessageType(char messageType) {
        this.messageType = messageType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static OrderMessage createMessage(String line){
        String[] parts = line.split(" ");
        OrderMessage orderMessage;
        if("A".equals(parts[1])){
            orderMessage = new AddOrderMessage();
            orderMessage.setMessageType('A');
            ((AddOrderMessage) orderMessage).setSide(parts[3].charAt(0));
            ((AddOrderMessage) orderMessage).setPrice(new BigDecimal(parts[4]));
            orderMessage.setSize(Integer.parseInt(parts[5]));
        } else {
            orderMessage = new ReduceOrderMessage();
            orderMessage.setMessageType('R');
            orderMessage.setSize(Integer.parseInt(parts[3]));
        }
        orderMessage.setTimestamp(parts[0]);
        orderMessage.setOrderId(parts[2]);
        return orderMessage;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
               "timestamp='" + timestamp + '\'' +
               ", messageType=" + messageType +
               ", orderId='" + orderId + '\'' +
               ", size=" + size +
               '}';
    }
}
