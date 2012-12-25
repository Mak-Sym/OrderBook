package com.maksym.orderbook;

import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.domain.message.PrintMessage;
import com.maksym.orderbook.processors.MessagesReader;
import com.maksym.orderbook.processors.OrdersBookProcessor;
import com.maksym.orderbook.processors.PrintProcessor;
import com.maksym.orderbook.queues.IQueue;
import com.maksym.orderbook.queues.impl.MessagesQueue;
import com.maksym.orderbook.utils.FlowController;

import java.io.FileReader;
import java.io.Reader;

/**
 * @author mfedoryshyn
 */
public class Main {
    private static final String FILENAME = "D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\pricer.in";
//    private static final String FILENAME = "D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\test.in";

    /*public static void main(String[] args) throws FileNotFoundException {
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
    }*/

    public static void main(String[] args) throws Exception {
        /*if(args.length != 1){
            System.out.println("Invalid argument");
            return;
        }
        int level = Integer.parseInt(args[0]);*/
        int level = 200;

        Reader reader = new FileReader(FILENAME);

        FlowController controller = new FlowController();

        IQueue<OrderMessage> orderMessagesQueue = new MessagesQueue<OrderMessage>(1000);
        IQueue<PrintMessage> printMessagesQueue = new MessagesQueue<PrintMessage>(10000);

        MessagesReader messagesReader = new MessagesReader(orderMessagesQueue, reader, controller);
        OrdersBookProcessor ordersBook = new OrdersBookProcessor(orderMessagesQueue, printMessagesQueue, controller, level);
        PrintProcessor printer = new PrintProcessor(printMessagesQueue, controller);


        Thread readerThread = new Thread(messagesReader, "readerThread");
        Thread processorThread = new Thread(ordersBook, "processorThread");
        Thread printerThread = new Thread(printer, "printerThread");

        long start = System.currentTimeMillis();
        readerThread.start();
        processorThread.start();
        printerThread.start();

        readerThread.join();
//        System.out.println("\n\nreaderThread completed");
        processorThread.join();
//        System.out.println("\n\nprocessorThread completed");
        printerThread.join();
        long duration = System.currentTimeMillis() - start;
        System.out.println("\n\nDURATION: " + duration);
    }
}
