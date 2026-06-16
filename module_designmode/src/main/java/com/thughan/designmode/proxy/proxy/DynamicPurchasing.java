package com.thughan.designmode.proxy.proxy;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicPurchasing implements InvocationHandler {

    private Object obj;

    public DynamicPurchasing(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(obj, args);
        if (method.getName().equals("buy")) {
            LogUtils.i("hanjinlong在买买买");
        }
        return result;
    }
}