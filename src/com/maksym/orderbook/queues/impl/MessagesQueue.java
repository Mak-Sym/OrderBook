package com.maksym.orderbook.queues.impl;

import com.maksym.orderbook.queues.IQueue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessagesQueue<T> implements IQueue<T> {

    private volatile long nextWritePosition;
    private ReadWriteArbiter readWriteArbiter;
    private int capacity;
    private long index;

    private Lock readLock;
    private Lock writeLock;

    private Object[] messages;

    public MessagesQueue() {
        this(DEFAULT_CAPACITY);
    }

    public MessagesQueue(int capacity) {
        this.nextWritePosition = 0;
        if(capacity < 1){
            throw new IllegalArgumentException("capacity is less than 1");
        }
        this.capacity = capacity;
        this.messages = new Object[capacity];
        readWriteArbiter = new ReadWriteArbiter(capacity);

        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
    }

    /**
     * returns message. Used by *consumer* thread
     * @return message
     */
    @Override
    public T getMessage(){
        readLock.lock();
        T message;
        try {
            message = getMessage(index);
            ++index;
        } finally {
            readLock.unlock();
        }
        return message;
    }

    /**
     * returns message. Used by *consumer* thread
     * @param index of the message
     * @return message
     */
    protected T getMessage(long index){
        T message;
        message = (T)this.messages[(int)(index % capacity)];
        readWriteArbiter.setLastReadPosition(index);
        return message;
    }

    /**
     * add message to queue. Used by *producer*
     * @param message to be added
     */
    @Override
    public void addMessage(T message){
        writeLock.lock();
        try {
            messages[(int)(nextWritePosition % capacity)] = message;
            ++nextWritePosition;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        return this.getCountOfFreeSlots() < 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getCountOfFreeSlots() >= messages.length;
    }

    /**
     * get number of free slots. Used by *producer*
     * @return number of free slots
     */
    public int getCountOfFreeSlots(){
        readLock.lock();
        try {
            return readWriteArbiter.getNumberOfFreeSlots(nextWritePosition - 1);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * get next write position. Used by *consumer* to determine if there are unread messages.
     * @return next write position.
     */
    public long getNextWritePosition(){
        writeLock.lock();
        try {
            return nextWritePosition;
        } finally {
            writeLock.unlock();
        }
    }
}
