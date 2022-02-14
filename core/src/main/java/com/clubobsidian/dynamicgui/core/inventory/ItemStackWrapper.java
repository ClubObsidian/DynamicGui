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
package com.clubobsidian.dynamicgui.core.inventory;

import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;

import java.io.Serializable;
import java.util.List;

public abstract class ItemStackWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7902733103453967016L;

    private T itemStack;

    public ItemStackWrapper(T itemStack) {
        this.itemStack = itemStack;
    }

    public T getItemStack() {
        return this.itemStack;
    }

    @SuppressWarnings("unchecked")
    protected void setItemStack(Object itemStack) {
        this.itemStack = (T) itemStack;
    }

    public abstract int getAmount();

    public abstract void setAmount(int amount);

    public abstract int getMaxStackSize();

    public abstract String getType();

    public abstract boolean setType(String type);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract List<String> getLore();

    public abstract void setLore(List<String> lore);

    public abstract short getDurability();

    public abstract void setDurability(short durability);

    public abstract void addEnchant(EnchantmentWrapper enchant);

    public abstract void removeEnchant(EnchantmentWrapper enchant);

    public abstract List<EnchantmentWrapper> getEnchants();

    public abstract String getNBT();

    public abstract void setNBT(String nbt);

    public abstract void setGlowing(boolean glowing);

    public abstract boolean isGlowing();

    public abstract void addItemFlags(List<String> itemFlags);

    public abstract boolean isSimilar(ItemStackWrapper<?> compareTo);

    public abstract boolean setModel(int data);

    public abstract boolean hasCustomModel();

    public abstract int getModelData();
}