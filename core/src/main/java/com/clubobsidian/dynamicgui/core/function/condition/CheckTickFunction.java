/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.core.function.condition;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;

import java.math.BigDecimal;
import java.util.Map;

public class CheckTickFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 9209750645416892269L;

    public CheckTickFunction() {
        super("checktick");
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
                Expression expr = new Expression(tickData, ExpressionConfiguration.defaultConfiguration().withAdditionalFunctions(Map.entry("STREQUAL", new EqualLazyFunction())));
                return expr.evaluate().getNumberValue().equals(BigDecimal.ONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
