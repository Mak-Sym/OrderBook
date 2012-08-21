package com.maksym.orderbook.structures;

import com.maksym.orderbook.Exceptions.NewLineFoundException;
import com.maksym.orderbook.interfaces.BufferPopulator;

import java.io.IOException;

/**
 * @author mfedoryshyn
 */
public class ByteCircledBuffersProcessor {
    private static final int DEFAULT_CAPACITY = 10000;
    private static final int DEFAULT_BUFFERS_NUMBER = 2;

    private byte buffers[][];
    private int capacity;
    private long currentBufferPointer = -1;
    private int currentPosition = 0;
    private BufferPopulator populator;

    public ByteCircledBuffersProcessor(){
        this(DEFAULT_CAPACITY);
    }

    public ByteCircledBuffersProcessor(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException("capacity has to be > 0");
        }
        this.capacity = capacity;
        buffers = new byte[DEFAULT_BUFFERS_NUMBER][capacity];
    }

    public int readNextLine(byte[] dst) throws IOException {
        byte[] currentBuffer = (this.currentBufferPointer == -1) ? null : buffers[(int)(this.currentBufferPointer % DEFAULT_BUFFERS_NUMBER)];
        int totalCount = 0;
        try {
            for(int i = currentPosition % capacity; ;){
                if(i == 0){
                    currentBuffer = buffers[(int)((++this.currentBufferPointer) % DEFAULT_BUFFERS_NUMBER)];
                    int readCount = populator.populateBuffer(currentBuffer);
                    if(readCount == -1){
                        throw new NewLineFoundException();
                    } else if(readCount < capacity){
                        currentBuffer[readCount] = '\n'; //two \n at the end means that that is last block
                    }
                    if((dst[totalCount++] = currentBuffer[i++]) == '\n'){
                        throw new NewLineFoundException();
                    }
                } else if(i > 0 && i < capacity){
                    if((dst[totalCount++] = currentBuffer[i++]) == '\n'){
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
