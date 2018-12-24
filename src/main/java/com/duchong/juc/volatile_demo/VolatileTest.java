
package com.duchong.juc.volatile_demo;

/**
 * @author DUCHONG
 * @since 2018-12-24 15:57
 **/
public class VolatileTest {

    public static void main(String[] args) {

        FlagThread flagThread=new FlagThread();
        new Thread(flagThread).start();

        while (true){
            //方式一
            synchronized (flagThread) {
                if (flagThread.getFlag()) {
                    System.out.println(Thread.currentThread().getName() + "---" + flagThread.getFlag());
                    break;
                }
            }
        }
    }

}

class FlagThread extends Thread{

    private boolean flag=false;
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
