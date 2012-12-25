/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */package com.maksym.orderbook.cache;

import com.maksym.orderbook.domain.OrderBean;

/**
 * @author mfedoryshyn
 */
public abstract class OrdersFetcher <T extends OrderBean> {
    /**
     * Add order to the internal cache
     * @param order order
     */
    public abstract void addOrder(T order);

    /**
     * Invalidates order
     * @param order order to be deleted
     */
    public abstract void invalidateOrder(T order);

    /**
     * Get order from the internal cache that matches to the argument
     * @param order order to search
     * @return previously saved bid
     */
    public abstract T getOrder(T order);

    public static <Q extends OrderBean> OrdersFetcher<Q> createFetcher(){
        return new SimpleArrayOrdersFetcher<Q>();
    }
}
