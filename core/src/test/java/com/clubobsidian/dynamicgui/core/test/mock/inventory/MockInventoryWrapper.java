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

package com.clubobsidian.dynamicgui.core.test.mock.inventory;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MockInventoryWrapper extends InventoryWrapper<MockInventory> {

    public MockInventoryWrapper(MockInventory inventory) {
        super(inventory);
    }

    @Override
    public ItemStackWrapper<?>[] getContents() {
        return this.getInventory().getContents();
    }

    @Override
    public ItemStackWrapper<?> getItem(int index) {
        return this.getInventory().getItem(index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStackWrapper<?> itemStack) {
        Objects.requireNonNull(itemStack);
        this.getInventory().setItem(index, itemStack);
    }

    @Override
    public void updateItem(int index, @NotNull PlayerWrapper<?> player) {
        Objects.requireNonNull(player);
        this.getInventory().updateItem(index, player);
    }

    @Override
    public int getSize() {
        return this.getInventory().getSize();
    }

    @Override
    public int getCurrentContentSize() {
        return this.getInventory().getCurrentContentSize();
    }
}
