package com.thughan.designmode.decorator.concreteDecorator;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.decorator.component.SwordsMan;
import com.thughan.designmode.decorator.decorator.Master;

public class HongQiGong extends Master {

    public HongQiGong(SwordsMan mSwordsMan) {
        super(mSwordsMan);
    }

    public void teachAttackMagin() {
        LogUtils.i("洪七公教授打狗棒法");
        LogUtils.i("杨过使用打狗棒法");
    }

    @Override
    public void attackMagic() {
        super.attackMagic();
        teachAttackMagin();
    }
}