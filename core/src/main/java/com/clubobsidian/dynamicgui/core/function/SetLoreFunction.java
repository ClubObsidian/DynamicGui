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

package com.clubobsidian.dynamicgui.core.function;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.replacer.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.core.util.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class SetLoreFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = -6723628078978301156L;

    public SetLoreFunction() {
        super("setlore");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        FunctionOwner owner = this.getOwner();
        if (owner != null && owner instanceof Slot) {
            Slot slot = (Slot) owner;
            Gui gui = slot.getOwner();
            if (gui != null) {
                InventoryWrapper<?> inv = gui.getInventoryWrapper();
                if (inv != null) {
                    ItemStackWrapper<?> item = slot.getItemStack();
                    List<String> lore = new ArrayList<>();
                    if (this.getData() == null) {
                        lore = null;
                    } else {
                        String newData = ReplacerManager.get().replace(this.getData(), playerWrapper);
                        newData = AnimationReplacerManager.get().replace(slot, playerWrapper, newData);
                        newData = ChatColor.translateAlternateColorCodes(newData);
                        if (newData.contains("\n")) {
                            for (String str : this.getData().split("\n")) {
                                lore.add(str);
                            }
                        } else {
                            lore.add(newData);
                        }
                    }
                    item.setLore(lore);
                    inv.setItem(slot.getIndex(), item);
                    return true;
                }
            }
        }
        return false;
    }
}