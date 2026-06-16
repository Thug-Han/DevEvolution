package com.thughan.designmode.template.concreteclass;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.template.abstractclass.AbstractSwordsman;

public class ZhangWuji extends AbstractSwordsman {
    @Override
    protected void neigong() {
        LogUtils.i("运行九阳神功");
    }

    @Override
    protected void weapons() {
    }

    @Override
    protected void moves() {
        LogUtils.i("使用乾坤大挪移");
    }

    @Override
    protected boolean hasWeapons() {
        return false;
    }
}
