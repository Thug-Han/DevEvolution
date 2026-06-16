package com.thughan.designmode.factory.factory;


import com.thughan.designmode.factory.iproduct.Computer;
import com.thughan.designmode.factory.product.AsusComputer;
import com.thughan.designmode.factory.product.HpComputer;
import com.thughan.designmode.factory.product.LenovoComputer;

/**
 * 简单工厂模式的场景和优缺点
 * 使用场景:
 * 工厂类负责创建的对象比较少.
 * 客户只需要知道传入工厂类的参数,而无须关心创建对象的逻辑.
 * 优点:使用户根据参数获得对应的类实例,避免了直接实例化类,降低了耦合性.
 * 缺点:可实例化的类型在编译期间已经被确定.如果增加新类型,则需要修改工厂,这违背了开放封装原则.
 * 简单工厂需要知道所生成的类型,其当子类过多或者子类层次过多时不适合使用.
 */
public class ComputerFactory {
    public static Computer createComputer(String type) {
        Computer mComputer = null;
        switch (type) {
            case "lenovo":
                mComputer = new LenovoComputer();
                break;
            case "hp":
                mComputer = new HpComputer();
                break;
            case "asus":
                mComputer = new AsusComputer();
                break;
            default:
                break;
        }
        return mComputer;
    }
}