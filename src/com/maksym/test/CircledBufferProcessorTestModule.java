package com.maksym.test;

import com.maksym.orderbook.domain.Message;
import com.maksym.orderbook.interfaces.BufferPopulator;
import com.maksym.orderbook.structures.ByteCircledBuffersProcessor;
import com.maksym.orderbook.structures.CharCircledBuffersProcessor;

import java.io.*;
import java.util.Date;

/**
 * @author mfedoryshyn
 */
public class CircledBufferProcessorTestModule implements BufferPopulator {
    private static final String fileName = "D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\pricer.in";
    private FileReader fileReader = null;
    private FileInputStream fileInputStream = null;

    public static void main(String[] args) throws Exception {
        System.out.println("test (100): " + test(100));
        System.out.println("test (1000): " + test(1000));
        System.out.println("test (10000): " + test(10000));
        System.out.println("test (100000): " + test(100000));
        System.out.println("test (1000000): " + test(1000000));
        System.out.println("test (10000000): " + test(10000000));

        System.out.println();
        System.out.println("test_with_messages (1000): " + test_with_messages(1000));
        System.out.println("test_with_messages (10000): " + test_with_messages(10000));
        System.out.println("test_with_messages (100000): " + test_with_messages(100000));
        System.out.println("test_with_messages (1000000): " + test_with_messages(1000000));
        System.out.println("test_with_messages (10000000): " + test_with_messages(10000000));

        System.out.println();
        System.out.println("test_2 (100): " + test_2(100));
        System.out.println("test_2 (1000): " + test_2(1000));
        System.out.println("test_2 (10000): " + test_2(10000));
        System.out.println("test_2 (100000): " + test_2(100000));
        System.out.println("test_2 (1000000): " + test_2(1000000));
        System.out.println("test_2 (10000000): " + test_2(10000000));

        System.out.println();
        System.out.println("BufferedReader with message creating:");
        System.out.println("duration (100): " + read_3_1(100));
        System.out.println("duration (1000): " + read_3_1(1000));
        System.out.println("duration (10000): " + read_3_1(10000));
        System.out.println("duration (100000): " + read_3_1(100000));
        System.out.println("duration (1000000): " + read_3_1(1000000));
        System.out.println("duration (default): " + read_3_1(0));
    }

    public static long test(int capacity) throws Exception {
        CircledBufferProcessorTestModule testModule = new CircledBufferProcessorTestModule();
        CharCircledBuffersProcessor circledBuffersProcessor = new CharCircledBuffersProcessor(capacity);
        circledBuffersProcessor.setPopulator(testModule);

        char[] dst = new char[200];

        long start = new Date().getTime();
        while(circledBuffersProcessor.readNextLine(dst)!= -1);
        return new Date().getTime() - start;
    }

    public static long test_with_messages(int capacity) throws Exception {
        CircledBufferProcessorTestModule testModule = new CircledBufferProcessorTestModule();
        CharCircledBuffersProcessor circledBuffersProcessor = new CharCircledBuffersProcessor(capacity);
        circledBuffersProcessor.setPopulator(testModule);

        char[] dst = new char[200];

        long start = new Date().getTime();
        try {
            while(circledBuffersProcessor.readNextLine(dst)!= -1){
                Message.createMessage(dst);
            }
        } catch(Exception e){
            for(int i = 0; i < 50; i++){
                System.out.print(dst[i]);
            }
            throw e;
        }
        return new Date().getTime() - start;
    }


    public static long test_2(int capacity) throws Exception {
        CircledBufferProcessorTestModule testModule = new CircledBufferProcessorTestModule();
        ByteCircledBuffersProcessor circledBuffersProcessor = new ByteCircledBuffersProcessor(capacity);
        circledBuffersProcessor.setPopulator(testModule);

        byte[] dst = new byte[200];

        long start = new Date().getTime();
        while(circledBuffersProcessor.readNextLine(dst)!= -1);
        return new Date().getTime() - start;
    }

    public int populateBuffer(char[] buffer) throws IOException {
        if(fileReader == null){
            fileReader = new FileReader(fileName);
        }
        int res = fileReader.read(buffer, 0, buffer.length);
        if(res == -1){
            fileReader.close();
        }
        return res;
    }

    private static long read_3_1(int bufferSize) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = bufferSize > 0 ? new BufferedReader(fileReader, bufferSize) : new BufferedReader(fileReader);
        long start = new Date().getTime();
        String line;
        while((line = bufferedReader.readLine()) != null){
            Message.createMessage(line);
        }
        long duration = new Date().getTime() - start;

        bufferedReader.close();
        fileReader.close();

        return duration;
    }

    public int populateBuffer(byte[] buffer) throws IOException {
        if(fileInputStream == null){
            fileInputStream = new FileInputStream(fileName);
        }
        int res = fileInputStream.read(buffer);
        if(res == -1 || res < buffer.length){
            fileInputStream.close();
        }
        return res;
    }
}
