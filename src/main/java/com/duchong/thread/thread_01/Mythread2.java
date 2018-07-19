package com.duchong.thread.thread_01;

/**
 * @author DUCHONG
 * @since 2018-07-18 17:37
 **/
public class Mythread2 implements Runnable {

    int  num=5;

    @Override
    public void run() {
        String currentName=Thread.currentThread().getName();
        //while (num>0){
            num--;
            System.out.println("当前线程:"+currentName+"---num:"+num);
        //}
    }
}
