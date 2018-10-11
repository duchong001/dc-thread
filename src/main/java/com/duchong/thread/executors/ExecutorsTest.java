package com.duchong.thread.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @author DUCHONG
 * @since 2018-07-31 11:44
 **/
public class ExecutorsTest {

    public static void main(String[] args) {

        ExecutorService executorService= Executors.newFixedThreadPool(3);
        for (int i = 0; i <10 ; i++) {

            final int task=i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"---task---"+task);
                }
            });
        }

        System.out.println("over ...");
        executorService.shutdown();
    }
}
