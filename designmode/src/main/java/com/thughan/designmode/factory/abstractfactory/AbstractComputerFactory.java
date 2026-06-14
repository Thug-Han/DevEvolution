package com.thughan.designmode.factory.abstractfactory;


import com.thughan.designmode.factory.iproduct.Computer;

public abstract class AbstractComputerFactory {
    public abstract <T extends Computer> T createComputer(Class<T> clz);
}