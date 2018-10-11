package com.duchong.designmodel.adapter;

/**
 * @author DUCHONG
 * @since 2018-10-11 10:11
 **/
public class Adapter extends OldClass implements NewInterface {
    @Override
    public void target() {
        super.old();
    }

}
