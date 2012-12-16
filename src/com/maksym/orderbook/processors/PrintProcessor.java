package com.maksym.orderbook.processors;

import com.maksym.orderbook.domain.message.PrintMessage;
import com.maksym.orderbook.queues.IQueue;
import com.maksym.orderbook.utils.FlowController;
import com.maksym.orderbook.utils.Logger;

/**
 * @author mfedoryshyn
 */
public class PrintProcessor implements Runnable {
    private IQueue<PrintMessage> queue;
    private FlowController flowController;
    private String newLine;

    public PrintProcessor(IQueue<PrintMessage> queue, FlowController flowController) {
        this.queue = queue;
        this.flowController = flowController;
        this.newLine = (System.getProperty("line.separator") == null) ? "\r\n" : System.getProperty("line.separator");
    }

    @Override
    public void run() {
        try {
            doProcess();
        } catch (Exception e) {
            Logger.error("Error invoking doProcess", PrintProcessor.class, e);
        }
    }

    public void doProcess() throws Exception {
        while (!flowController.orderBookComplete){
            if(queue.isEmpty()){
                try { Thread.sleep(10);}
                catch (InterruptedException ignored) {}
                continue;
            }
            print(queue.getMessage());
        }
    }

    private void print(PrintMessage message) {
        System.out.print(this.newLine);
        System.out.print(message.toString());
    }
}
