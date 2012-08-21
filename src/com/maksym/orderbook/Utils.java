package com.maksym.orderbook;

/**
 * @author mfedoryshyn
 */
public class Utils {
    public static int findNextWhitespace(char[] buff, int start){
        for(int i = start; ;  i++){
            try{
                if(isWhiteSpace(buff[i])){
                   return i;
               }
            } catch (ArrayIndexOutOfBoundsException e){
                return i;
            }
        }
    }

    public static int findNextAlpha(char[] buff, int start){
        for(int i = start; ;  i++){
           if(!isWhiteSpace(buff[i])){
               return i;
           }
        }
    }

    public static boolean isWhiteSpace(char ch){
        return ch == ' ' || ch == '\n' || ch == '\t';
    }
}
