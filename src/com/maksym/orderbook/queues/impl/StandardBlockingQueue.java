/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.queues.impl;

import com.maksym.orderbook.domain.Message;
import com.maksym.orderbook.queues.IQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This implementation is based on standard ArrayBlockingQueue
 */
public class StandardBlockingQueue implements IQueue {

    private BlockingQueue<Message> queue;

    public StandardBlockingQueue(){
        this(DEFAULT_CAPACITY);
    }

    public StandardBlockingQueue(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException("capacity is less than 1");
        }
        queue = new LinkedBlockingQueue<Message>(capacity);//linked queue is one of the best choices here
    }

    @Override
    public Message getMessage() throws Exception {
        return queue.take();
    }

    @Override
    public void addMessage(Message message) throws Exception {
        if(message != null){
            queue.put(message);
        }
    }
}
