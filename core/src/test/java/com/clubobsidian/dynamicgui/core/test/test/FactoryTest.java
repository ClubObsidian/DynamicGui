package com.clubobsidian.dynamicgui.core.test.test;

import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.BeforeEach;

public interface FactoryTest {

    MockFactory factory = new MockFactory();

    @BeforeEach
    default void setup() {
        factory.inject();
    }
}