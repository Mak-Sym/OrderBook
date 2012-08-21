package com.maksym.orderbook.domain;

public abstract class Message {
    private String timestamp;
    private char messageType;
    private String orderId;
    private int size;

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

    public static Message createMessage(String line){
        String[] parts = line.split(" ");
        Message message;
        if("A".equals(parts[1])){
            message = new AddOrderMessage();
            message.setMessageType('A');
            ((AddOrderMessage)message).setSide(parts[3].charAt(0));
            ((AddOrderMessage)message).setPrice(Double.parseDouble(parts[4]));
            message.setSize(Integer.parseInt(parts[5]));
        } else {
            message = new ReduceOrderMessage();
            message.setMessageType('R');
            message.setSize(Integer.parseInt(parts[3]));
        }
        message.setTimestamp(parts[0]);
        message.setOrderId(parts[2]);
        return message;
    }
}
