/* Copyright Homeaway, Inc 2005-2007. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.maksym.orderbook.domain.message;

import java.math.BigDecimal;

/**
 * mfedor has not yet bothered to enter a useful javadoc comment.
 */
public class PrintMessage {
    private String timestamp;
    private char action;
    private BigDecimal total;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public char getAction() {
        return action;
    }

    public void setAction(char action) {
        this.action = action;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
