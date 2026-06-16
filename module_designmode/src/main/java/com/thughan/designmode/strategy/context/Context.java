package com.thughan.designmode.strategy.context;


import com.thughan.designmode.strategy.strategy.FightingStrategy;

public class Context {
    private FightingStrategy fightingStrategy;

    public Context(FightingStrategy fightingStrategy) {
        this.fightingStrategy = fightingStrategy;
    }

    public void fighting() {
        fightingStrategy.fighting();
    }
}