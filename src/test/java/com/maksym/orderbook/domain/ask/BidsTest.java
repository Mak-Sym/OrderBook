package com.maksym.test.orderbook.domain.ask;

import com.maksym.orderbook.domain.bid.Bid;
import com.maksym.orderbook.domain.bid.Bids;
import com.maksym.orderbook.domain.message.AddOrderMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static junit.framework.Assert.assertTrue;

public class BidsTest {

    private Bids bids;

    @Test
    public void testOrder1(){
        bids = new Bids();
        AddOrderMessage addOrderMessage1 = new AddOrderMessage();
        addOrderMessage1.setPrice(new BigDecimal(10L));
        addOrderMessage1.setMessageType('A');
        addOrderMessage1.setSide('B');
        bids.add(addOrderMessage1);
        AddOrderMessage addOrderMessage2 = new AddOrderMessage();
        addOrderMessage2.setPrice(new BigDecimal(100L));
        addOrderMessage2.setMessageType('A');
        addOrderMessage2.setSide('B');
        bids.add(addOrderMessage2);

        Iterator<Bid> iterator = bids.getBids().iterator();
        Bid bid1 = iterator.next();
        Bid bid2 = iterator.next();
        assertTrue(bid1.getPrice().longValue() > bid2.getPrice().longValue());
    }

    @Test
    public void testOrder2(){
        bids = new Bids();
        AddOrderMessage addOrderMessage1 = new AddOrderMessage();
        addOrderMessage1.setPrice(new BigDecimal(100L));
        addOrderMessage1.setMessageType('A');
        addOrderMessage1.setSide('B');
        bids.add(addOrderMessage1);
        AddOrderMessage addOrderMessage2 = new AddOrderMessage();
        addOrderMessage2.setPrice(new BigDecimal(10L));
        addOrderMessage2.setMessageType('A');
        addOrderMessage2.setSide('B');
        bids.add(addOrderMessage2);

        Iterator<Bid> iterator = bids.getBids().iterator();
        Bid bid1 = iterator.next();
        Bid bid2 = iterator.next();
        assertTrue(bid1.getPrice().longValue() > bid2.getPrice().longValue());
    }
}
