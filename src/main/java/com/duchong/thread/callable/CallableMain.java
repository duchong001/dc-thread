package com.duchong.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author DUCHONG
 * @since 2019-01-03 11:23
 **/
public class CallableMain {


    public static void main(String[] args)throws Exception{

        FutureTask<Integer> futureTask=new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //业务逻辑
                Integer sum=0;
                for (int i=1;i<=100;i++){
                    sum+=i;
                }
                return sum;
            }
        });

        new Thread(futureTask).start();

        Integer callResult=futureTask.get();

        System.out.println(callResult);
    }
}


