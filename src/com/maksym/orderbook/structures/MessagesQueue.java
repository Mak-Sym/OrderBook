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

    public synchronized Message getMessage(long index){
        Message message = this.messages[(int)(index % capacity)];
        readWriteArbiter.setLastReadPosition(index);
        return message;
    }

    public synchronized void addMessage(Message message){
        messages[(int)(++nextWritePosition % capacity)] = message;
    }

    public synchronized int getCountOfFreeSlots(){
        return readWriteArbiter.getNumberOfFreeSlots(nextWritePosition - 1);
    }

    public synchronized long getNextWritePosition(){
        return nextWritePosition;
    }
}
