package com.thughan.designmode.factory.factory;


import com.thughan.designmode.factory.abstractfactory.AbstractComputerFactory;
import com.thughan.designmode.factory.iproduct.Computer;

/**
 * 工厂方法模式
 * 去除了与具体产品的依赖,无需修改工厂类,直接创建产品即可.
 */
public class GDComputerFactory extends AbstractComputerFactory {
    @Override
    public <T extends Computer> T createComputer(Class<T> clz) {
        Computer computer = null;
        String className = clz.getName();
        try {
            computer = (Computer) Class.forName(className).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) computer;
    }

}