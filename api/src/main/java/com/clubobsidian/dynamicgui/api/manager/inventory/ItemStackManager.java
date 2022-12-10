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

package com.clubobsidian.dynamicgui.api.manager.inventory;

import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;

import javax.inject.Inject;

public abstract class ItemStackManager {

    @Inject
    private static ItemStackManager instance;

    public static ItemStackManager get() {
        return instance;
    }
    /**
     * Creates a native item stack given a type and quantity
     *
     * @param type the item type
     * @param quantity the amount
     * @return the created native item stack
     */
    public abstract Object createItemStack(String type, int quantity);

    /**
     * Crates an item stack wrapper around a native wrapper
     *
     * @param itemStack a native item stack
     * @return the item wrapper
     */
    public abstract ItemStackWrapper<?> createItemStackWrapper(Object itemStack);

    /**
     * Creates an item stack wrapper around a native wrapper
     *
     * @param type type the item type
     * @param quantity the amount
     * @return the item wrapper
     */
    public ItemStackWrapper<?> createItemStackWrapper(String type, int quantity) {
        Object itemStack = this.createItemStack(type, quantity);
        return this.createItemStackWrapper(itemStack);
    }
}