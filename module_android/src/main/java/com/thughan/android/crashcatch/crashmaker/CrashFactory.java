package com.thughan.android.crashcatch.crashmaker;

/**
 * @Date : 2024/3/22
 * @Desc : crash factory
 **/
public class CrashFactory extends AbstractCrashFactory {

    @Override
    public <T extends IProduct> T createProduct(Class<T> clz) {
        IProduct product = null;
        try {
            product = (IProduct) Class.forName(clz.getName()).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (clz.isInstance(product)) {
            return clz.cast(product);
        } else {
            return null;
        }
    }

}
