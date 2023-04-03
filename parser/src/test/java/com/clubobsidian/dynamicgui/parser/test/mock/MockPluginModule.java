package com.clubobsidian.dynamicgui.parser.test.mock;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.google.inject.Binder;
import com.google.inject.Module;

public class MockPluginModule implements Module {

    private final DynamicGui dynamicGui;

    public MockPluginModule(DynamicGui dynamicGui) {
        this.dynamicGui = dynamicGui;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(DynamicGui.class).toInstance(this.dynamicGui);
        binder.requestStaticInjection(DynamicGui.class);
    }
}