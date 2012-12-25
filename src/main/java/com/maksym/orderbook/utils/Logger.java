package com.maksym.orderbook.utils;

/**
 * @author mfedoryshyn
 */
public class Logger {
    public static void info(String msg){
        System.out.print("\r\n");
        System.out.print("[INFO] - " + msg);
    }

    public static void error(String msg, Class cls, Throwable e){
        System.out.print("\r\n");
        System.out.print(cls.getName() + "[ERROR] - " + msg);
        e.printStackTrace(System.out);
    }
}
