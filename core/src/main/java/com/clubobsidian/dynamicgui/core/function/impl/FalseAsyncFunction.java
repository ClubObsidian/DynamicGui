package com.clubobsidian.dynamicgui.core.function.impl;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import org.apache.commons.lang3.math.NumberUtils;

public class FalseAsyncFunction extends Function {

    public FalseAsyncFunction() {
        super("falseasync", true);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        try {
            Thread.sleep(5000);
            return false;
        } catch(InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
