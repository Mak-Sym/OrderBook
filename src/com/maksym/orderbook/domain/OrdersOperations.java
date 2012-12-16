package com.maksym.orderbook.domain;

import com.maksym.orderbook.domain.message.OrderMessage;

/**
 * @author mfedoryshyn
 */
public interface OrdersOperations {
    public void add(OrderMessage addMessage);

    /**
     *
     * @param reduceMessage - reduce message
     * @return true if records were affected, false otherwise
     */
    public boolean reduce(OrderMessage reduceMessage);
    public int getTotalCount();
}
