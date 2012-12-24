package com.maksym.orderbook.domain.bid;

import com.maksym.orderbook.cache.OrdersFetcher;
import com.maksym.orderbook.domain.OrdersOperations;
import com.maksym.orderbook.domain.message.OrderMessage;
import com.maksym.orderbook.utils.OrderBookHelper;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author mfedoryshyn
 */
public class Bids implements OrdersOperations {
    private Set<Bid> bidsByPrice;
    private int count;
    private OrdersFetcher<Bid> ordersFetcher = OrdersFetcher.createFetcher();
    private Comparator<Bid> sortByPriceComparator = new Comparator<Bid>() {
        @Override
        public int compare(Bid o1, Bid o2) {
            int result =  o2.getPrice().compareTo(o1.getPrice());
            return result == 0 ? (o1.getSize() - o2.getSize()) : result;
        }
    };

    public Bids() {
        bidsByPrice = new ConcurrentSkipListSet<Bid>(sortByPriceComparator);
    }

    @Override
    public void add(OrderMessage message){
        if(message.getMessageType() != 'A'){
            throw new IllegalArgumentException("Wrong message type!");
        }
        Bid bid = OrderBookHelper.convertToBid(message);
        System.out.println("Adding BID: " + bid.getOrderId());
        bidsByPrice.add(bid);
        ordersFetcher.addOrder(bid);
        count += message.getSize();
        System.out.println("count of BIDs " + count);
    }

    @Override
    public boolean reduce(OrderMessage message){
        if(message.getMessageType() != 'R'){
            throw new IllegalArgumentException("Wrong message type!");
        }
        Bid bid = OrderBookHelper.convertToBid(message);
        System.out.println("Trying to reduce bids for BID: " + bid.getOrderId());
        Bid existentBid = ordersFetcher.getOrder(bid);
        if(existentBid != null){
            if(bid.getSize() >= existentBid.getSize()){
                bidsByPrice.remove(existentBid);
                ordersFetcher.invalidateOrder(existentBid);
                count -= existentBid.getSize();
            } else {
                existentBid.setSize(existentBid.getSize() - bid.getSize());
                count -= bid.getSize();
            }
            System.out.println("BID " + bid.getOrderId() + " FOUND! Count of bids: " + count);
            return true;
        }
        System.out.println("BID " + bid.getOrderId() + " NOT FOUND");
        return false;
    }

    @Override
    public int getTotalCount() {
        return count;
    }

    public Collection<Bid> getBids(){
        return bidsByPrice;
    }

    public String debugOutput(){
        StringBuilder sb = new StringBuilder();
        sb.append("BIDS: ");
        for(Bid bid: bidsByPrice) {
            sb.append(bid.getOrderId()).append(" -> ").append(bid.getPrice()).append(" -> ").append(bid.getSize()).append(";   ");
        }
        return sb.toString();
    }
}
