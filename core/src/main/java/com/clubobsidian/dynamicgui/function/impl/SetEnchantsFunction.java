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

import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

import java.util.HashMap;
import java.util.Map;

public class SetEnchantsFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = 8291956007296368761L;

    public SetEnchantsFunction() {
        super("setenchants");
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

                        Map<String, Integer> enchants = new HashMap<String, Integer>();
                        if(this.getData().contains(";")) {
                            for(String str : this.getData().split(";")) {
                                String[] split = str.split(",");
                                enchants.put(split[0], Integer.valueOf(split[1]));
                            }
                        } else {
                            String[] split = this.getData().split(",");
                            enchants.put(split[0], Integer.valueOf(split[1]));
                        }

                        for(EnchantmentWrapper ench : item.getEnchants()) {
                            item.removeEnchant(ench);
                        }

                        for(String str : enchants.keySet()) {
                            item.addEnchant(new EnchantmentWrapper(str, enchants.get(str)));
                        }

                        inv.setItem(slot.getIndex(), item);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}