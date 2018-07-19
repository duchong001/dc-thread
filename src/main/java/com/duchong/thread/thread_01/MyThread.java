package com.duchong.thread.thread_01;

/**
 * @author DUCHONG
 * @since 2018-07-18 17:09
 **/
public class MyThread extends Thread {

     int  num=10;

    public MyThread(String name){
        this.setName(name);
    }

    @Override
    public void run() {
        String currentName=this.currentThread().getName();
        while (num>0){
            num--;
            System.out.println("当前线程:"+currentName+"---num:"+num);
        }
    }
}
