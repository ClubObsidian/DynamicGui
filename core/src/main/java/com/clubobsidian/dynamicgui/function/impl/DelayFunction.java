package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import org.apache.commons.lang3.math.NumberUtils;

public class DelayFunction extends Function {

    public DelayFunction() {
        super("delay");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if(this.getData() == null || !NumberUtils.isParsable(this.getData())) {
            return false;
        }
        try {
            Thread.sleep(Integer.parseInt(this.getData()));
            return true;
        } catch(InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
