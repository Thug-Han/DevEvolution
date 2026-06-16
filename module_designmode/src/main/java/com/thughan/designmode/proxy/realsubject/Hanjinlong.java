package com.thughan.designmode.proxy.realsubject;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.proxy.subject.IShop;

public class Hanjinlong implements IShop {
    @Override
    public void buy() {
        LogUtils.i("购买");
    }
}