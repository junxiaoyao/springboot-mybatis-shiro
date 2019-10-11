package com.jxy.study.util;

/**
 * @description
 * @author: jxy
 * @create: 2019-10-10 15:39
 */
public class ThreadLocalUtil {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal() {

        @Override
        protected Object initialValue() {
            return "";
        }
    };
    public static void addValue(String sessionId){
        threadLocal.set(sessionId);
    }
    public static String getValue(){
        return threadLocal.get();
    }
}
