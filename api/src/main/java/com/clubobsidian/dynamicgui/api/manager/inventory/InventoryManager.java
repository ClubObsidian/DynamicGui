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

package com.clubobsidian.dynamicgui.api.manager.inventory;

import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;

import jakarta.inject.Inject;

public abstract class InventoryManager {

    @Inject
    private static InventoryManager instance;

    public static InventoryManager get() {
        return instance;
    }

    /**
     * Creates a native inventory given a size and title.
     *
     * @param size  the size of the gui, should be dividable by 9
     * @param title the title of the inventory
     * @return the created native inventory
     */
    public abstract Object createInventory(int size, String title);

    /**
     * Creates a native inventory with the default size with a given title and type.
     *
     * @param title the title of the inventory
     * @param type  the type of inventory
     * @return the created native inventory
     */
    public abstract Object createInventory(String title, String type);

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
     * @param size  the size of the gui, should be dividable by 9
     * @param title the title of the inventory
     * @return an inventory wrapper
     */
    public InventoryWrapper<?> createInventoryWrapper(int size, String title) {
        Object inventory = this.createInventory(size, title);
        return this.createInventoryWrapper(inventory);
    }
}