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
package com.clubobsidian.dynamicgui.bukkit.inventory;

import com.clubobsidian.dynamicgui.bukkit.util.BukkitPacketUtil;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class BukkitInventoryWrapper<T extends Inventory> extends InventoryWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 954426075975347755L;

    public BukkitInventoryWrapper(T inventory) {
        super(inventory);
    }

    @Override
    public ItemStackWrapper<?>[] getContents() {
        ItemStack[] bukkitContents = this.getInventory().getContents();
        ItemStackWrapper<?>[] wrapperContents = new ItemStackWrapper<?>[bukkitContents.length];
        for (int i = 0; i < bukkitContents.length; i++) {
            ItemStack itemStack = bukkitContents[i];
            ItemStackWrapper<?> wrapped = new BukkitItemStackWrapper<>(itemStack);
            wrapperContents[i] = wrapped;
        }

        return wrapperContents;
    }

    @Override
    public ItemStackWrapper<ItemStack> getItem(int index) {
        return new BukkitItemStackWrapper<>(this.getInventory().getItem(index));
    }

    @Override
    public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) {
        this.getInventory().setItem(index, (ItemStack) itemStackWrapper.getItemStack());
    }

    @Override
    public void updateItem(int index, PlayerWrapper<?> playerWrapper) {
        ItemStackWrapper<ItemStack> itemStackWrapper = this.getItem(index);
        Player player = (Player) playerWrapper.getPlayer();
        ItemStack itemStack = itemStackWrapper.getItemStack();
        BukkitPacketUtil.sendSlotPacket(index, player, itemStack);
    }

    @Override
    public int getSize() {
        return this.getInventory().getSize();
    }

    @Override
    public int getCurrentContentSize() {
        int contentSize = 0;
        for (ItemStack item : this.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                contentSize += 1;
            }
        }
        return contentSize;
    }
}