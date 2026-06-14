package com.thughan.designmode.template.abstractclass;

import com.blankj.utilcode.util.LogUtils;

public abstract class AbstractSwordsman {
    public final void fighting() {
        // 运行内功,抽象方法
        neigong();
        // 调整经脉,具体方法
        meridian();
        // 如果有开口,则准备开口
        if (hasWeapons()) {
            weapons();
        }
        // 使用招工
        moves();
        // 钩子方法
        hook();
    }

    // 空实现方法
    protected void hook() {

    }

    protected abstract void neigong();

    protected abstract void weapons();

    protected abstract void moves();

    protected void meridian() {
        LogUtils.i("开启正经与奇经");
    }

    /**
     * 是否有武器,默认是有开口的,钩子方法.
     *
     * @return
     */
    protected boolean hasWeapons() {
        return true;
    }

}
