/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.function.impl.condition;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.udojava.evalex.Expression;

public class CheckTickFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 9209750645416892269L;

    public CheckTickFunction(String name) {
        super(name);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        FunctionOwner owner = this.getOwner();
        if (owner instanceof Slot) {
            Slot slot = (Slot) owner;
            int tick = slot.getCurrentTick();
            int frame = slot.getFrame();

            try {
                String tickData = this.getData()
                        .replace("%tick%", String.valueOf(tick))
                        .replace("%frame%", String.valueOf(frame));
                Expression expr = new Expression(tickData);
                expr.addLazyFunction(new EqualLazyFunction());

                if (!expr.isBoolean()) {
                    return false;
                }

                return expr.eval().intValue() == 1;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
