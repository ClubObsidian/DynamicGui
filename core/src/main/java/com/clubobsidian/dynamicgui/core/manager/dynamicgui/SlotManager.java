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

package com.clubobsidian.dynamicgui.core.manager.dynamicgui;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class SlotManager {

    private static SlotManager instance;

    public static SlotManager get() {
        if (instance == null) {
            instance = new SlotManager();
        }
        return instance;
    }

    private SlotManager() {
        this.updateSlots();
    }

    private void updateSlots() {
        DynamicGui.get().getPlatform().getScheduler().scheduleSyncRepeatingTask(() -> {
            Map<Gui, Collection<Slot>> updatedStaticGui = new HashMap<>();
            for (Entry<UUID, Gui> next : GuiManager.get().getPlayerGuis().entrySet()) {
                UUID key = next.getKey();
                PlayerWrapper<?> playerWrapper = DynamicGui.get().getPlatform().getPlayer(key);
                Gui gui = next.getValue();
                Collection<Slot> cachedSlots = updatedStaticGui.get(gui);
                if (gui.isStatic() && cachedSlots != null) {
                    InventoryWrapper<?> inventoryWrapper = gui.getInventoryWrapper();
                    for (Slot slot : cachedSlots) {
                        inventoryWrapper.updateItem(slot.getIndex(), playerWrapper);
                    }
                    continue;
                }
                Collection<Slot> updatedSlots = new ArrayList<>();
                for (Slot slot : gui.getSlots()) {
                    if (slot.getUpdateInterval() == 0 && !slot.getUpdate()) {
                        continue;
                    }

                    slot.tick();
                    if (slot.getUpdate() || (slot.getCurrentTick() % slot.getUpdateInterval() == 0)) {
                        ItemStackWrapper<?> itemStackWrapper = slot.buildItemStack(playerWrapper);
                        int slotIndex = slot.getIndex();

                        InventoryWrapper<?> inventoryWrapper = slot.getOwner().getInventoryWrapper();
                        inventoryWrapper.setItem(slotIndex, itemStackWrapper);

                        FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper);
                        if (!slot.getItemStack().getType().equalsIgnoreCase(Slot.IGNORE_MATERIAL)) {
                            inventoryWrapper.updateItem(slotIndex, playerWrapper);
                            updatedSlots.add(slot);
                        }
                        slot.setUpdate(false);
                    }
                }
                if (gui.isStatic()) { //Cache if gui is static an attempt to update was made
                    updatedStaticGui.put(gui, updatedSlots);
                }
            }
        }, 1L, 1L);
    }
}