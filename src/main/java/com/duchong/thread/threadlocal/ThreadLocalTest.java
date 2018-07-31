package com.duchong.thread.threadlocal;

import java.util.UUID;

/**
 * @author DUCHONG
 * @since 2018-07-30 17:35
 **/
public class ThreadLocalTest {

    private static  ThreadLocal<String> data=new ThreadLocal<String>();

    public static void main(String[] args) {


        for (int i = 0; i <2 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String str= UUID.randomUUID().toString().replace("-","").toUpperCase();
                    data.set(str);
                    System.out.println(Thread.currentThread().getName()+"---put---"+str+"---in ThreadLocal");
                    new A().getStr();
                    new B().getStr();
                }
            }).start();
        }
    }


    static class B {

        private String str=data.get();
        public void getStr(){
            System.out.println("class B---get---"+str+"---from---"+Thread.currentThread().getName());
        }
    }

    static class A {

        private String str=data.get();
        public void getStr(){

            System.out.println("class A---get---"+str+"---from---"+Thread.currentThread().getName());
        }
    }
}
