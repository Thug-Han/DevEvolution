package com.thughan.designmode.strategy;


import com.thughan.designmode.strategy.concreteStrategy.CommonRivalStrategy;
import com.thughan.designmode.strategy.concreteStrategy.StrongRivalStrategy;
import com.thughan.designmode.strategy.concreteStrategy.WeakRivalStrategy;
import com.thughan.designmode.strategy.context.Context;

/**
 * 策略模式:
 * 使用场景:
 * 对客户隐藏具体策略(算法)的实现细节,彼此完全独立.
 * 针对同一类型问题的多种处理方式,仅仅是具体行为有差别时.
 * 在一个类中定义了多种行为,而且这些行为在这个类中的操作以多个条件语句的形式出现.
 * 策略模式将相关的条件分支移入它们各自的Strategy类中,以代替这些条件语句.
 * 优点:
 * 使用策略模式可以避免使用多重条件语句.多重条件语句不易维护,而且容易出错.
 * 易于拓展,当需要添加一个策略时,只需要实现接口就可以了.
 * 缺点:
 * 每一个策略都是一个类,复用性小.如果策略过多,类的数量会增多.
 * 上层模块必须知道有哪些策略,才能够使用这些策略,这与迪米特原则相违背.
 */
public class StrategyClient {
    public static void invoke() {
        Context context;
        // 张无忌遇到对手宋青书,采用对较弱对手的策略.
        context = new Context(new WeakRivalStrategy());
        context.fighting();
        // 张无忌遇到对手灭绝师太,采用对普通对手的策略.
        context = new Context(new CommonRivalStrategy());
        context.fighting();
        // 张无忌遇到对手成昆,采用对强大对手的策略.
        context = new Context(new StrongRivalStrategy());
        context.fighting();
    }
} 