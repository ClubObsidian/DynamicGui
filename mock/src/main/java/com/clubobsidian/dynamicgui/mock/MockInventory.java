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

package com.clubobsidian.dynamicgui.mock;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;

import java.util.HashMap;
import java.util.Map;

public class MockInventory {

    private final MockFactory factory = new MockFactory();
    private final int size;
    private final Map<Integer, MockItemStack> contents = new HashMap<>();

    public MockInventory(int size) {
        this.size = size;
    }

    public ItemStackWrapper<?>[] getContents() {
        ItemStackWrapper<?>[] ar = new ItemStackWrapper[this.size];
        for (int i = 0; i < this.size; i++) {
            ar[i] = this.getItem(i);
        }
        return ar;
    }

    public ItemStackWrapper<?> getItem(int index) {
        return this.contents.get(index) == null ?
                this.factory.createItemStack(Slot.IGNORE_MATERIAL)
                : this.factory.createItemStack(this.contents.get(index));
    }

    public void setItem(int index, ItemStackWrapper<?> itemStack) {
        this.contents.put(index, (MockItemStack) itemStack.getItemStack());
    }

    public void updateItem(int index, PlayerWrapper<?> player) {
        //do nothing
    }

    public int getSize() {
        return this.size;
    }

    public int getCurrentContentSize() {
        return this.contents.size();
    }
}
