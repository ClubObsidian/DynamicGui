package com.clubobsidian.dynamicgui.parser.test;

import com.clubobsidian.dynamicgui.parser.test.mock.MockFactory;
import org.junit.jupiter.api.BeforeEach;

public abstract class FactoryTest {

    private static final MockFactory FACTORY = new MockFactory();

    @BeforeEach
    public void before() {
        FACTORY.inject();
    }

}
