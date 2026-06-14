package com.thughan.android.crashcatch.crashmaker.crash;

import com.thughan.android.crashcatch.crashmaker.IProduct;

import java.util.ArrayList;

/**
 * @Date : 2024/3/22
 * @Desc : out of bound crash
 **/
public class OutOfBoundCrash implements IProduct {
    @Override
    public void triggerCrash() {
        ArrayList<String> arrayList = new ArrayList<>(2);
        arrayList.add("A");
        arrayList.add("B");
        arrayList.get(2);
    }

}
