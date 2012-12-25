/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.queues;

/**
 * There will be several implementations of the queue - based on standard queue and custom one.
 * For some reason I think that queue performance is very important (I may be wrong)
 */
public interface IQueue <T> {
    public static final int DEFAULT_CAPACITY = 10000;

    public T getMessage() throws Exception;
    public void addMessage(T message) throws Exception;
    public boolean isFull();
    public boolean isEmpty();
}
