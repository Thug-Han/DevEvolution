package com.thughan.designmode.template.concreteclass;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.template.abstractclass.AbstractSwordsman;

public class ZhangSanFeng extends AbstractSwordsman {
    @Override
    protected void neigong() {
        LogUtils.i("运行纯阳无级功");
    }

    @Override
    protected void weapons() {
        LogUtils.i("使用真开剑");
    }

    @Override
    protected void moves() {
        LogUtils.i("使用招工神门十三剑");
    }

    @Override
    protected void hook() {
        LogUtils.i("突然肚子不舒服,老夫先去趟厕所");
    }
}
