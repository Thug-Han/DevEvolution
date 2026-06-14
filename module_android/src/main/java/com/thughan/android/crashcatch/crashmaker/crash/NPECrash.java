package com.thughan.android.crashcatch.crashmaker.crash;

import android.os.Handler;

import com.thughan.android.crashcatch.crashmaker.IProduct;

/**
 * @Date : 2024/3/22
 * @Desc : 空指针
 **/
public class NPECrash implements IProduct {

    private NPECrash mNPECrash;

    @Override
    public void triggerCrash() {
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
                mNPECrash.triggerCrash();
//            }
//        });
    }

}
