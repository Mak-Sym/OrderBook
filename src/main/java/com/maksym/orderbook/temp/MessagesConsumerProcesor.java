package com.maksym.orderbook.temp;

import com.maksym.orderbook.queues.impl.MessagesQueue;
import com.maksym.orderbook.utils.Logger;

/**
 * @author mfedoryshyn
 */
public class MessagesConsumerProcesor implements Runnable {
    private static final long DEFAULT_DELAY = 50;

    private MessagesQueue messagesQueue;
    private static volatile long index = 0;

    public MessagesConsumerProcesor(MessagesQueue messagesQueue) {
        this.messagesQueue = messagesQueue;
    }

    public void doProcess() {
        while(true){
            if(index < messagesQueue.getNextWritePosition() - 1){
                messagesQueue.getMessage();
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
