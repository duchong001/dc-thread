package com.duchong.designmodel.factory;

/**
 * 简单工厂类
 *
 * @author DUCHONG
 * @since 2018-10-29 11:20
 **/

public class CarSimpleFactory {


    public static Car createCar(String type){

        if(type.equals("Auto")){
            return new Auto();
        }
        else if(type.equals("Byd")){
            return new Byd();
        }

        return null;
    }
}
