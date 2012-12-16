/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */package com.maksym.orderbook.cache;

import com.maksym.orderbook.domain.OrderBean;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mfedoryshyn
 */
public class SimpleArrayOrdersFetcher<T extends OrderBean> extends OrdersFetcher<T> {
    private List<T> bidsIndex = new LinkedList<T>();

    @Override
    public void addOrder(T order) {
        bidsIndex.add(order);
    }

    @Override
    public void invalidateOrder(T order) {
        bidsIndex.remove(order);

    }

    @Override
    public T getOrder(T order) {
        for(T order1 : bidsIndex){
            if(order.equals(order1)){
                return order1;
            }
        }
        return null;
    }
}
