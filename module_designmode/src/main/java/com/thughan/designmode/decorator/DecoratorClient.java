package com.thughan.designmode.decorator;


import com.thughan.designmode.decorator.concreteComponent.YangGuo;
import com.thughan.designmode.decorator.concreteDecorator.HongQiGong;
import com.thughan.designmode.decorator.concreteDecorator.OuYangFeng;

/**
 * 装饰模式
 * 使用场景:
 * 在不影响其它对象的情况下,以动态,透明的方式给单个对象添加职责.
 * 需要动态给一个对象增加功能,这些功能可以动态的撤销.
 * 当不能采用继承的方式对系统进行扩充或者采用继承不利于系统扩展和维护时.
 * 优点:
 * 通过组合而非继承的方式,动态地扩展一个对象的功能,在运行时选择不同的装饰器,从而实现不同的行为.
 * 有效避免了使用继承的方式扩展对象功能而带来的灵活性差,子类无限制扩张的问题.
 * 具体组件类与具体装饰类可以独立变化,用户可以根据需要增加新的具体组件类和具体装饰类,在使用时再对其进行组合,原有代码无须改变,符合"开闭原则".
 * 缺点:
 * 因为所有对象均继承于Component,所以如果Component内部结构发生改变,则不可避免地影响所有子类(装饰者和被装饰者).
 * 如果基类改变,则势必影响对象的内部.
 * 比继承更加灵活机动的特性,也意味着装饰模式比继承更加容易出错,排错也困难.对于多次装饰的对象,调试时需要逐级排查,较为繁琐,
 * 所以,只在必要的时候使用装饰模式.
 * 装饰层数不能过多,否则会影响效率.
 */
public class DecoratorClient {
    public static void invoke() {
        // 创建杨过
        YangGuo yangGuo = new YangGuo();
        // 洪七公向杨过传授打狗棒法,杨过学会打狗棒法
        HongQiGong hongQiGong = new HongQiGong(yangGuo);
        hongQiGong.attackMagic();
        // 欧阳锋向杨过传授蛤蟆功,杨过学会蛤蟆功
        OuYangFeng ouYangFeng = new OuYangFeng(yangGuo);
        ouYangFeng.attackMagic();
    }
} 