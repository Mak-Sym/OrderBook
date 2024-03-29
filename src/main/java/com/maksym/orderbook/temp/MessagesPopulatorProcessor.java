package com.maksym.orderbook.temp;

import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.queues.impl.MessagesQueue;
import com.maksym.orderbook.utils.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author mfedoryshyn
 */
public class MessagesPopulatorProcessor implements Runnable {
    private static final long DEFAULT_DELAY = 10000;

    private MessagesQueue messagesQueue;
    private BufferedReader inputStreamReader;

    public MessagesPopulatorProcessor(String inputFileName, int bufferSize, MessagesQueue messagesQueue) throws FileNotFoundException {
        this.messagesQueue = messagesQueue;
        inputStreamReader = new BufferedReader(new FileReader(inputFileName), bufferSize);
    }

    public void doProcess() throws IOException {
        while(true){
            int slotsCount = messagesQueue.getCountOfFreeSlots();
            if(slotsCount < 1){
                Logger.info("Producer Is Waiting...");
                try {Thread.sleep(DEFAULT_DELAY);}
                catch (InterruptedException ignored) {}
            } else {
                for(int i = 0; i < slotsCount; i++){
                    String line = inputStreamReader.readLine();
                    if(line == null){
                        inputStreamReader.close();
                        return;
                    }
                    messagesQueue.addMessage(OrderMessage.createMessage(line));
                }
            }
        }
    }

    public void run() {
        try {
            doProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
