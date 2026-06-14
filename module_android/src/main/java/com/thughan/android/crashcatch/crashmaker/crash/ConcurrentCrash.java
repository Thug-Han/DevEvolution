package com.thughan.android.crashcatch.crashmaker.crash;

import com.thughan.android.crashcatch.crashmaker.IProduct;

import java.util.ArrayList;

/**
 * @Date : 2024/3/22
 * @Desc : concurrent Modification exception
 **/
public class ConcurrentCrash implements IProduct {

    @Override
    public void triggerCrash() {
        /**
         * 还有一种情况是此处在For循环,但是有其它线程进行移除操作.也会报这个异常.
         */
        ArrayList<String> arrayList = new ArrayList<>(3);
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        for (String s : arrayList) {
            arrayList.remove(s);
        }
    }
}
