/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.queues.impl;

import com.maksym.orderbook.queues.IQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This implementation is based on standard ArrayBlockingQueue
 */
public class StandardBlockingQueue<T> implements IQueue<T> {

    private BlockingQueue<T> queue;

    public StandardBlockingQueue(){
        this(DEFAULT_CAPACITY);
    }

    public StandardBlockingQueue(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException("capacity is less than 1");
        }
        queue = new LinkedBlockingQueue<T>(capacity);//linked queue is one of the best choices here
    }

    @Override
    public T getMessage() throws Exception {
        return queue.take();
    }

    @Override
    public void addMessage(T message) throws Exception {
        if(message != null){
            queue.put(message);
        }
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
