package com.maksym.orderbook.structures;

import com.maksym.orderbook.domain.Message;

public class MessagesQueue {
    private static final int DEFAULT_CAPACITY = 10000;

    private volatile long nextWritePosition;
    private int capacity;

    private Message[] messages;

    public MessagesQueue() {
        this(DEFAULT_CAPACITY);
    }

    public MessagesQueue(int capacity) {
        this.nextWritePosition = 0;
        if(capacity < 1){
            throw new IllegalArgumentException("capacity is less than 1");
        }
        this.capacity = capacity;
        this.messages = new Message[capacity];
    }

    public Message getMessage(int index){
        return this.messages[index];
    }

    public void addMessage(Message message){
        messages[(int)(nextWritePosition % capacity)] = message;
        ++nextWritePosition;
    }
}
