/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.queues;

import com.maksym.orderbook.domain.Message;

/**
 * There will be two implementations of the queue - based on standard queue and custom one
 */
public interface IQueue {
    public static final int DEFAULT_CAPACITY = 10000;

    public Message getMessage() throws Exception;
    public void addMessage(Message message) throws Exception;
}
