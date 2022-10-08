/*
 *    Copyright 2022 virustotalop and contributors.
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

package com.clubobsidian.dynamicgui.core.function.impl;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;

public class SetGlowFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = -3727112026677117024L;

    public SetGlowFunction() {
        super("setglow", "setglowing");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        if (this.getData() == null) {
            return false;
        }
        FunctionOwner owner = this.getOwner();
        if (owner != null && owner instanceof Slot) {
            Slot slot = (Slot) owner;
            Gui gui = slot.getOwner();
            if (gui != null) {
                InventoryWrapper<?> inv = gui.getInventoryWrapper();
                if (inv != null) {
                    boolean value = Boolean.parseBoolean(this.getData());
                    ItemStackWrapper<?> item = slot.getItemStack();
                    item.setGlowing(value);
                    inv.setItem(slot.getIndex(), item);
                    return true;
                }
            }
        }
        return false;
    }
}