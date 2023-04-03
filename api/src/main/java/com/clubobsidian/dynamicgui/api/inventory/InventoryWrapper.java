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

package com.clubobsidian.dynamicgui.api.inventory;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public abstract class InventoryWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1039529042564261223L;

    private final T inventory;

    public InventoryWrapper(@NotNull T inventory) {
        this.inventory = Objects.requireNonNull(inventory);
    }

    /**
     * Gets the underlying native inventory
     *
     * @return native inventory
     */
    public T getInventory() {
        return this.inventory;
    }

    /**
     * Gets the contents of the inventory as an
     * array of ItemStackWrapper.
     *
     * @return array of contents of the inventory
     */
    public abstract ItemStackWrapper<?>[] getContents();

    /**
     * Gets an ItemStackWrapper for a given index.
     *
     * @param index to get the ItemStackWrapper from
     * @return an ItemStackWrapper
     */
    public abstract ItemStackWrapper<?> getItem(int index);

    /**
     * Sets the ItemStackWrapper at a given index
     *
     * @param index to set the ItemStackWrapper at
     * @param itemStackWrapper the wrapper to set
     */
    public abstract void setItem(int index, @NotNull ItemStackWrapper<?> itemStackWrapper);

    /**
     * Updates the item at a given index, this is accomplished
     * by not updating the physical item but with sending the
     * updated item to the player via packets.
     *
     * @param index to update at
     * @param playerWrapper the wrapper to update the item for
     */
    public abstract void updateItem(int index, @NotNull PlayerWrapper<?> playerWrapper);

    /**
     * Gets the size of the inventory, as in how many slots
     * that the inventory contains.
     *
     * @return the amount of slots the inventory has
     */
    public abstract int getSize();

    /**
     * Gets how many slots are currently occupied in the inventory,
     * this will not give the size of the inventory.
     *
     * @return the amount of slots occupied in the inventory
     */
    public abstract int getCurrentContentSize();

    /**
     * Adds a given ItemStackWrapper to an inventory
     *
     * @param itemStackWrapper the wrapper to add
     * @return -1 if the item could not be added else the index where it was added
     */
    public int addItem(@NotNull ItemStackWrapper<?> itemStackWrapper) {
        int index = this.getCurrentContentSize();
        if (index >= this.getSize()) {
            return -1;
        }
        this.setItem(index, itemStackWrapper);
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof InventoryWrapper))
            return false;
        InventoryWrapper<?> wrapper = (InventoryWrapper<?>) obj;

        return this.getInventory().equals(wrapper.getInventory());
    }
}