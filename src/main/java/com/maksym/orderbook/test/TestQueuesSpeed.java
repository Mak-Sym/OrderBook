package com.maksym.orderbook.test;

import com.maksym.orderbook.domain.message.AddOrderMessage;
import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.domain.message.ReduceOrderMessage;
import com.maksym.orderbook.queues.impl.MessagesQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mdfedoryshyn
 */
public class TestQueuesSpeed {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test: LinkedBlockingDeque: " + test1());
        System.out.println("Test: ArrayBlockingQueue: " + test1_1());
        System.out.println("Test: LinkedBlockingQueue: " + test1_2());
        System.out.println("Test: My Implementation: " + test2());
    }

    private static long test1() throws InterruptedException {
        BlockingQueue<OrderMessage> queue = new LinkedBlockingDeque<OrderMessage>(10000);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        start = System.currentTimeMillis() - start;
        return start;
    }

    private static long test1_1() throws InterruptedException {
        BlockingQueue<OrderMessage> queue = new ArrayBlockingQueue<OrderMessage>(10000);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        start = System.currentTimeMillis() - start;
        return start;
    }

    private static long test1_2() throws InterruptedException {
        BlockingQueue<OrderMessage> queue = new LinkedBlockingQueue<OrderMessage>(10000);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        start = System.currentTimeMillis() - start;
        return start;
    }

    private static long test2() throws InterruptedException {
        MessagesQueue queue = new MessagesQueue(10000);
        Producer1 producer = new Producer1(queue);
        Consumer1 consumer = new Consumer1(queue);
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        start = System.currentTimeMillis() - start;
        return start;
    }
}

class Producer extends Thread {
    private BlockingQueue<OrderMessage> queue;

    Producer(BlockingQueue<OrderMessage> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0; i < 1000000; i++){
            try {
                queue.put(new ReduceOrderMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private BlockingQueue<OrderMessage> queue;

    Consumer(BlockingQueue<OrderMessage> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0; i < 1000000; i++){
            try {
                OrderMessage orderMessage = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer1 extends Thread {
    private MessagesQueue queue;

    Producer1(MessagesQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0; i < 1000000; i++){
            int count = queue.getCountOfFreeSlots();
            for(int ii = 0; ii < count; ii++, i++){
                queue.addMessage(new AddOrderMessage());
            }
        }
    }
}

class Consumer1 extends Thread {
    private MessagesQueue queue;
    private long index = 0;

    Consumer1(MessagesQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0; i < 1000000; i++){
            if(index < queue.getNextWritePosition() - 1){
                queue.getMessage();
            }
        }
    }
}
