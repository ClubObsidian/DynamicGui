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

package com.clubobsidian.dynamicgui.core.function;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.world.WorldWrapper;

public class GetGameRuleFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = -5840073572003297982L;

    public GetGameRuleFunction() {
        super("getgamerule");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null || !this.getData().contains(",")) {
            return false;
        }
        String[] split = this.getData().split(",");
        if (split.length == 3) {
            String worldName = split[0];
            WorldWrapper<?> world = DynamicGui.get().getPlatform().getWorld(worldName);
            if (world == null) {
                return false;
            }
            String rule = split[1];
            String value = split[2];
            String worldRule = world.getGameRule(rule);
            return value.equals(worldRule);
        }
        return false;
    }
}
