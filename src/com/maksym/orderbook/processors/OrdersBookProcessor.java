package com.maksym.orderbook.processors;

import com.maksym.orderbook.domain.ask.Ask;
import com.maksym.orderbook.domain.ask.Asks;
import com.maksym.orderbook.domain.bid.Bid;
import com.maksym.orderbook.domain.bid.Bids;
import com.maksym.orderbook.domain.message.AddOrderMessage;
import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.domain.message.PrintMessage;
import com.maksym.orderbook.queues.IQueue;
import com.maksym.orderbook.utils.FlowController;
import com.maksym.orderbook.utils.Logger;

import java.math.BigDecimal;

/**
 * @author mfedoryshyn
 */
public class OrdersBookProcessor implements Runnable {
    private Bids bids;
    private Asks asks;
    private IQueue<OrderMessage> inputQueue;
    private IQueue<PrintMessage> outputQueue;
    private FlowController flowController;
    private int level;
    private BigDecimal lastAsksPrice = null;
    private BigDecimal lastBidsPrice = null;


    public OrdersBookProcessor(IQueue<OrderMessage> inputQueue, IQueue<PrintMessage> outputQueue,
                               FlowController flowController, int level) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.flowController = flowController;
        this.level = level;
        this.asks = new Asks();
        this.bids = new Bids();
    }

    @Override
    public void run() {
        try {
            doProcess();
        } catch (Exception e) {
            Logger.error("Error invoking doProcess", OrdersBookProcessor.class, e);
        }
    }

    public void doProcess() throws Exception {
        OrderMessage orderMessage;
        while(true){
            while(inputQueue.isEmpty()){
                if(!flowController.readComplete){
                    try { Thread.sleep(10);}
                    catch (InterruptedException ignored) {}
                } else {
                    flowController.orderBookComplete = true;
                    return; //we're done
                }
            }
            orderMessage = inputQueue.getMessage();
            PrintMessage printMessage = updateBidsAndAsks(orderMessage);
            if(printMessage != null){
                outputQueue.addMessage(printMessage);
            }
        }
    }

    protected PrintMessage updateBidsAndAsks(OrderMessage orderMessage) {
        PrintMessage printMessage = new PrintMessage();
        printMessage.setTimestamp(orderMessage.getTimestamp());
        if(orderMessage.getMessageType() == 'R'){
            if(bids.reduce(orderMessage)){
                printMessage = processBids(printMessage);
            } else if(asks.reduce(orderMessage)){
                printMessage = processAsks(printMessage);
            }
        } else if (orderMessage instanceof AddOrderMessage){
            if(((AddOrderMessage)orderMessage).getSide() == 'S'){
                asks.add(orderMessage);
                printMessage = processAsks(printMessage);
            } else {
                bids.add(orderMessage);
                printMessage = processBids(printMessage);
            }
        }
        return printMessage;
    }

    private PrintMessage processAsks(PrintMessage printMessage) {
        printMessage.setAction('B');
        BigDecimal total = null;
        if(asks.getTotalCount() >= level){
            total = new BigDecimal(0L);
            int offersLeft = level;
            for(Ask ask: asks.getAsks()){
                int num = Math.min(offersLeft, ask.getSize());
                total = total.add(ask.getPrice().multiply(new BigDecimal(num)));
                offersLeft -= num;
            }
            printMessage.setTotal(total);
        }
        if((lastAsksPrice == null && total == null)
           || (lastAsksPrice != null && total != null && total.equals(lastAsksPrice))){
            printMessage = null;
        }
        lastAsksPrice = total;
        return printMessage;
    }

    private PrintMessage processBids(PrintMessage printMessage) {
        printMessage.setAction('S');
        BigDecimal total = null;
        if(bids.getTotalCount() >= level){
            total = new BigDecimal(0L);
            int offersLeft = level;
            for(Bid bid: bids.getBids()){
                int num = Math.min(offersLeft, bid.getSize());
                total = total.add(bid.getPrice().multiply(new BigDecimal(num)));
                offersLeft -= num;
            }
            printMessage.setTotal(total);
        }
        if((lastBidsPrice == null && total == null)
           || (lastBidsPrice != null && total != null && total.equals(lastBidsPrice))){
            printMessage = null;
        }
        lastBidsPrice = total;
        return printMessage;
    }
}
