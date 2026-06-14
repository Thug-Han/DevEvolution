package com.thughan.designmode.factory.product;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.factory.iproduct.Computer;

public class AsusComputer extends Computer {
    @Override
    public void start() {
        LogUtils.i("华硕计算机启动");
    }
}