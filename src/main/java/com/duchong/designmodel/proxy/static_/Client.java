package com.duchong.designmodel.proxy.static_;

/**
 * @author DUCHONG
 * @since 2018-10-30 18:20
 **/
public class Client {

    public static void main(String[] args) {

        Singer singer=new ProxySinger(new RealSinger());
        singer.talk();
        singer.sing();
        singer.getMoney();
    }
}
