/*
 *    Copyright 2018-2025 virustotalop
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

package com.clubobsidian.dynamicgui.core.packet;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An inventory that only exists on the client, it is sent and updated through packets.
 */
public class PacketInventoryWrapper extends InventoryWrapper<PacketInventory> {

    private static final long serialVersionUID = 4870915316513452710L;

    private final transient PacketEventsManager packetManager;

    public PacketInventoryWrapper(@NotNull PacketInventory inventory, @NotNull PacketEventsManager packetManager) {
        super(inventory);
        this.packetManager = Objects.requireNonNull(packetManager);
    }

    @Override
    public ItemStackWrapper<?>[] getContents() {
        ItemStackWrapper<?>[] contents = new ItemStackWrapper<?>[this.getSize()];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = this.getItem(i);
        }
        return contents;
    }

    @Override
    public ItemStackWrapper<?> getItem(int index) {
        ItemStackWrapper<?> item = this.getInventory().getItem(index);
        if (item == null) {
            return ItemStackManager.get().createItemStackWrapper(Slot.IGNORE_MATERIAL, 1);
        }
        return item;
    }

    @Override
    public void setItem(int index, @NotNull ItemStackWrapper<?> itemStackWrapper) {
        Objects.requireNonNull(itemStackWrapper);
        this.packetManager.setItem(this, index, itemStackWrapper);
    }

    @Override
    public void updateItem(int index, @NotNull PlayerWrapper<?> playerWrapper) {
        Objects.requireNonNull(playerWrapper);
        this.packetManager.sendItem(playerWrapper, this, index);
    }

    @Override
    public void open(@NotNull PlayerWrapper<?> playerWrapper) {
        Objects.requireNonNull(playerWrapper);
        this.packetManager.open(playerWrapper, this);
    }

    @Override
    public void close(@NotNull PlayerWrapper<?> playerWrapper) {
        Objects.requireNonNull(playerWrapper);
        this.packetManager.close(playerWrapper, this);
    }

    @Override
    public void setTitle(@NotNull PlayerWrapper<?> playerWrapper, @NotNull String title) {
        Objects.requireNonNull(playerWrapper);
        Objects.requireNonNull(title);
        this.packetManager.setTitle(playerWrapper, this, title);
    }

    @Override
    public int getSize() {
        return this.getInventory().getSize();
    }

    @Override
    public int getCurrentContentSize() {
        int contentSize = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if (!PacketEventsManager.isEmpty(this.getInventory().getItem(i))) {
                contentSize += 1;
            }
        }
        return contentSize;
    }
}
