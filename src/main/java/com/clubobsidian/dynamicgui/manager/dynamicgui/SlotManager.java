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
package com.clubobsidian.dynamicgui.manager.dynamicgui;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.util.FunctionUtil;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

public class SlotManager {

    private static SlotManager instance;

    public static SlotManager get() {
        if(instance == null) {
            instance = new SlotManager();
        }
        return instance;
    }


    private SlotManager() {
        this.updateSlots();
    }

    private void updateSlots() {
        DynamicGui.get().getServer().getScheduler().scheduleSyncRepeatingTask(DynamicGui.get().getPlugin(), () -> {
            Iterator<Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
            while(it.hasNext()) {
                Entry<UUID, Gui> next = it.next();
                UUID key = next.getKey();
                PlayerWrapper<?> playerWrapper = DynamicGui.get().getServer().getPlayer(key);
                Gui gui = next.getValue();

                for(Slot slot : gui.getSlots()) {
                    System.out.println(slot.getItemStack().getType() + " "
                            + slot.getUpdateInterval() + " "
                            + slot.getCurrentTick());
                    if(slot.getUpdateInterval() == 0 && !slot.getUpdate()) {
                        continue;
                    }

                    slot.tick();
                    if(slot.getUpdate() || (slot.getCurrentTick() % slot.getUpdateInterval() == 0)) {
                        System.out.println("Should be updated");
                        ItemStackWrapper<?> itemStackWrapper = slot.buildItemStack(playerWrapper);
                        int slotIndex = slot.getIndex();

                        InventoryWrapper<?> inventoryWrapper = slot.getOwner().getInventoryWrapper();
                        inventoryWrapper.setItem(slotIndex, itemStackWrapper);

                        FunctionUtil.tryFunctions(slot, FunctionType.LOAD, playerWrapper);
                        if(!slot.getItemStack().getType().equalsIgnoreCase(Slot.IGNORE_MATERIAL)) {
                            inventoryWrapper.updateItem(slotIndex, playerWrapper);
                        }
                        slot.setUpdate(false);
                    }
                }
            }
        }, 1L, 1L);
    }
}