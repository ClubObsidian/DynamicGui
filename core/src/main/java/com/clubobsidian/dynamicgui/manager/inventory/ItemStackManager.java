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
package com.clubobsidian.dynamicgui.manager.inventory;

import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.google.inject.Inject;

public abstract class ItemStackManager {

    @Inject
    private static ItemStackManager instance;

    public static ItemStackManager get() {
        return instance;
    }

    public abstract Object createItemStack(String type, int quantity);

    public abstract ItemStackWrapper<?> createItemStackWrapper(Object itemStack);

    public ItemStackWrapper<?> createItemStackWrapper(String type, int quantity) {
        Object itemStack = this.createItemStack(type, quantity);
        return this.createItemStackWrapper(itemStack);
    }
}