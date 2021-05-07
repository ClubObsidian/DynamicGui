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
package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SetDataFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = 6943230273788425141L;

    public SetDataFunction(String name) {
        super(name);
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        FunctionOwner owner = this.getOwner();
        if(owner != null) {
            if(owner instanceof Slot) {
                Slot slot = (Slot) owner;
                Gui gui = slot.getOwner();
                if(gui != null) {
                    InventoryWrapper<?> inv = gui.getInventoryWrapper();
                    if(inv != null) {
                        ItemStackWrapper<?> item = slot.getItemStack();

                        try {
                            short dura = Short.parseShort(this.getData());
                            item.setDurability(dura);
                            inv.setItem(slot.getIndex(), item);
                            return true;
                        } catch(Exception ex) {
                            DynamicGui.get().getLogger().info("Unable to parse + " + this.getData() + " as durability");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}