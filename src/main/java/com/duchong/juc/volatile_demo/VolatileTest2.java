package com.duchong.juc.volatile_demo;

/**
 * @author DUCHONG
 * @since 2018-12-24 16:37
 **/
public class VolatileTest2 {
    public static void main(String[] args) {

        FlagThread2 flagThread=new FlagThread2();
        new Thread(flagThread).start();

        while (true){
            //方式二
            if (flagThread.getFlag()) {
                System.out.println(Thread.currentThread().getName() + "---" + flagThread.getFlag());
                break;
            }
        }
    }

}

class FlagThread2 extends Thread{

    private volatile boolean flag=false;
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
        }
        flag=true;
        System.out.println(Thread.currentThread().getName()+"---run");
    }

    public boolean getFlag(){
        return flag;
    }
}

