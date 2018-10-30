package com.duchong.designmodel.proxy.static_;

/**
 * 代理歌手
 *
 * @author DUCHONG
 * @since 2018-10-30 17:23
 **/
public class ProxySinger implements Singer {

    private RealSinger realSinger;

    public ProxySinger(RealSinger singer) {

        this.realSinger=singer;
    }

    @Override
    public void talk() {
        System.out.println("ProxySinger talk....");
    }

    @Override
    public void sing() {
        realSinger.sing();
    }

    @Override
    public void getMoney() {
        System.out.println("ProxySinger getMoney...");
    }
}
