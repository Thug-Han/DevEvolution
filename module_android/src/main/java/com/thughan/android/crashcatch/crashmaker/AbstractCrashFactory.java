package com.thughan.android.crashcatch.crashmaker;

/**
 * @Date : 2024/3/22
 * @Desc :
 **/
public abstract class AbstractCrashFactory {
    public abstract <T extends IProduct> T createProduct(Class<T> clz);

}
