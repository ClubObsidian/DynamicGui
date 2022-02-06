package com.clubobsidian.dynamicgui.test.mock;

import org.mockito.Mockito;

public class MockFactory {

    public <T> T mock(Class<T> mockClazz) {
        return Mockito.mock(mockClazz, Mockito.withSettings()
                .useConstructor()
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public <T> T mock(Class<T> mockClazz, Object... args) {
        return Mockito.mock(mockClazz, Mockito.withSettings().useConstructor(args)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public MockPlayerWrapper createPlayer() {
        return this.mock(MockPlayerWrapper.class);
    }

    public MockItemStackWrapper createItemStack(String type) {
        MockItemStack itemStack = this.mock(MockItemStack.class, type);
        return this.mock(MockItemStackWrapper.class, itemStack);
    }
}