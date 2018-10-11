package com.duchong.designmodel.adapter;

/**
 * @author DUCHONG
 * @since 2018-10-11 10:50
 **/
public class AdapterWithoutExtends implements NewInterface {

    private OldClass oldClass;

    public AdapterWithoutExtends(OldClass oldClass) {
        this.oldClass = oldClass;
    }

    @Override
    public void target() {
        this.oldClass.old();
    }
}
