package com.thughan.designmode.flyweight.concreteFlyweight;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.flyweight.flyweight.IGoods;

public class Goods implements IGoods {
    // 名称
    private String name;
    // 版本
    private String version;

    public Goods(String name) {
        this.name = name;
    }

    @Override
    public void showGoodsPrice(String version) {
        if (version.equals("32G")) {
            LogUtils.i("价格为5199元");
        } else if (version.equals("128G")) {
            LogUtils.i("价格为5999元");
        }
    }
}