package com.maksym.orderbook.structures;

import com.maksym.orderbook.Exceptions.NewLineFoundException;
import com.maksym.orderbook.interfaces.BufferPopulator;

import java.io.IOException;

/**
 * @author mfedoryshyn
 */
public class CharCircledBuffersProcessor {
    private static final int DEFAULT_CAPACITY = 10000;
    private static final int DEFAULT_BUFFERS_NUMBER = 2;

    private char buffer[];
    private int capacity;
    private int currentPosition = 0;
    private BufferPopulator populator;

    public CharCircledBuffersProcessor(){
        this(DEFAULT_CAPACITY);
    }

    public CharCircledBuffersProcessor(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException("capacity has to be > 0");
        }
        this.capacity = capacity;
        buffer = new char[capacity];
    }

    public int readNextLine(char[] dst) throws IOException {
        int totalCount = 0;
        try {
            for(int i = this.currentPosition % capacity; ;){
                if(i > 0 && i < capacity){
                    if((dst[totalCount++] = buffer[i++]) == '\n'){
                        throw new NewLineFoundException();
                    }
                } else if(i == 0){
                    int readCount = populator.populateBuffer(buffer);
                    if(readCount == -1){
                        throw new NewLineFoundException();
                    } else if(readCount < capacity){
                        buffer[readCount] = '\n'; //two \n at the end means that that is last block
                    }
                    if((dst[totalCount++] = buffer[i++]) == '\n'){
                        throw new NewLineFoundException();
                    }
                } else {
                    i = 0;
                }
            }
        } catch (NewLineFoundException nlfe){ /*expected*/}
        this.currentPosition += totalCount;
        return (totalCount < 2) ? -1 : totalCount;
    }

    public void setPopulator(BufferPopulator populator) {
        this.populator = populator;
    }
}
