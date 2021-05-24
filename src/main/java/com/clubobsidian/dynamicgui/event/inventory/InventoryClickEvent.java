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
package com.clubobsidian.dynamicgui.event.inventory;


import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.InventoryEvent;
import com.clubobsidian.dynamicgui.gui.InventoryView;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.trident.Cancellable;

public class InventoryClickEvent extends InventoryEvent implements Cancellable {

    private final ItemStackWrapper<?> itemStackWrapper;
    private final int slot;
    private final Click click;
    private final InventoryView view;
    private boolean cancelled = false;

    public InventoryClickEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper, ItemStackWrapper<?> itemStackWrapper, int slot, Click click, InventoryView view) {
        super(playerWrapper, inventoryWrapper);
        this.itemStackWrapper = itemStackWrapper;
        this.slot = slot;
        this.click = click;
        this.view = view;
    }

    public ItemStackWrapper<?> getItemStackWrapper() {
        return this.itemStackWrapper;
    }

    public int getSlot() {
        return this.slot;
    }

    public InventoryView getView() {
        return this.view;
    }

    public Click getClick() {
        return this.click;
    }

    @Deprecated
    public boolean isCanceled() {
        return this.cancelled;
    }

    @Deprecated
    public void setCanceled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}