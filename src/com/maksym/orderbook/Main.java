package com.maksym.orderbook;

import com.maksym.orderbook.processors.MessagesConsumerProcesor;
import com.maksym.orderbook.processors.MessagesPopulatorProcessor;
import com.maksym.orderbook.queues.impl.MessagesQueue;

import java.io.FileNotFoundException;

/**
 * @author mfedoryshyn
 */
public class Main {
    private static final String FILENAME = "D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\pricer.in";

    public static void main(String[] args) throws FileNotFoundException {
        MessagesQueue messagesQueue = new MessagesQueue(10000);
        MessagesPopulatorProcessor populatorProcessor = new MessagesPopulatorProcessor(FILENAME, 100000, messagesQueue);
        MessagesConsumerProcesor consumerProcesor = new MessagesConsumerProcesor(messagesQueue);
        MessagesConsumerProcesor consumerProcesor2 = new MessagesConsumerProcesor(messagesQueue);
        MessagesConsumerProcesor consumerProcesor3 = new MessagesConsumerProcesor(messagesQueue);

        new Thread(populatorProcessor).start();
        new Thread(consumerProcesor).start();
        new Thread(consumerProcesor2).start();
        new Thread(consumerProcesor3).start();
//        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);
//        threadExecutor.execute(populatorProcessor);
//        threadExecutor.execute(consumerProcesor);
    }
}
