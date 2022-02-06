package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public abstract class MockItemStackWrapper extends ItemStackWrapper<MockItemStack> {

    public MockItemStackWrapper(MockItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public String getType() {
        return this.getItemStack().getType();
    }
}
