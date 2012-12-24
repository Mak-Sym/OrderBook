package com.maksym.test.orderbook.domain.ask;

import com.maksym.orderbook.domain.ask.Ask;
import com.maksym.orderbook.domain.ask.Asks;
import com.maksym.orderbook.domain.message.AddOrderMessage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static junit.framework.Assert.assertTrue;

public class AsksTest {
    @Test
    public void testOrder1(){
        Asks asks = new Asks();
        AddOrderMessage addOrderMessage1 = new AddOrderMessage();
        addOrderMessage1.setPrice(new BigDecimal(10L));
        addOrderMessage1.setMessageType('A');
        addOrderMessage1.setSide('S');
        asks.add(addOrderMessage1);
        AddOrderMessage addOrderMessage2 = new AddOrderMessage();
        addOrderMessage2.setPrice(new BigDecimal(100L));
        addOrderMessage2.setMessageType('A');
        addOrderMessage2.setSide('S');
        asks.add(addOrderMessage2);

        Iterator<Ask> iterator = asks.getAsks().iterator();
        Ask ask1 = iterator.next();
        Ask ask2 = iterator.next();
        assertTrue(ask1.getPrice().longValue() < ask2.getPrice().longValue());
    }

    @Test
    public void testOrder2(){
        Asks asks = new Asks();
        AddOrderMessage addOrderMessage1 = new AddOrderMessage();
        addOrderMessage1.setPrice(new BigDecimal(100L));
        addOrderMessage1.setMessageType('A');
        addOrderMessage1.setSide('S');
        asks.add(addOrderMessage1);
        AddOrderMessage addOrderMessage2 = new AddOrderMessage();
        addOrderMessage2.setPrice(new BigDecimal(10L));
        addOrderMessage2.setMessageType('A');
        addOrderMessage2.setSide('S');
        asks.add(addOrderMessage2);

        Iterator<Ask> iterator = asks.getAsks().iterator();
        Ask ask1 = iterator.next();
        Ask ask2 = iterator.next();
        assertTrue(ask1.getPrice().longValue() < ask2.getPrice().longValue());
    }
}
