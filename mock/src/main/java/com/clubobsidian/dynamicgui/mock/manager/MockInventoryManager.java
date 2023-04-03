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

package com.clubobsidian.dynamicgui.mock.manager;

import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.mock.MockInventory;
import com.clubobsidian.dynamicgui.mock.MockInventoryWrapper;

public class MockInventoryManager extends InventoryManager {
    @Override
    public Object createInventory(int size, String title) {
        return new MockInventory(size);
    }

    @Override
    public Object createInventory(String title, String type) {
        return new MockInventory(9); //TODO - Do inventory size based on type
    }

    @Override
    public InventoryWrapper<?> createInventoryWrapper(Object inventory) {
        return new MockInventoryWrapper((MockInventory) inventory);
    }
}
