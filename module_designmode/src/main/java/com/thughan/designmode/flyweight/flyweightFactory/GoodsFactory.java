package com.thughan.designmode.flyweight.flyweightFactory;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.flyweight.concreteFlyweight.Goods;

import java.util.HashMap;
import java.util.Map;

public class GoodsFactory {

    private static Map<String, Goods> pool = new HashMap<>();

    public static Goods getGoods(String name) {
        if (pool.containsKey(name)) {
            LogUtils.i("使用缓存,key为:" + name);
            return pool.get(name);
        } else {
            Goods goods = new Goods(name);
            pool.put(name, goods);
            LogUtils.i("创建商品,key为:" + name);
            return goods;
        }
    }

} 