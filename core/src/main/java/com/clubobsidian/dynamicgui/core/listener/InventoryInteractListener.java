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

package com.clubobsidian.dynamicgui.core.listener;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryView;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryClickEvent;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryDragEvent;
import com.clubobsidian.trident.EventHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class InventoryInteractListener {

    @EventHandler
    public void invClick(final InventoryClickEvent e) {
        PlayerWrapper<?> player = e.getPlayerWrapper();
        if (!GuiManager.get().hasGuiOpen(player)) {
            return;
        }

        Gui gui = GuiManager.get().getPlayerGui(player);

        Slot slot = this.getSlotFromIndex(gui, e.getSlot());
        if (slot == null && e.getView() != InventoryView.BOTTOM) {
            this.cancelClick(e);
            return;
        }

        ItemStackWrapper<?> item = e.getItemStackWrapper();
        if (e.getClick() == null) { //For other types of clicks besides left, right, middle
            this.cancelClick(e);
            return;
        } else if (item.getItemStack() == null) {
            return;
        } else if (e.getView() == InventoryView.BOTTOM) {
            if (e.getClick().isShift()) {
                if (!this.canStack(gui, e.getInventoryWrapper(), item)) {
                    this.cancelClick(e);
                }
            }
            return;
        }

        List<FunctionNode> functions = slot.getFunctions().getRootNodes();
        if (functions.size() > 0) {
            String clickString = e.getClick().toString();
            FunctionManager.get().tryFunctions(slot, FunctionType.valueOf(clickString), player);
        }

        if (!slot.isMovable()) {
            this.cancelClick(e);
        }

        boolean close = slot.getClose() != null ? slot.getClose() : (gui.getClose() != null ? gui.getClose() : true);
        if (close) {
            player.closeInventory();
        }
    }

    private void cancelClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Platform platform = DynamicGui.get().getPlatform();
        platform.getScheduler().runSyncDelayedTask(() -> {
            PlayerWrapper<?> futurePlayer = platform.getPlayer(e.getPlayerWrapper().getUniqueId());
            if (futurePlayer != null) {
                futurePlayer.updateCursor();
            }
        }, 1);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        PlayerWrapper<?> player = e.getPlayerWrapper();
        if (!GuiManager.get().hasGuiOpen(player)) {
            return;
        }

        Gui gui = GuiManager.get().getPlayerGui(player);

        Iterator<Entry<Integer, ItemStackWrapper<?>>> it = e.getSlotItems().entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, ItemStackWrapper<?>> next = it.next();
            int rawSlot = next.getKey();
            if (rawSlot < 0 || rawSlot >= e.getInventoryWrapper().getSize()) {
                return;
            }

            Slot slot = this.getSlotFromIndex(gui, rawSlot);
            if (slot == null || (slot != null && !slot.isMovable())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    private Slot getSlotFromIndex(Gui gui, int index) {
        for (Slot s : gui.getSlots()) {
            if (index == s.getIndex()) {
                return s;
            }
        }

        return null;
    }

    private boolean canStack(Gui gui, InventoryWrapper<?> inventory, ItemStackWrapper<?> clickedItem) {
        boolean canStack = false;
        ItemStackWrapper<?>[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStackWrapper<?> stackTo = contents[i];
            if (stackTo.getItemStack() == null || (stackTo.isSimilar(clickedItem) && validSize(clickedItem, stackTo))) {
                Slot slot = this.getSlotFromIndex(gui, i);
                if (slot != null) {
                    if (slot.isMovable()) {
                        canStack = true;
                    } else if (canStack && !slot.isMovable()) {
                        canStack = false;
                    }
                }
            }
        }

        return canStack;
    }

    private boolean validSize(ItemStackWrapper<?> clickedItem, ItemStackWrapper<?> stackTo) {
        return stackTo.getAmount() + clickedItem.getAmount() <= clickedItem.getMaxStackSize();
    }
}