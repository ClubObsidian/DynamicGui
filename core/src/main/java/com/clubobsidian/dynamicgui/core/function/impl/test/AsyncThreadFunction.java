package com.clubobsidian.dynamicgui.core.function.impl.test;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;

public class AsyncThreadFunction extends Function {

    public AsyncThreadFunction() {
        super("isasyncthread", true);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        return !DynamicGui.get().getPlatform().isMainThread();
    }
}
