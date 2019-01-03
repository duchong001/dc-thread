package com.duchong.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 有返回值的Runnable
 *
 * @author DUCHONG
 * @since 2018-07-19 14:15
 **/
public class CallThread {


    public static void main(String[] args) throws Exception
    {
        ExecutorService es = Executors.newCachedThreadPool();

        Future<String> f = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello callable";
            }
        });

        es.shutdown();
        //获取返回值
        String str = f.get();

        System.out.println(str);
    }
}
