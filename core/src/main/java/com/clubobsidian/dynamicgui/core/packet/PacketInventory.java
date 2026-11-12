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

import com.clubobsidian.dynamicgui.api.inventory.InventoryLimits;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * The contents of a client side only inventory.
 * <p>
 * Each item is stored with its packet item so packets can be written
 * from the netty thread without converting items there.
 */
public class PacketInventory {

    private final InventoryType type;
    private final int rows;
    private final int windowSlots;
    private final int size;
    private final AtomicReferenceArray<ItemStackWrapper<?>> items;
    private final AtomicReferenceArray<ItemStack> packetItems;
    private final AtomicReference<Component> title;
    private final Set<UUID> viewers = ConcurrentHashMap.newKeySet();

    public PacketInventory(@NotNull Component title, @NotNull InventoryType type, int rows) {
        this.title = new AtomicReference<>(Objects.requireNonNull(title));
        this.type = Objects.requireNonNull(type);
        this.rows = rows;
        this.windowSlots = InventoryLimits.windowSlots(type, rows);
        this.size = InventoryLimits.size(type, rows, true);
        this.items = new AtomicReferenceArray<>(this.size);
        this.packetItems = new AtomicReferenceArray<>(this.size);
    }

    public InventoryType getType() {
        return this.type;
    }

    public int getRows() {
        return this.rows;
    }

    /**
     * Gets the amount of slots the window has before the player's inventory
     *
     * @return the amount of window slots
     */
    public int getWindowSlots() {
        return this.windowSlots;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * Whether the inventory extends into the player's inventory
     *
     * @return if the inventory covers player inventory slots
     */
    public boolean coversPlayerInventory() {
        return this.size > this.windowSlots;
    }

    /**
     * Whether a window slot in the player's inventory part of the window is covered
     *
     * @param windowSlot the window slot
     * @return if the slot shows an item of this inventory
     */
    public boolean coversPlayerSlot(int windowSlot) {
        return windowSlot >= this.windowSlots && windowSlot < this.size;
    }

    @Nullable
    public PacketMenuType getMenuType() {
        return PacketMenuType.of(this.type, this.rows);
    }

    public Component getTitle() {
        return this.title.get();
    }

    public void setTitle(@NotNull Component title) {
        this.title.set(Objects.requireNonNull(title));
    }

    @Nullable
    public ItemStackWrapper<?> getItem(int index) {
        return this.items.get(index);
    }

    @NotNull
    public ItemStack getPacketItem(int index) {
        ItemStack packetItem = this.packetItems.get(index);
        return packetItem == null ? ItemStack.EMPTY : packetItem;
    }

    public void setItem(int index, @Nullable ItemStackWrapper<?> item, @NotNull ItemStack packetItem) {
        this.items.set(index, item);
        this.packetItems.set(index, Objects.requireNonNull(packetItem));
    }

    public Collection<UUID> getViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }

    void addViewer(UUID uuid) {
        this.viewers.add(uuid);
    }

    void removeViewer(UUID uuid) {
        this.viewers.remove(uuid);
    }
}
