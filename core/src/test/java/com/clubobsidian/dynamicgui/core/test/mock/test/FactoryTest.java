package com.clubobsidian.dynamicgui.core.test.mock.test;

import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.BeforeEach;

public abstract class FactoryTest {

    private final MockFactory factory = new MockFactory();

    public MockFactory getFactory() {
        return this.factory;
    }

    @BeforeEach
    public void setup() {
        this.factory.inject();
    }
}