package com.duchong.designmodel.proxy.static_;

/**
 * 真实歌手
 *
 * @author DUCHONG
 * @since 2018-10-30 17:22
 **/
public class RealSinger implements Singer {


    @Override
    public void talk() {
        System.out.println("RealSinger talk ...");
    }

    @Override
    public void sing() {
        System.out.println("RealSinger sing ...");
    }

    @Override
    public void getMoney() {
        System.out.println("RealSinger getMoney ...");
    }
}
