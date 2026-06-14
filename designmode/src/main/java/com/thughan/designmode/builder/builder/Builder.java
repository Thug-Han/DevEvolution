package com.thughan.designmode.builder.builder;

import com.thughan.designmode.builder.product.Computer;

public abstract class Builder {

    public abstract void buildCpu(String cpu);

    public abstract void buildMainboard(String mainboard);

    public abstract void buildRam(String ram);

    public abstract Computer create();
} 