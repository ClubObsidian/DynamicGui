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

package com.clubobsidian.dynamicgui.api.manager.inventory;

import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;

import jakarta.inject.Inject;
import net.kyori.adventure.text.Component;

public abstract class InventoryManager {

    @Inject
    private static InventoryManager instance;

    public static InventoryManager get() {
        return instance;
    }

    /**
     * Creates a native inventory given a size and title.
     *
     * @param title the title of the inventory
     * @param size  the size of the gui, should be dividable by 9
     * @return the created native inventory
     */
    public abstract Object createInventory(Component title, int size);

    /**
     * Creates a native inventory with the default size with a given title and type.
     *
     * @param title the title of the inventory
     * @param type  the type of inventory
     * @return the created native inventory
     */
    public abstract Object createInventory(Component title, String type);

    /**
     * Creates an inventory wrapper
     *
     * @param inventory the native inventory to wrap
     * @return an inventory wrapper
     */
    public abstract InventoryWrapper<?> createInventoryWrapper(Object inventory);

    /**
     * Creates an inventory wrapper
     *
     * @param title the title of the inventory
     * @param size  the size of the gui, should be dividable by 9
     * @return an inventory wrapper
     */
    public InventoryWrapper<?> createInventoryWrapper(Component title, int size) {
        Object inventory = this.createInventory(title, size);
        return this.createInventoryWrapper(inventory);
    }
}