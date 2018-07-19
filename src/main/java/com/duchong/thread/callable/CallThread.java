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
public class CallThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("进入CallThread的call()方法, 开始睡觉, 睡觉时间为" + System.currentTimeMillis());
        Thread.sleep(10000);
        return "Hello Callable";
    }

    public static void main(String[] args) throws Exception
    {
        ExecutorService es = Executors.newCachedThreadPool();
        CallThread ct = new CallThread();
        Future<String> f = es.submit(ct);
        es.shutdown();

        Thread.sleep(5000);
        System.out.println("主线程等待5秒, 当前时间为" + System.currentTimeMillis());

        String str = f.get();
        System.out.println("Future已拿到数据, str = " + str + ", 当前时间为" + System.currentTimeMillis());
    }
}
