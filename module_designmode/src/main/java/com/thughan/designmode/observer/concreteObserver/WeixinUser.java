package com.thughan.designmode.observer.concreteObserver;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.observer.observer.Observer;

public class WeixinUser implements Observer {
    // 用户名
    private String name;

    public WeixinUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        LogUtils.i(name + "-" + message);
    }
}
