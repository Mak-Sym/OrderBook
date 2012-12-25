package com.maksym.orderbook.queues.impl;

/**
 * @author mfedoryshyn
 */
public class ReadWriteArbiter {
    long lastReadPosition;
    int capacity;

    public ReadWriteArbiter(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberOfFreeSlots(long writePosition){
        return capacity - (int)(writePosition - lastReadPosition);
    }

    public void setLastReadPosition(long lastReadPosition) {
        this.lastReadPosition = lastReadPosition;
    }
}
