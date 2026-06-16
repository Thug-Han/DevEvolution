package com.thughan.designmode.decorator.concreteDecorator;

import com.blankj.utilcode.util.LogUtils;
import com.thughan.designmode.decorator.component.SwordsMan;
import com.thughan.designmode.decorator.decorator.Master;

public class OuYangFeng extends Master {
    public OuYangFeng(SwordsMan mSwordsMan) {
        super(mSwordsMan);
    }

    public void teachAttackMagic() {
        LogUtils.i("欧阳锋教授蛤蟆功");
        LogUtils.i("杨过使用蛤蟆功");
    }

    @Override
    public void attackMagic() {
        super.attackMagic();
        teachAttackMagic();
    }
}