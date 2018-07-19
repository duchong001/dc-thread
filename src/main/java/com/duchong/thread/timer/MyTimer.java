package com.duchong.thread.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author DUCHONG
 * @since 2018-07-19 17:06
 **/
public class MyTimer {

    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello timer");
            }
        },1000,1000);
    }
}
