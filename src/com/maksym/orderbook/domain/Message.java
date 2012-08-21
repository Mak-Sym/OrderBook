package com.maksym.orderbook.domain;

import com.maksym.orderbook.Utils;

public abstract class Message {
    private long timestamp;
    private char messageType;
    private String orderId;
    private int size;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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
        message.setTimestamp(Long.parseLong(parts[0]));
        message.setOrderId(parts[2]);
        return message;
    }

    public static Message createMessage(char[] buff){
        Message message;
        //skipping the timestamp
        int currentPosition = Utils.findNextAlpha(buff, Utils.findNextWhitespace(buff, 0));
        int nextPosition = Utils.findNextWhitespace(buff, currentPosition);

        if(buff[currentPosition] == 'A'){
            message = new AddOrderMessage();
            message.setMessageType('A'); //[1]

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            message.setOrderId(String.copyValueOf(buff, currentPosition, nextPosition - currentPosition));//[2]

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            ((AddOrderMessage)message).setSide(buff[currentPosition]);//[3]

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            ((AddOrderMessage)message).setPrice(
                    Double.parseDouble(
                            String.copyValueOf(buff, currentPosition, nextPosition - currentPosition)
                    ));//[4]

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            message.setSize(Integer.parseInt(
                    String.copyValueOf(buff, currentPosition, nextPosition - currentPosition)
            ));//[5]

        } else {
            message = new ReduceOrderMessage();
            message.setMessageType('R');

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            message.setOrderId(String.copyValueOf(buff, currentPosition, nextPosition - currentPosition));//[2]

            currentPosition = Utils.findNextAlpha(buff, nextPosition);
            nextPosition = Utils.findNextWhitespace(buff, currentPosition);
            message.setSize(Integer.parseInt(
                    String.copyValueOf(buff, currentPosition, nextPosition - currentPosition)
            ));//[3]
        }

        return message;
    }
}
