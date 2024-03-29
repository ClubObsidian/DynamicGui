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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.manager.gui.SlotManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.platform.Platform;

import javax.inject.Inject;
import java.util.*;

public class SlotManagerImpl extends SlotManager {

    private final GuiManager guiManager;
    private final Platform platform;

    @Inject
    private SlotManagerImpl(GuiManager guiManager, Platform platform) {
        this.guiManager = guiManager;
        this.platform = platform;
        this.updateSlots();
    }

    private void updateSlots() {
        this.platform.getScheduler().scheduleSyncRepeatingTask(() -> {
            Map<Gui, Collection<Slot>> updatedStaticGui = new HashMap<>();
            for (Map.Entry<UUID, Gui> next : this.guiManager.getPlayerGuis().entrySet()) {
                UUID key = next.getKey();
                PlayerWrapper<?> playerWrapper = this.platform.getPlayer(key);
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