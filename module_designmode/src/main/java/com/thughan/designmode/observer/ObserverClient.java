package com.thughan.designmode.observer;


import com.thughan.designmode.observer.concreteObserver.WeixinUser;
import com.thughan.designmode.observer.concreteSubject.SubscriptionSubject;

/**
 * 观察者模式
 * 使用场景:
 * 关联行为场景.需要注意的是,关联行为是可拆分的,而不是组合关系.
 * 事件多级触发场景.
 * 跨系统的消息交换场景,如消息队列,事件总线的处理机制.
 * 优点:
 * 观察者和被观察者之间是抽象耦合,容易扩展.
 * 方便建立一套触发机制.
 * 缺点:
 * 在应用观察者模式时需要考虑一下开发效率和运行效率的问题.程序中包括一个被观察者,多个观察者,开发,调试等内容会比较复杂,
 * 而且在java中消息的通知一般是顺序执行的,那么一个观察者卡顿,会影响整体的执行效率,在这种情况下,一般会采用异步方式.
 */
public class ObserverClient {
    public static void invoke() {
        SubscriptionSubject subscriptionSubject = new SubscriptionSubject();
        // 创建微信用户
        WeixinUser user1 = new WeixinUser("杨影枫");
        WeixinUser user2 = new WeixinUser("月眉儿");
        WeixinUser user3 = new WeixinUser("紫轩");
        // 订阅公众号
        subscriptionSubject.attach(user1);
        subscriptionSubject.attach(user2);
        subscriptionSubject.attach(user3);
        // 发出消息给订阅的微信用户
        subscriptionSubject.notify("我的专栏终于更新了!!!");
    }
}
