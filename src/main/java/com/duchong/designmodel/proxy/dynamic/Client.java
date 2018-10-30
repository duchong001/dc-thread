package com.duchong.designmodel.proxy.dynamic;

import com.duchong.designmodel.proxy.static_.RealSinger;
import com.duchong.designmodel.proxy.static_.Singer;

import java.lang.reflect.Proxy;

/**
 * @author DUCHONG
 * @since 2018-10-30 18:23
 **/
public class Client {


    public static void main(String[] args) {

        Singer singer=new RealSinger();
        Singer proxy= (Singer) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),RealSinger.class.getInterfaces(),new SingerHandler(singer));
        proxy.sing();
    }


}
