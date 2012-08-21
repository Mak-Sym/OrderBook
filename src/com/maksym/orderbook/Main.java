package com.maksym.orderbook;

import com.maksym.orderbook.processors.MessagesConsumerProcesor;
import com.maksym.orderbook.processors.MessagesPopulatorProcessor;
import com.maksym.orderbook.structures.MessagesQueue;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mfedoryshyn
 */
public class Main {
    private static final String FILENAME = "D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\pricer.in";

    public static void main(String[] args) throws FileNotFoundException {
        MessagesQueue messagesQueue = new MessagesQueue(1000);
        MessagesPopulatorProcessor populatorProcessor = new MessagesPopulatorProcessor(FILENAME, 100000, messagesQueue);
        MessagesConsumerProcesor consumerProcesor = new MessagesConsumerProcesor(messagesQueue);

        ExecutorService threadExecutor = Executors.newFixedThreadPool(2);
        threadExecutor.execute(populatorProcessor);
        threadExecutor.execute(consumerProcesor);
    }
}
