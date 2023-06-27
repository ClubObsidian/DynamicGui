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
import org.apache.commons.lang3.math.NumberUtils;

public class CheckOpenInventorySlotsFunction extends Function {

    public CheckOpenInventorySlotsFunction() {
        super("OpenInventorySlots");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) throws Exception {
        if (this.getData() == null || !NumberUtils.isDigits(this.getData())) {
            DynamicGui.get().getLogger().error("Invalid data found for the check inventory slots function");
            return false;
        }
        try {
            int slots = Integer.parseInt(this.getData());
            int openSlots = playerWrapper.getOpenInventorySlots();
            return openSlots >= slots;
        } catch (NumberFormatException ex) {
            DynamicGui.get().getLogger().error("Invalid data found for the check inventory slots function");
        }
        return false;
    }
}
