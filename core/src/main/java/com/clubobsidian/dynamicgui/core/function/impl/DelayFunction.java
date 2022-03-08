package com.clubobsidian.dynamicgui.core.function.impl;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import org.apache.commons.lang3.math.NumberUtils;

public class DelayFunction extends Function {

    public DelayFunction() {
        super("delay", true);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null || !NumberUtils.isParsable(this.getData())) {
            return false;
        }
        try {
            Thread.sleep(Integer.parseInt(this.getData()));
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
