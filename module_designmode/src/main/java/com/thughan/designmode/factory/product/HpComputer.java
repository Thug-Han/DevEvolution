package com.thughan.designmode.factory.product;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.factory.iproduct.Computer;

public class HpComputer extends Computer {
    @Override
    public void start() {
        LogUtils.i("惠普计算机启动");
    }
}