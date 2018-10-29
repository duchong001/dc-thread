package com.duchong.designmodel.factory;

/**
 * 工厂类的客户端
 *
 * @author DUCHONG
 * @since 2018-10-29 11:20
 **/
public class Client {

    public static void main(String[] args) {

        //不使用工厂类
//        Car auto=new Auto();
//        auto.run();
//        Car byd=new Byd();
//        byd.run();

        //使用简单工厂类
        Car car= null;

        car=CarSimpleFactory.createCar("Auto");
        car.run();

        car=CarSimpleFactory.createCar("Byd");
        car.run();


    }


}
