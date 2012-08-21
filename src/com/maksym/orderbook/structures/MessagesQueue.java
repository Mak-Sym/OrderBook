package com.maksym.orderbook.structures;

import com.maksym.orderbook.domain.Message;

public class MessagesQueue {
    private static final int DEFAULT_CAPACITY = 10000;

    private volatile long nextWritePosition;
    private ReadWriteArbiter readWriteArbiter;
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
        readWriteArbiter = new ReadWriteArbiter(capacity);
    }

    public Message getMessage(long index){
        readWriteArbiter.setLastReadPosition(index);
        return this.messages[(int)(index % capacity)];
    }

    public void addMessage(Message message){
        messages[(int)(++nextWritePosition % capacity)] = message;
    }

    public int getCountOfFreeSlots(){
        return readWriteArbiter.getNumberOfFreeSlots(nextWritePosition - 1);
    }

    public long getNextWritePosition(){
        return nextWritePosition;
    }
}
