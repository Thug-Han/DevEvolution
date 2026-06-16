package com.thughan.designmode.flyweight;


import com.thughan.designmode.flyweight.concreteFlyweight.Goods;
import com.thughan.designmode.flyweight.flyweightFactory.GoodsFactory;

/**
 * 享元模式
 * 使用场景:
 * 系统中存在大量的相似对象
 * 需要缓冲池的场景
 */
public class FlyweightClient {
    public static void invoke() {
        Goods goods1 = GoodsFactory.getGoods("iphone7");
        goods1.showGoodsPrice("32G");
        Goods goods2 = GoodsFactory.getGoods("iphone7");
        goods2.showGoodsPrice("32G");
        Goods goods3 = GoodsFactory.getGoods("iphone7");
        goods3.showGoodsPrice("128G");
    }
} 