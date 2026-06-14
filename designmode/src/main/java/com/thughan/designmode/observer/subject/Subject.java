package com.thughan.designmode.observer.subject;


import com.thughan.designmode.observer.observer.Observer;

public interface Subject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    void attach(Observer observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    void detach(Observer observer);

    /**
     * 通知订阅者更新消息
     *
     * @param message
     */
    void notify(String message);
}
