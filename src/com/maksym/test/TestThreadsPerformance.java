/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mfedoryshyn
 */
public class TestThreadsPerformance {
    public static void main(String[] args) throws Exception {
        test_1();
        test_2();
        test_3();
        test_4();
        test_5();
        test_6();
    }

    public static void test_1() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: ONE PRODUCER - ONE CONSUMER");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");

        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }

    public static void test_2() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer2 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: ONE PRODUCER - TWO CONSUMERS");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        consumer2.start();
        producer.join();
        consumer.join();
        consumer2.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }

    public static void test_3() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer2 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer3 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: ONE PRODUCER - THREE CONSUMERS");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        consumer2.start();
        consumer3.start();
        producer.join();
        consumer.join();
        consumer2.join();
        consumer3.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }

    public static void test_4() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer2 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer2 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: TWO PRODUCERS - TWO CONSUMERS");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");
        long start = System.currentTimeMillis();
        producer.start();
        producer2.start();
        consumer.start();
        consumer2.start();
        producer.join();
        producer2.join();
        consumer.join();
        consumer2.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }

    public static void test_5() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer2 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer3 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer2 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: TWO PRODUCERS - THREE CONSUMERS");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");
        long start = System.currentTimeMillis();
        producer.start();
        producer2.start();
        consumer.start();
        consumer2.start();
        consumer3.start();
        producer.join();
        producer2.join();
        consumer.join();
        consumer2.join();
        consumer3.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }

    public static void test_6() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(100);
        StandardMessageConsumer consumer = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer2 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer3 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer4 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer5 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer6 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer7 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageConsumer consumer8 = new StandardMessageConsumer(10, 10000, queue);
        StandardMessageProducer producer = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer2 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer3 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer4 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageProducer producer5 = new StandardMessageProducer(5, 10000, queue);
        StandardMessageConsumer.readMessagesCount = new AtomicInteger(0);
        StandardMessageProducer.writeMessagesCount = new AtomicInteger(0);
        System.out.println("TEST: 5 PRODUCERS - 8 CONSUMERS");
        System.out.println("PRODUCER SPEED: 5ms");
        System.out.println("CONSUMER SPEED: 10ms");
        long start = System.currentTimeMillis();
        producer.start();
        producer2.start();
        producer3.start();
        producer4.start();
        producer5.start();
        consumer.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
        consumer6.start();
        consumer7.start();
        consumer8.start();
        producer.join();
        producer2.join();
        producer3.join();
        producer4.join();
        producer5.join();
        consumer.join();
        consumer2.join();
        consumer3.join();
        consumer4.join();
        consumer5.join();
        consumer6.join();
        consumer7.join();
        consumer8.join();
        start = System.currentTimeMillis() - start;
        System.out.println("TOTAL TIME: " + start);
    }
}

class StandardMessageProducer extends Thread {
    private long _delay = 10;
    private int _numberOfMessages;
    private BlockingQueue<Object> _queue;
    private int _countOfWaiting;
    public volatile static AtomicInteger writeMessagesCount;

    public StandardMessageProducer(long delay, int numberOfMessages, BlockingQueue<Object> queue){
        this._delay = delay;
        this._numberOfMessages = numberOfMessages;
        this._queue = queue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        while(writeMessagesCount.incrementAndGet() < _numberOfMessages){
            try {
                nop();
                if(_queue.remainingCapacity() == 0){
                    //LOG.log("remaining capacity == 0!");
                    ++_countOfWaiting;
                }
                _queue.put(String.valueOf(System.currentTimeMillis()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.log("Producer waited " + _countOfWaiting + " times");
    }

    private void nop() throws InterruptedException {
        Thread.sleep(_delay); //computing imitation
    }
}

class StandardMessageConsumer extends Thread {
    private long _delay = 10;
    private int _numberOfMessages = 1000;
    private BlockingQueue<Object> _queue;
    private int _countOfWaiting;
    public volatile static AtomicInteger readMessagesCount;

    public StandardMessageConsumer(long delay, int numberOfMessages, BlockingQueue<Object> queue){
        this._delay = delay;
        this._numberOfMessages = numberOfMessages;
        this._queue = queue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        while(readMessagesCount.incrementAndGet() < _numberOfMessages){
            try {
                if(_queue.isEmpty()){
//                    LOG.log("queue is empty!");
                    ++_countOfWaiting;
                }
                _queue.take();
                nop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.log("Consumer waited " + _countOfWaiting + " times");
    }

    private void nop() throws InterruptedException {
        Thread.sleep(_delay); //computing imitation
    }
}

class LOG {
    public static void log(String message){
        System.out.print(message);
        System.out.print("\r\n");
    }
}