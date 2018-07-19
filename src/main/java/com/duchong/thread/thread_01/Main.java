package com.duchong.thread.thread_01;

/**
 * @author DUCHONG
 * @since 2018-07-18 16:58
 **/
public class Main {

    public static void main(String[] args) {

//        MyThread thread1=new MyThread("thread1");
//        thread1.start();
//        MyThread thread2=new MyThread("thread2");
//        thread2.start();
//        MyThread thread3=new MyThread("thread3");
//        thread3.start();
//        MyThread thread4=new MyThread("thread4");
//        thread4.start();
//        MyThread thread5=new MyThread("thread5");
//        thread5.start();

        Mythread2 mythread2=new Mythread2();
        new Thread(mythread2 ).start();
        new Thread(mythread2).start();
        new Thread(mythread2).start();
        new Thread(mythread2).start();
        new Thread(mythread2).start();
    }
}
