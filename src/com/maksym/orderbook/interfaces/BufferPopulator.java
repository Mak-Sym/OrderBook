package com.maksym.orderbook.interfaces;

import java.io.IOException;

/**
 * @author mfedoryshyn
 */
public interface BufferPopulator {
    int populateBuffer(char[] buffer) throws IOException;
    int populateBuffer(byte[] buffer) throws IOException;
}
