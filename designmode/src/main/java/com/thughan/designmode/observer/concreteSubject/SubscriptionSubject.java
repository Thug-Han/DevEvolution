package com.thughan.designmode.observer.concreteSubject;

import com.thughan.designmode.observer.observer.Observer;
import com.thughan.designmode.observer.subject.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionSubject implements Subject {
    // 存储订阅公众号的微信用户
    private List<Observer> wexinUserList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        wexinUserList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        wexinUserList.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : wexinUserList) {
            observer.update(message);
        }
    }
}
