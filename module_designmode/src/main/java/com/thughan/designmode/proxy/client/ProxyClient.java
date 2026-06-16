package com.thughan.designmode.proxy.client;


import com.thughan.designmode.proxy.proxy.DynamicPurchasing;
import com.thughan.designmode.proxy.proxy.Purchasing;
import com.thughan.designmode.proxy.realsubject.Hanjinlong;
import com.thughan.designmode.proxy.subject.IShop;

import java.lang.reflect.Proxy;


/**
 * 代理模式从编码角度来说可以分为静态代理和动态代理,而从适应范围来说可分为以下4种类型.
 * 1.远程代理:为一个对象在不同的地址空间提供局部代表,这样系统可以将Server部分的实现隐藏.
 * 2.虚拟代理:使用一个代理对象表示一个十分耗费资源的对象并在真正需要时才创建.
 * 3.安全代理:用来控制真实对象访问时的权限.一般用于真实对象有不同的访问权限时.
 * 4.智能指引:当调用真实对象时,代理处理另外一些事,比如计算真实对象的引用计数,当该对象没有引用时,可以自动释放它;
 * 或者访问一个实际对象时,检查是否已经能够锁定它,以确保其他对象不能改变它.
 * 代理模式优点主要有以下几点:
 * 1.真实主题类就是实现实际的业务逻辑,不用关心其它非本职的工作.
 * 2.真实主题类随时都会发生变化;但是因为它实现了公共的接口,所以代理类可以不做任何修改就能够使用.
 */
public class ProxyClient {
    /**
     * 静态代理调用方法
     */
    public static void staticBuy() {
        IShop hanjinlong = new Hanjinlong();
        IShop purchasing = new Purchasing(hanjinlong);
        purchasing.buy();
    }

    /**
     * 动态代理调用方法
     */
    public static void DynamicBuy() {
        IShop hanjinlong = new Hanjinlong();
        DynamicPurchasing dynamicPurchasing = new DynamicPurchasing(hanjinlong);
        ClassLoader loader = hanjinlong.getClass().getClassLoader();
        IShop purchasing = (IShop) Proxy.newProxyInstance(loader, new Class[]{IShop.class}, dynamicPurchasing);
        purchasing.buy();
    }
} 