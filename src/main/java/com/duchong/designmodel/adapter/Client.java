package com.duchong.designmodel.adapter;

/**
 * @author DUCHONG
 * @since 2018-10-11 10:13
 **/
public class Client {

    public static void main(String[] args) {

        NewInterface t;

        t=new Adapter();
        t.target();

        t=new AdapterWithoutExtends(new OldClass());
        t.target();
    }
}
