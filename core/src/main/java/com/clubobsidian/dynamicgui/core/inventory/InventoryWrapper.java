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

package com.clubobsidian.dynamicgui.core.inventory;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;

import java.io.Serializable;

public abstract class InventoryWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1039529042564261223L;

    private final T inventory;

    public InventoryWrapper(T inventory) {
        this.inventory = inventory;
    }

    public T getInventory() {
        return this.inventory;
    }

    public abstract ItemStackWrapper<?>[] getContents();

    public abstract ItemStackWrapper<?> getItem(int index);

    public abstract void setItem(int index, ItemStackWrapper<?> itemStackWrapper);

    public abstract void updateItem(int index, PlayerWrapper<?> playerWrapper);

    public abstract int getSize();

    public abstract int getCurrentContentSize();

    public int addItem(ItemStackWrapper<?> itemStackWrapper) {
        int index = this.getCurrentContentSize();
        if (index >= this.getSize())
            return -1;

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