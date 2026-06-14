package com.thughan.designmode.decorator.decorator;


import com.thughan.designmode.decorator.component.SwordsMan;

public abstract class Master extends SwordsMan {

    private SwordsMan mSwordsMan;

    public Master(SwordsMan mSwordsMan) {
        this.mSwordsMan = mSwordsMan;
    }

    @Override
    public void attackMagic() {
        mSwordsMan.attackMagic();
    }

}