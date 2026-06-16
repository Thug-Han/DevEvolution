package com.thughan.designmode.proxy.proxy;


import com.thughan.designmode.proxy.subject.IShop;

public class Purchasing implements IShop {
    private IShop mShop;

    public Purchasing(IShop mShop) {
        this.mShop = mShop;
    }

    @Override
    public void buy() {
        mShop.buy();
    }
}