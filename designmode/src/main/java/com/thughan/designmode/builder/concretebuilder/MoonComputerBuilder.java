package com.thughan.designmode.builder.concretebuilder;


import com.thughan.designmode.builder.builder.Builder;
import com.thughan.designmode.builder.product.Computer;

public class MoonComputerBuilder extends Builder {
    private Computer mComputer = new Computer();

    @Override
    public void buildCpu(String cpu) {
        mComputer.setmCpu(cpu);
    }

    @Override
    public void buildMainboard(String mainboard) {
        mComputer.setmMainboard(mainboard);
    }

    @Override
    public void buildRam(String ram) {
        mComputer.setmRam(ram);
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}