package com.maksym.orderbook.processors;

import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.queues.IQueue;
import com.maksym.orderbook.utils.FlowController;
import com.maksym.orderbook.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Reads the messages and puts them into messages queue
 *
 * @author mfedoryshyn
 */
public class MessagesReader implements Runnable {
    private IQueue<OrderMessage> messagesQueue;
    private BufferedReader inputStreamReader;
    private FlowController flowController;

    public MessagesReader(IQueue<OrderMessage> messagesQueue, Reader reader, FlowController flowController) {
        this.messagesQueue = messagesQueue;
        this.inputStreamReader = new BufferedReader(reader, 1000);
        this.flowController = flowController;
    }

    public void doProcess() throws IOException {
        String line;
        try {
            while((line = inputStreamReader.readLine()) != null){
                while(messagesQueue.isFull()){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {}
                }
                messagesQueue.addMessage(OrderMessage.createMessage(line));
            }
        } catch(Exception e) {
            Logger.error("Error reading file", MessagesReader.class, e);
        } finally {
            flowController.readComplete = true;
            if(inputStreamReader != null){
                inputStreamReader.close();
            }
        }
    }

    @Override
    public void run() {
        try {
            doProcess();
        } catch (IOException e) {
            Logger.error("Error invoking doProcess", MessagesReader.class, e);
        }
    }
}
