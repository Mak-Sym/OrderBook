package com.maksym.orderbook.processors;

import com.maksym.orderbook.structures.MessagesQueue;
import com.maksym.orderbook.utils.Logger;

/**
 * @author mfedoryshyn
 */
public class MessagesConsumerProcesor implements Runnable {
    private static final long DEFAULT_DELAY = 50;

    private MessagesQueue messagesQueue;
    private long index = 0;

    public MessagesConsumerProcesor(MessagesQueue messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    public void doProcess() {
        while(true){
            if(index < messagesQueue.getNextWritePosition() - 1){
                messagesQueue.getMessage(index++);
                try {Thread.sleep(10);}//imitation of computing
                catch (InterruptedException ignored) {}
            } else {
                Logger.info("Consumer Is Waiting...");
                try {Thread.sleep(DEFAULT_DELAY);}
                catch (InterruptedException ignored) {}
            }
        }
    }

    public void run() {
        doProcess();
    }
}
