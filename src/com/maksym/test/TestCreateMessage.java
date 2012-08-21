package com.maksym.test;

import com.maksym.orderbook.domain.AddOrderMessage;
import com.maksym.orderbook.domain.Message;
import com.maksym.orderbook.domain.ReduceOrderMessage;

/**
 * @author mfedoryshyn
 */
public class TestCreateMessage {
    public static void main(String[] args){
        char[] testInput_1 = new String("1234546767975 A 123456 S 127.99 567").toCharArray();
        char[] testInput_2 = new String("213245433 R 987654 2258").toCharArray();

        Message message = Message.createMessage(testInput_1);
        if(! (message instanceof AddOrderMessage)){
            throw new RuntimeException("Invalid Message Type");
        }

        AddOrderMessage addOrderMessage = (AddOrderMessage)message;
        if(addOrderMessage.getMessageType() != 'A'){
            throw new RuntimeException("Invalid Type!");
        }
        if(!"123456".equals(addOrderMessage.getOrderId())){
            throw new RuntimeException("Invalid Order Id!");
        }
        if(addOrderMessage.getSide() != 'S'){
            throw new RuntimeException("Invalid Side!");
        }
        if(addOrderMessage.getPrice() != 127.99){
            throw new RuntimeException("Invalid Price!");
        }
        if(addOrderMessage.getSize() != 567){
            throw new RuntimeException("Invalid Size!");
        }

        message = Message.createMessage(testInput_2);
        if(! (message instanceof ReduceOrderMessage)){
            throw new RuntimeException("Invalid Message Type");
        }

        ReduceOrderMessage reduceOrderMessage = (ReduceOrderMessage)message;
        if(reduceOrderMessage.getMessageType() != 'R'){
            throw new RuntimeException("Invalid Type!");
        }
        if(!"987654".equals(reduceOrderMessage.getOrderId())){
            throw new RuntimeException("Invalid Order Id!");
        }
        if(reduceOrderMessage.getSize() != 2258){
            throw new RuntimeException("Invalid Size!");
        }
    }
}
