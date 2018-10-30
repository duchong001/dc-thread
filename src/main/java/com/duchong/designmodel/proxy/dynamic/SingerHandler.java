package com.duchong.designmodel.proxy.dynamic;

import com.duchong.designmodel.proxy.static_.Singer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author DUCHONG
 * @since 2018-10-30 18:27
 **/
public class SingerHandler implements InvocationHandler {

    private Singer singer;

    public SingerHandler(Singer singer) {
        this.singer=singer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("--------");
        method.invoke(singer,args);
        System.out.println("--------");
        return null;
    }
}
