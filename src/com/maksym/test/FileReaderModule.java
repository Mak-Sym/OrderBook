package com.maksym.test;

import com.maksym.orderbook.domain.Message;

import java.io.*;

public class FileReaderModule {
    public static void main(String[] args) throws IOException {
        File input = new File("D:\\Projects\\Education\\OredrBook_TestTask\\test_data\\pricer.in");
        System.out.println("FileInputStream:");
//        System.out.println("duration (100): " + read_1(input, 100));
//        System.out.println("duration (1000): " + read_1(input, 1000));
//        System.out.println("duration (10000): " + read_1(input, 10000));
        System.out.println("duration (100000): " + read_1(input, 100000));
//        System.out.println("duration (1000000): " + read_1(input, 1000000));
        
        System.out.println("FileReader:");
        System.out.println("duration (100): " + read_2(input, 100));
        System.out.println("duration (1000): " + read_2(input, 1000));
        System.out.println("duration (10000): " + read_2(input, 10000));
        System.out.println("duration (100000): " + read_2(input, 100000));
        System.out.println("duration (1000000): " + read_2(input, 1000000));

        System.out.println("BufferedReader:");
        System.out.println("duration (100): " + read_3(input, 100));
        System.out.println("duration (1000): " + read_3(input, 1000));
        System.out.println("duration (10000): " + read_3(input, 10000));
        System.out.println("duration (100000): " + read_3(input, 100000));
        System.out.println("duration (1000000): " + read_3(input, 1000000));
        System.out.println("duration (default): " + read_3(input, 0));

        System.out.println("BufferedReader with message creating:");
        System.out.println("duration (100): " + read_3_1(input, 100));
        System.out.println("duration (1000): " + read_3_1(input, 1000));
        System.out.println("duration (10000): " + read_3_1(input, 10000));
        System.out.println("duration (100000): " + read_3_1(input, 100000));
        System.out.println("duration (1000000): " + read_3_1(input, 1000000));
        System.out.println("duration (default): " + read_3_1(input, 0));
    }

    private static long read_1(File input, int bufferSize) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(input);
        byte[] buffer = new byte[bufferSize];

        long start = System.currentTimeMillis();
        int len;
        while((len = fileInputStream.read(buffer)) != -1){
            System.out.print(len);
            System.out.print("\r\n");
        }
        long duration = System.currentTimeMillis() - start;

        fileInputStream.close();
        return duration;
    }
    
    private static long read_2(File input, int bufferSize) throws IOException {
        FileReader fileReader = new FileReader(input);
        char[] buffer = new char[bufferSize];

        long start = System.currentTimeMillis();
        while(fileReader.read(buffer) != -1);
        long duration = System.currentTimeMillis() - start;
        
        fileReader.close();
        return duration;
    }

    private static long read_3(File input, int bufferSize) throws IOException {
        FileReader fileReader = new FileReader(input);
        BufferedReader bufferedReader = bufferSize > 0 ? new BufferedReader(fileReader, bufferSize) : new BufferedReader(fileReader);
        long start = System.currentTimeMillis();
        while(bufferedReader.readLine() != null);
        long duration = System.currentTimeMillis() - start;

        bufferedReader.close();
        fileReader.close();

        return duration;
    }

    private static long read_3_1(File input, int bufferSize) throws IOException {
        FileReader fileReader = new FileReader(input);
        BufferedReader bufferedReader = bufferSize > 0 ? new BufferedReader(fileReader, bufferSize) : new BufferedReader(fileReader);
        long start = System.currentTimeMillis();
        String line;
        while((line = bufferedReader.readLine()) != null){
            Message.createMessage(line);
        }
        long duration = System.currentTimeMillis() - start;

        bufferedReader.close();
        fileReader.close();

        return duration;
    }
}
