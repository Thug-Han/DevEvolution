package com.thughan.designmode.factory.product;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.factory.iproduct.Computer;

public class LenovoComputer extends Computer {
    @Override
    public void start() {
        LogUtils.i("联想计算机启动");
    }
}