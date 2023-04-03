package com.clubobsidian.dynamicgui.parser.test.mock;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.google.inject.Guice;
import org.mockito.Mockito;

public class MockFactory {

    public <T> T mock(Class<T> mockClazz) {
        return Mockito.mock(mockClazz, Mockito.withSettings()
                .useConstructor()
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public void inject() {
        DynamicGui dynamicGui = mock(MockDynamicGui.class);
        Guice.createInjector(new MockPluginModule(dynamicGui));
    }
}
